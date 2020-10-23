package cn.ecomb.common.utils.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 渠道 HttpClient 封装
 *
 * @author brian.zhou
 * @date 2020/10/16
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 单例 HttpClient 对象
     */
    private static CloseableHttpClient httpClient = null;

    /**
     * 初始化 HttpClient 客户端
     * todo 这里的参数配置需要调优
     *
     * @return
     */
    public static CloseableHttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(1000);
        connectionManager.setDefaultMaxPerRoute(500);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .build();

        if (httpClient == null) {
            synchronized (HttpClientUtil.class) {
                if (httpClient == null) {
                    httpClient = HttpClientBuilder.create()
                            .setDefaultRequestConfig(requestConfig)
                            .setConnectionManager(connectionManager)
                            .addInterceptorFirst((HttpRequestInterceptor) (request, context)
                                    -> logger.info("请求支付接口：%s", request.getRequestLine().getUri()))
                            .build();
                }
            }
        }

        return httpClient;
    }

    /**
     * 封装 HttpClient，处理请求结果
     *
     * @param request          请求参数
     * @param responseResolver 返回结果解析
     * @return 返回请求对象
     */
    public static <T> T client(HttpRequestBase request, Function<HttpResponse, T> responseResolver) {

        CloseableHttpClient client = httpClient();

        ResponseHandler<T> rh = response -> {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                logger.info("请求 %s 接口报错：%s", request.getURI(), response.toString());
                throw new RuntimeException("请求 %s 接口报错");
            }

            return responseResolver.apply(response);
        };

        try {
            return client.execute(request, rh);
        } catch (IOException e) {
            logger.info("请求 %s 接口抛异常：%s", request.getURI(), e.getMessage());
            throw new RuntimeException("请求 %s 接口抛异常");
        }
    }

    /**
     * 简单封装 Get 请求，不同的支付方，自己实现参数加密，以及返回结果解析
     *
     * @param uri           请求 URL
     * @param responseClass 返回结构
     * @return 返回请求解密后的结果
     */
    public static <T> T get(String uri, Header[] headers, Class<T> responseClass) {
        HttpGet get = new HttpGet(uri);
        get.setHeaders(headers);

        return client(get, response -> {
            return resolverResponse(response, responseClass);
        });
    }

    public static <T> T get(String uri, Header[] headers, Function<HttpResponse, T> responseResolver) {
        HttpGet get = new HttpGet(uri);
        get.setHeaders(headers);
        return client(get, responseResolver);
    }

    /**
     * 封装 Post 请求
     *
     * @param uri           请求 URL
     * @param body          Post 请求参数，Json 格式
     * @param responseClass 返回结构
     * @return 返回请求解密后的结果
     */
    public static <T> T post(String uri, String body, Header[] headers, Class<T> responseClass) {
        HttpPost post = new HttpPost(uri);
        post.setHeaders(headers);

        StringEntity stringEntity = new StringEntity(body, UTF_8);
        post.setEntity(stringEntity);
        return client(post, response -> resolverResponse(response, responseClass));
    }

    public static <T> T post(String uri, String body, Header[] headers, Function<HttpResponse, T> responseResolver) {
        HttpPost post = new HttpPost(uri);
        post.setHeaders(headers);

        StringEntity stringEntity = new StringEntity(body, UTF_8);
        post.setEntity(stringEntity);
        return client(post, responseResolver);
    }

    /**
     * 封装 Put 请求
     *
     * @param uri           请求 URL
     * @param headers       定制请求头
     * @param responseClass 返回结构
     * @return 返回请求解密后的结果
     */
    public static <T> T put(String uri, Header[] headers, Class<T> responseClass) {
        HttpPut put = new HttpPut(uri);
        put.setHeaders(headers);

        return client(put, response -> resolverResponse(response, responseClass));
    }

    public static <T> T put(String uri, Header[] headers, Function<HttpResponse, T> responseResolver) {
        HttpPut put = new HttpPut(uri);
        put.setHeaders(headers);

        return client(put, responseResolver);
    }

    private static <T> T resolverResponse(HttpResponse response, Class<T> responseClass) {
        ObjectMapper mapper = new ObjectMapper();
        HttpEntity entity = response.getEntity();
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        try {
            Reader reader = new InputStreamReader(entity.getContent(), charset);
            return mapper.readValue(reader, responseClass);
        } catch (IOException e) {
            logger.error("读取响应 body 错误");
            throw new RuntimeException("读取响应 body 错误");
        }
    }
}

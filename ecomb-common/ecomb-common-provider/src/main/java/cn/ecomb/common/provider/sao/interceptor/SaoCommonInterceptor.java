package cn.ecomb.common.provider.sao.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 统一外部接口调用拦截器
 * @author zhouzhigang
 * @date 2018/8/2.
 */
@Slf4j
public class SaoCommonInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("请求url：", request.getURI());
        HttpHeaders headers = request.getHeaders();
        headers.add(HttpHeaders.USER_AGENT, "ecomb");
        log.info("请求参数: {}", new String(body, "UTF-8"));
        // 这里处理加签
        // 加签处理需要截取返回流，需要重新编写流复制代码
        ClientHttpResponse response = execution.execute(request, body);
        log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        return response;
    }
}

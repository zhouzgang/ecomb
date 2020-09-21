package cn.ecomb.common.provider.sao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

/**
 * @author zhouzhigang
 * @date 2018/8/2.
 */
@Component
public class EcombRestTemplateBuilder {

    @Bean(name = "mweeResource")
    RestTemplate mweeRestTemplateBuilder(
            @Qualifier("httpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.requestFactory((Supplier) () -> {
            return clientHttpRequestFactory;
        });
//        restTemplateBuilder.additionalInterceptors(new MweeInterceptor());
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean(name = "kouBeiResource")
    RestTemplate KouBeiRestTemplateBuilder(
            @Qualifier("httpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.requestFactory((Supplier) () -> {
            return clientHttpRequestFactory;
        });
//        restTemplateBuilder.additionalInterceptors(new KouBeiInterceptor());
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean(name = "SxMallResource")
    RestTemplate SxMallRestTemplateBuilder(
            @Qualifier("httpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.requestFactory((Supplier) () -> {
            return clientHttpRequestFactory;
        });
//        restTemplateBuilder.additionalInterceptors(new SxMallInterceptor());
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
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

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(@Qualifier("httpClient") HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(60*1000);
        clientHttpRequestFactory.setReadTimeout(60*1000);
        return clientHttpRequestFactory;
    }
}

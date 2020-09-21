package cn.ecomb.common.web.annotation;

import cn.ecomb.common.web.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpResponse;

/**
 * @description: 接口安全策略
 *              根据不同的第三方，处理不同的：
 *              1. 签名校验逻辑
 *              2. 加解密逻辑
 * @author: zhouzg
 * @create: 2019/12/12
 **/
public interface InterfaceSecurityStrategy {

    /**
     * 接口参数校验
     * @param body 请求参数
     * @param cryptKey 接口配置
     * @return  加密参数
     */
    String signValid(String body, String cryptKey);

    /**
     * 接口返回结果加密
     * @param response
     * @param responseBody
     * @param cryptKey
     * @return
     */
    Object responseCrypt(ServerHttpResponse response, HttpHeaders headers, Response responseBody, String cryptKey);
}

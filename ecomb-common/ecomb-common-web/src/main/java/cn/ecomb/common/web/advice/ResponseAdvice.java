package cn.ecomb.common.web.advice;

import cn.ecomb.common.provider.api.utils.SxEDUtils;
import cn.ecomb.common.utils.util.FastJsonUtils;
import cn.ecomb.common.web.annotation.Crypt;
import cn.ecomb.common.web.annotation.EcombApi;
import cn.ecomb.common.web.annotation.InterfaceSecurityStrategy;
import cn.ecomb.common.web.response.Response;
import cn.ecomb.common.web.response.WebResponse;
import cn.ecomb.common.web.response.WebResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 请求结果解析处理
 * 1. 所有第三方返回结果需要继承{@link WebResponse}, 并重写setCode(),setMessage()，setData() 方法。
 * <p>
 * 2. 如果接口返回 ResponseBody，则直接返回。
 * 否则根据注解 {@link } 的 responseClass 属性封装返回。
 * 接口没有注解，则默认封装 {@link WebResponse} 返回。
 * <p>
 * 3. 根据注解 {@link } 判断接口是否需要加密，如果需要解密，则加密封装对象返回。
 *
 * @author zhouzg
 * @date 2019/08/06
 */
@Slf4j
@ControllerAdvice(basePackages = {"cn.ecomb.web.app.controller"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        EcombApi classApi = returnType.getContainingClass().getAnnotation(EcombApi.class);
        Response responseBody = null;
        if (Objects.isNull(classApi) || (Objects.nonNull(classApi) && classApi.isWrap())) {
            if (!(body instanceof WebResponse)) {
                if (Objects.nonNull(classApi) && classApi.isOld()) {
                    responseBody = WebResponseWrapper.oldOk(body);
                } else if (Objects.isNull(classApi)) {
                    responseBody = WebResponseWrapper.ok(body);
                } else {
                    try {
                        responseBody = (Response) classApi.responseClass().newInstance();
                        responseBody.setData(body);
                        responseBody.fillMessage("success");
                        responseBody.fillCode(0);
                    } catch (InstantiationException e) {
                        log.error("对象实例化异常：{}", e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.error("对象实例化异常：{}", e.getMessage());
                    }
                }
            }
            body = responseBody;
        }

        Crypt methodDecrypt = returnType.getMethodAnnotation(Crypt.class);
        Crypt classDecrypt = returnType.getContainingClass().getAnnotation(Crypt.class);
        if (RequestAdvice.supportCrypt(methodDecrypt, classDecrypt)) {
            String cryptKey = RequestAdvice.getCryptKey(methodDecrypt, classDecrypt);
            InterfaceSecurityStrategy strategy = RequestAdvice.getCryptStrategy(methodDecrypt, classDecrypt);
            if (strategy != null) {
                return strategy.responseCrypt(response, null, responseBody, cryptKey);
            } else {
                String bodyStr = FastJsonUtils.toJSONString(body);
                response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                String encrypt = SxEDUtils.encrypt(bodyStr, cryptKey,
                        Objects.nonNull(classApi)
                                ? classApi.value()
                                : null);
                log.debug("加密key：{}, 加密原文：{},密文：{}", cryptKey, bodyStr, encrypt);
                return encrypt;
            }
        }

        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}

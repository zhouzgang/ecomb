package cn.ecomb.common.web.advice;


import cn.ecomb.common.provider.api.utils.CryptDynamicKeyUtil;
import cn.ecomb.common.provider.api.utils.SxEDUtils;
import cn.ecomb.common.web.annotation.Crypt;
import cn.ecomb.common.web.annotation.EcombApi;
import cn.ecomb.common.web.annotation.InterfaceSecurityStrategy;
import cn.ecomb.common.web.exception.WebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

import static cn.ecomb.common.provider.api.exception.CommonErrorCode.PARAM_CRYPT;


/**
 * 请求参数解析处理
 * 这个 ResponseBodyAdvice 只支持 @ResponseBody 注解的 controller 方法，
 * 同样，RequestBodyAdvice 只支持带有 @RequestBody 注解的 controller 方法参数的方法，
 * 同时上报的数据必须是 json or xml
 *
 * @author zhouzg
 * @date 2019/8/2
 */
@Slf4j
@ControllerAdvice(basePackages = {"cn.ecomb.web.app.controller"})
public class RequestAdvice implements RequestBodyAdvice {

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Crypt methodDecrypt = methodParameter.getMethodAnnotation(Crypt.class);
        Crypt classDecrypt = methodParameter.getContainingClass().getAnnotation(Crypt.class);
        return supportCrypt(methodDecrypt, classDecrypt) && !skipDecode(methodDecrypt, classDecrypt);
    }

    public static boolean supportCrypt(Crypt methodDecrypt, Crypt classDecrypt) {
        if (methodDecrypt != null && !methodDecrypt.value()) {
            return false;
        }
        boolean isClassDe = classDecrypt != null
                && classDecrypt.value()
                && classDecrypt.key().getKey() != null;
        return methodDecrypt != null && methodDecrypt.value()
                && (methodDecrypt.key().getKey() != null || methodDecrypt.dynamicKey() || isClassDe)
                || isClassDe;
    }

    public static boolean skipDecode(Crypt methodDecrypt, Crypt classDecrypt) {
        boolean isSkipClass = classDecrypt != null
                && classDecrypt.skipDecode();
        boolean isSkipMethod = methodDecrypt != null
                && methodDecrypt.skipDecode();
        return isSkipClass || isSkipMethod;
    }

    @Override
    public HttpInputMessage beforeBodyRead(
		    HttpInputMessage inputMessage, MethodParameter parameter,
		    Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Crypt methodDecrypt = parameter.getMethodAnnotation(Crypt.class);
        Crypt classDecrypt = parameter.getContainingClass().getAnnotation(Crypt.class);
        String cryptKey = getCryptKey(methodDecrypt, classDecrypt);

        InputStream is = inputMessage.getBody();
        byte[] bytes = new byte[64];
        int len = 0;
        StringBuffer body = new StringBuffer();
        while ((len = is.read(bytes)) != -1) {
            String s = new String(bytes, 0, len);
            body.append(s);
        }

        log.info("请求参数密文：{}", body.toString());
        EcombApi classApi = parameter.getContainingClass().getAnnotation(EcombApi.class);
        String decryptParam = SxEDUtils.decrypt(body.toString(), cryptKey, Objects.nonNull(classApi)
                ? classApi.value()
                : null);
        if (decryptParam == null) {
            log.info("参数解密失败");
            throw new WebException(PARAM_CRYPT);
        }
        log.info("请求Url：{}, 请求参数原文:{}", servletRequest.getRequestURL(), decryptParam);
        return new DecodedHttpInputMessage(inputMessage.getHeaders(),
                new ByteArrayInputStream(decryptParam.getBytes("UTF-8")));
    }

    public static String getCryptKey(Crypt methodDecrypt, Crypt classDecrypt) {
        String cryptKey = "";
        if (methodDecrypt != null && methodDecrypt.key().getKey() != null) {
            cryptKey = methodDecrypt.key().getKey();
        } else if (classDecrypt != null && classDecrypt.key().getKey() != null) {
            cryptKey = classDecrypt.key().getKey();
        } else if (methodDecrypt != null && methodDecrypt.dynamicKey()) {
            cryptKey = CryptDynamicKeyUtil.getDynamicKey();
        }
        return cryptKey;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
                                  MethodParameter parameter, Type targetType,
                                  Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecodedHttpInputMessage extends MappingJacksonInputMessage {
        HttpHeaders headers;
        InputStream body;

        public DecodedHttpInputMessage(HttpHeaders headers, InputStream body) {
            super(body, headers);
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

    public static InterfaceSecurityStrategy getCryptStrategy(Crypt methodDecrypt, Crypt classDecrypt) {
        InterfaceSecurityStrategy strategy = null;
        if (methodDecrypt != null && methodDecrypt.interfaceSecurityStrategy() != Void.class) {
            strategy = SpringContextProvider.getBean(methodDecrypt.interfaceSecurityStrategy());
        } else if (classDecrypt != null && classDecrypt.interfaceSecurityStrategy() != Void.class) {
            strategy = SpringContextProvider.getBean(classDecrypt.interfaceSecurityStrategy());
        }
        return strategy;
    }
}

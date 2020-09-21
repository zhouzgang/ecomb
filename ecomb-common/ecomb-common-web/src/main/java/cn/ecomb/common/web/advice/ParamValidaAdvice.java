package cn.ecomb.common.web.advice;

import cn.ecomb.common.provider.api.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 请求参数解析处理
 * 这个 ResponseBodyAdvice 只支持 @ResponseBody 注解的 controller 方法，
 * 同样，RequestBodyAdvice 只支持带有 @RequestBody 注解的 controller 方法参数的方法，
 * 同时上报的数据必须是 json or xml
 *
 * @author zhouzg
 * @date 2019/8/2
 */
@ControllerAdvice(basePackages = {"cn.ecomb"})
@Slf4j
public class ParamValidaAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        ValidationUtil.hibernateValidate(body);
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}

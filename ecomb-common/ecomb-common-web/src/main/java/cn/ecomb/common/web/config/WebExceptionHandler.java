package cn.ecomb.common.web.config;

import cn.ecomb.common.provider.api.exception.AbstractException;
import cn.ecomb.common.provider.api.exception.CommonErrorCode;
import cn.ecomb.common.provider.api.utils.SxEDUtils;
import cn.ecomb.common.utils.util.EDUtils;
import cn.ecomb.common.utils.util.FastJsonUtils;
import cn.ecomb.common.web.advice.RequestAdvice;
import cn.ecomb.common.web.annotation.Crypt;
import cn.ecomb.common.web.annotation.EcombApi;
import cn.ecomb.common.web.annotation.InterfaceSecurityStrategy;
import cn.ecomb.common.web.response.Response;
import cn.ecomb.common.web.response.WebResponseWrapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * 全局异常处理切面
 * HttpStatus: 500
 *
 * @author zhouzhigang
 * @date 2017/11/13.
 */
@Slf4j
@ControllerAdvice(basePackages = {"cn.ecomb.web.app"})
public class WebExceptionHandler {

    /**
     * 所有系统异常拦截处理
     *
     * @param ex 抛出的任何异常
     * @return 返回封装后的异常，都以httpStatus：200 的形式
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(
		    final Exception ex, HttpServletResponse response, HandlerMethod handlerMethod) throws IllegalAccessException, InstantiationException {

        log.error("【Web应用异常】: {}", ex.getMessage(), ex);
        Class containingClass = handlerMethod.getBeanType();
        if (Objects.isNull(containingClass)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Crypt methodDecrypt = handlerMethod.getMethod().getAnnotation(Crypt.class);
        Crypt classDecrypt = (Crypt) containingClass.getAnnotation(Crypt.class);
        EcombApi classApi = (EcombApi) containingClass.getAnnotation(EcombApi.class);
        Response sxWebResponse = null;
        if (Objects.nonNull(classApi)) {
            sxWebResponse = (Response) classApi.responseClass().newInstance();
            sxWebResponse.fillCode(CommonErrorCode.ERROR.getCode());
            sxWebResponse.fillMessage(CommonErrorCode.ERROR.getMsg());
        } else {
            sxWebResponse = WebResponseWrapper.error();
        }
        if (ex instanceof AbstractException) {
            sxWebResponse.fillCode(((AbstractException) ex).getCode());
            sxWebResponse.fillMessage(ex.getMessage());
        }
        if (RequestAdvice.supportCrypt(methodDecrypt, classDecrypt)) {
            String cryptKey = RequestAdvice.getCryptKey(methodDecrypt, classDecrypt);
            if (StringUtils.isEmpty(cryptKey)) {
                return new ResponseEntity<>(sxWebResponse, HttpStatus.OK);
            }
            InterfaceSecurityStrategy strategy = RequestAdvice.getCryptStrategy(methodDecrypt, classDecrypt);
            if (strategy != null) {
                HttpHeaders headers = new HttpHeaders();
                return new ResponseEntity<>(
                        JSON.toJSONString(strategy.responseCrypt(null, headers, sxWebResponse, cryptKey)),
                        headers,
                        HttpStatus.OK);
            } else {
                String bodyStr = FastJsonUtils.toJSONString(sxWebResponse);
                log.debug("加密key：{}, 加密原文：{}", cryptKey, bodyStr);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                String encrypt = SxEDUtils.encrypt(bodyStr, cryptKey,
                        Objects.nonNull(classApi)
                                ? classApi.value()
                                : null);
                ResponseEntity responseEntity = new ResponseEntity<>(encrypt, headers, HttpStatus.OK);
                return responseEntity;
            }
        }
        return new ResponseEntity<>(sxWebResponse, HttpStatus.OK);
    }

}

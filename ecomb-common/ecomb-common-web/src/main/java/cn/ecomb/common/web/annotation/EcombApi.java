package cn.ecomb.common.web.annotation;


import cn.ecomb.common.provider.api.constant.SxApiUserConstant;
import cn.ecomb.common.web.response.WebResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义 API 基础属性
 * @author zhouzg
 * @date 2019/8/5
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EcombApi {

    /**
     * 是否使用统一返回结构封装
     * @return
     */
    boolean isWrap() default true;


    /**
     * 是否旧版接口（旧版code为0）
     * @return
     */
    boolean isOld() default false;
    /**
     * 接口使用方
     * @return
     */
    SxApiUserConstant value() default SxApiUserConstant.SUI_XING;

    /**
     * 接口返回结构
     * @return
     */
    Class responseClass() default WebResponse.class;
}

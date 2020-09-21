package cn.ecomb.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数解密
 *
 * @author zhouzg
 * @date 2019/8/3
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Crypt {

    /**
     * 是否需要解密
     * @return
     */
    boolean value() default true;

    boolean name() default false;

    boolean skipDecode() default false;
    /**
     * 解密的key
     * @return
     */
    CryptKeyConstant key() default CryptKeyConstant.DEFAULT_CRYPT_KEY;

    /**
     * 动态的key,从threadload取值
     * @return
     */
    boolean dynamicKey() default false;

    /**
     * 使用aes-gcm算法加密
     * @return
     */
    boolean useAesGcm() default false;

    /**
     * 鉴权策略
     * @return
     */
    Class interfaceSecurityStrategy() default Void.class;

}

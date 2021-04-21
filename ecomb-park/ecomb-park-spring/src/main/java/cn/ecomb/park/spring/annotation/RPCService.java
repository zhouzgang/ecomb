package cn.ecomb.park.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author brian.zhou
 * @date 2021/4/21
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RPCService {

	/**
	 * path
	 * @return
	 */
	String value() default "";

	String appId() default "";
}

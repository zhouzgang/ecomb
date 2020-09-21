package cn.ecomb.common.provider.api.utils;

import cn.ecomb.common.provider.api.exception.CommonErrorCode;
import cn.ecomb.common.provider.api.exception.ErrorResult;
import cn.ecomb.common.provider.api.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 参数校验工具类
 *
 * @author zhouzg
 * @date 2018/7/25
 */
@Slf4j
public class ValidationUtil {

    /*** 手机号码正则*/
    private static final String MOBILE = "^(1[3-9])\\d{9}$";

    public static void isNullOrEmpty(List list, Integer code, String msg) {
        if (CollectionUtils.isEmpty(list)) {
            throw new ValidationException(code, msg);
        }
    }

    public static void isNullOrEmpty(boolean expression, ErrorResult errorCode) {
        if (expression) {
            throw new ValidationException(errorCode.getCode(), errorCode.getMsg());
        }
    }


    public static void isNull(Object object, String message) {
        isNull(object, 1001, message);
    }

    public static void isNull(Object object, ErrorResult errorResult) {
        if (Objects.isNull(object)) {
            throw new ValidationException(errorResult.getCode(), errorResult.getMsg());
        }
    }

    public static void isNull(ErrorResult errorResult, Object... objs) {
        for (Object obj : objs) {
            if (obj == null || "".equals(obj)) {
                throw new ValidationException(errorResult.getCode(), errorResult.getMsg());
            }
        }
    }

    public static void isNull(Object object, Integer code, String message) {
        if (object == null || "".equals(object)) {
            throw new ValidationException(code, message);
        }
    }

    public static void isNull(String message, Object... objs) {
        isNull(1001, message, objs);
    }

    public static void isNull(Integer code, String message, Object... objs) {
        for (Object obj : objs) {
            if (obj == null || "".equals(obj)) {
                throw new ValidationException(code, message);
            }
        }
    }


    public static void isTrueThrow(boolean expression, String message) {
        isTrueThrow(expression, 1002, message);
    }

    public static void isTrueThrow(boolean expression, ErrorResult errorResult) {
        if (expression) {
            throw new ValidationException(errorResult);
        }
    }


    public static void isTrueThrow(boolean expression, Integer code, String message) {
        if (expression) {
            throw new ValidationException(code, message);
        }
    }

    public static void isFalseThrow(boolean expression, String message) {
        isFalseThrow(!expression, 1002, message);
    }

    public static void isFalseThrow(boolean expression, ErrorResult errorResult) {
        isFalseThrow(expression, errorResult.getCode(), errorResult.getMsg());
    }

    public static void isFalseThrow(boolean expression, Integer code, String message) {
        isTrueThrow(!expression, code, message);
    }

    public static void isInvalid(boolean expression, String message) {
        if (expression) {
            throw new ValidationException(1002, message);
        }
    }


    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    /**
     * 指定需要校验的参数，调用Hibernate-validate中的工具来校验。
     *
     * @param param      需要校验的对象
     * @param flag       true表示只校验properties中的属性；false表示不对properties中的属性进行校验
     * @param properties 属性列表
     * @throws ValidationException
     * @throws IllegalAccessException
     */
    public static void hibernateValidate(Object param, boolean flag, String... properties) throws ValidationException {
        getValidateProperties(param, flag, properties).stream()
                .flatMap(name -> validator.validateProperty(param, name).stream())
                .map((ConstraintViolation<?> item) -> item.getMessage())
                .findFirst() //只取出第一条校验结果
                .ifPresent(msg -> {
                    throw new ValidationException(CommonErrorCode.PARAM_ERROR.getCode(), msg);
                });
    }

    /**
     * 指定需要校验的参数，调用Hibernate-validate中的工具来校验。
     *
     * @param param 需要校验的对象
     * @throws ValidationException
     * @throws IllegalAccessException
     */
    public static void hibernateValidate(Object param) throws ValidationException {
        Optional.ofNullable(param.getClass().getDeclaredFields())
                .ifPresent(fields -> {
                    Arrays.stream(fields)
                            .map(field -> field.getName())
                            .flatMap(name -> validator.validateProperty(param, name).stream())
                            .map((ConstraintViolation<?> item) -> item.getMessage())
                            .findFirst()
                            .ifPresent((msg) -> {
                                log.error("接口：{}，参数校验失败：{}", param.getClass().getName(), msg);
                                throw new ValidationException(CommonErrorCode.PARAM_ERROR.getCode(), msg);
                            });
                });
    }

    /**
     * 返回参与校验的属性
     * @param param
     * @param flag
     * @param properties
     * @return
     */
    private static List<String> getValidateProperties(Object param, boolean flag, String... properties) {
        List<String> list = Arrays.asList(properties);
        if (flag) {
            return list;
        }

        return Optional.ofNullable(param.getClass().getDeclaredFields())
                .map(fields -> Arrays.stream(fields)
                        .map(field -> field.getName())
                        .filter(name -> !list.contains(name))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

    }

    /**
     * 验证手机号码格式
     * @param mobilePhone
     * @return
     */
    public static boolean verityMobile(String mobilePhone){
        Pattern p = Pattern.compile(MOBILE);
        Matcher m = p.matcher(mobilePhone);
        return m.find();
    }
}

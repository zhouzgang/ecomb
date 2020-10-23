package cn.ecomb.common.provider.api.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 自定义异常的父类
 * 继承 HystrixBadRequestException 类，逻辑异常不需要熔断。
 *
 * @author zhouzhigang
 * @date 2017/11/14.
 */
@Data
public abstract class AbstractException extends RuntimeException implements Supplier {

    /** 异常状态码 */
    private int code;

    /** 返回用户错误信息 */
    private String message;

    public AbstractException(ErrorResult errorResult) {
        super(errorResult.getMsg());
        this.code = errorResult.getCode();
        this.message = errorResult.getMsg();
    }

    public AbstractException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public Object get() {
        return this;
    }
}
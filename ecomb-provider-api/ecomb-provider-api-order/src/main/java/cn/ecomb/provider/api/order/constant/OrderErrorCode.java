package cn.ecomb.provider.api.order.constant;


import cn.ecomb.common.provider.api.exception.ErrorResult;

import java.util.Objects;

/**
 * 订单相关错误码
 *
 * @author brian.zhou
 * @date 2020/9/22
 */
public enum OrderErrorCode implements ErrorResult {

    PRODUCT_NOT_EXIST(2, "商品 %s 不存在"),
    PAY_ERROR(2, "支付失败"),

    USER_PHONE_EMPTY(252200,"手机号为空"),
    USER_PHONE_ERROR(252201,"手机号格式错误"),
    PHONE_SEND_MSG_TOO_FAST(252202,"请勿频繁发生短信"),
    USER_CREATE_CODE_FAIL(252203,"生成code失败"),
    PHONE_VERIFY_CODE_VALIDATE_ERROR(252204,"手机验证码校验失败"),
    IMAGE_VERIFY_CODE_VALIDATE_ERROR(252205,"图片验证码校验失败"),
    USER_NO_LOGIN(252206,"用户未登录，请先登录"),
    USER_TOKEN_EXPIRE(252207,"token过期")
    ;

    private int code;
    private String msg;


    OrderErrorCode(int value, String msg) {
        this.code = value;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 通过code获取枚举类
     *
     * @return
     */
    public static ErrorResult parseEnumByCode(int code) {
        for (OrderErrorCode commonError : OrderErrorCode.values()) {
            if (Objects.equals(commonError.getCode(), code)) {
                return commonError;
            }
        }
        return null;
    }

    public  ErrorResult buildErrorMsg(String errorMsg) {
        if (errorMsg == null || "".equals(errorMsg)) {
            errorMsg = "";
        }

        this.msg = String.format(this.msg, errorMsg);
        return this;
    }
}

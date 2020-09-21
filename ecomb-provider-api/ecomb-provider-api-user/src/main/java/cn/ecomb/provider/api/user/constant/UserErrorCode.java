package cn.ecomb.provider.api.user.constant;


import cn.ecomb.common.provider.api.exception.ErrorResult;

import java.util.Objects;

/**
 * 用户相关错误码
 * @author brian.zhou
 */
public enum UserErrorCode implements ErrorResult {

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


    UserErrorCode(int value, String msg) {
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
        for (UserErrorCode commonError : UserErrorCode.values()) {
            if (Objects.equals(commonError.getCode(), code)) {
                return commonError;
            }
        }
        return null;
    }
}

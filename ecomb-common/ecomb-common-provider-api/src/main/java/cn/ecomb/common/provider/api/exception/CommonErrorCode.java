package cn.ecomb.common.provider.api.exception;

import java.util.Objects;

/**
 * 错误对象封装
 * - 错误码A-BB-CCC
 * - A  : 错误级别编号： 1-系统级  2-内部业务  3-外部业务
 * - BB : 模块编号: 数据模块 与 业务模块使用同一位置编码，分别使用不同段
 *     - 数据模块编号[00~49]：00-scene 01-admin 02-area 03-user 04-service 05-trip 06-sync 07-customer
 *     - 业务模块编号[50~99]：50-common 51-admin 52-coupon 53-mobile 54-trip
 * - CCC: 具体错误编号: 错误编码根据功能块分段，具体分段划分备注在 XXXErrorCode 的错误枚举类中，ex：
 *   - 优惠: 252
 *     - 公共：00 ~ 49
 *     - 商场：50 ~ 99
 *     - 店铺：100 ~ 149
 *     - 排号：150 ~ 199
 *     - 用户中心：200 ~ 249
 * @author zhouzhigang
 */
public enum CommonErrorCode implements ErrorResult {

    SUCCESS(200, "success"),
    ERROR(200003, "服务器异常，稍后重试"),
    PARAM_CRYPT(200004, "参数解密失败"),
    NO_PARAM(200004, "参数%s不能为空"),
    NO_RESOURCES(200005, "资源不存在"),
    NOT_MATCH_RESOURCE(200006, "资源不匹配"),
    DEPEND_API_ERROR(202006, "依赖接口异常"),
    PARAM_ERROR(200007, "参数有误"),
    DATA_ERROR(200008, "资源已过期，请重试"),
    DUPLICATEKEY(200009,"记录已存在"),


    GAO_DE_APP_KEY_DISABLED(200400,"无可用appkey"),
    ;

    private int code;
    private String msg;


    CommonErrorCode(int value, String msg) {
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
        for (CommonErrorCode commonError : CommonErrorCode.values()) {
            if (Objects.equals(commonError.getCode(), code)) {
                return commonError;
            }
        }
        return null;
    }

    public  ErrorResult buildParamErrorMsg(String paramErrorMsg) {
        if (paramErrorMsg == null || "".equals(paramErrorMsg)) {
            paramErrorMsg = "";
        }

        if (this.code == NO_PARAM.code) {
            this.msg = "参数 " + paramErrorMsg + " 不能为空";
        }
        return this;
    }

}
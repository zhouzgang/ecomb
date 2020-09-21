package cn.ecomb.common.provider.constant;

import cn.ecomb.common.provider.api.exception.CommonErrorCode;
import cn.ecomb.common.provider.api.exception.ErrorResult;

import java.util.Objects;

/**
 * 接入层相关异常码
 * - CCC: 具体错误编号: 错误编码根据功能块分段，具体分段划分备注在 XXXErrorCode 的错误枚举类中：
 *      - mwee: 352[000 ~ 099]
 *      - gao: 352[100 ~ 199]
 * @author zhouzg
 * @date
 */
public enum SaoErrorCode implements ErrorResult {
    MWEE_ENCRYPT_ERROR(352001, "参数加密失败"),
    MWEE_DECRYPT_ERROR(352001, "结果解密失败"),
    ;

    private int code;
    private String msg;


    SaoErrorCode(int value, String msg) {
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
}

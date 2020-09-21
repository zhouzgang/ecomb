package cn.ecomb.common.web.response;

import cn.ecomb.common.provider.api.exception.ErrorResult;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行返回结果接口
 *
 * @author zhouzg
 * @date 2019/8/6
 */
@Data
public class WebResponse<T> implements Serializable, Response<T> {

    /**
     * 返回详细状态码
     */
    private int code;

    /**
     * 返回用户错误信息
     */
    private String message;

    private T data;

    public WebResponse() {
    }

    public WebResponse(ErrorResult errorResult) {
        this(errorResult.getCode(), errorResult.getMsg());
    }

    public WebResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public int fetchCode() {
        return code;
    }

    @Override
    public WebResponse fillCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String fetchMessage() {
        return message;
    }

    @Override
    public WebResponse fillMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Response setData(T data) {
        this.data = data;
        return this;
    }
}

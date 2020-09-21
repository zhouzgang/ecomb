package cn.ecomb.common.provider.api.result;

import cn.ecomb.common.provider.api.exception.ErrorResult;
import lombok.Data;

/**
 * 随行标准返回结构体
 *
 * @author zhouzg
 * @date 2019/9/9
 */
@Data
public class ApiResult<T>{

    public static int SUCCESS_CODE = 200;

    public static String SUCCESS_MESSAGE = "操作成功";

    /** 返回详细状态码 */
    private int code;

    /** 返回用户错误信息 */
    private String message;

    /** 返回正常状态的数据 */
    private T data;

    public ApiResult() {}

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(ErrorResult errorResult) {
        this(errorResult.getCode(), errorResult.getMsg());
    }

    public ApiResult(int code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public boolean isSuccess(){
        if(code == SUCCESS_CODE){
            return true;
        }else{
            return false;
        }
    }

}

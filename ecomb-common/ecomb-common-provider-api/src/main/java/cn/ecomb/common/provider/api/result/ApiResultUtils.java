package cn.ecomb.common.provider.api.result;


import cn.ecomb.common.provider.api.exception.ErrorResult;
import cn.ecomb.common.provider.api.exception.ProviderApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static cn.ecomb.common.provider.api.exception.CommonErrorCode.DEPEND_API_ERROR;


/**
 * @description 返回结构工具类
 * @author zhouzg
 * @create 2019-09-17 21:43
 **/
public class ApiResultUtils {

    private static Logger logger = LoggerFactory.getLogger(ApiResultUtils.class);

    /***
     * 构建正确返回对象
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> ok(T t) {
        return new ApiResult<>(ApiResult.SUCCESS_CODE,
                ApiResult.SUCCESS_MESSAGE,
                t);
    }

    /***
     * 构建异常返回对象
     * @param errorResult
     * @return
     */
    public static ApiResult error(ErrorResult errorResult) {
        return new ApiResult(errorResult.getCode(), errorResult.getMsg());
    }

    /**
     * 构建异常返回对象
     * @param code
     * @param message
     * @return
     */
    public static ApiResult error(int code, String message) {
        return new ApiResult(code, message);
    }

    /**
     * 判断请求是否成功
     * @param response
     * @return
     */
    public static Boolean isSuccess(ApiResult response) {
        if (Objects.isNull(response)) {
            throw new ProviderApiException(DEPEND_API_ERROR);
        }
        return response.getCode() != ApiResult.SUCCESS_CODE;
    }

    /**
     * 获取成功请求的返回数据
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T body(ApiResult<T> response) {
        if (Objects.isNull(response)) {
            throw new ProviderApiException(DEPEND_API_ERROR);
        }
        if (response.getCode() != ApiResult.SUCCESS_CODE) {
            logger.error("调用XX服务XXXurl报错：mallCode：{}，message：{}",
                    response.getCode(), response.getMessage());
            // todo 需要解决服务提供方接口错误码转服务消费错误异常码
            throw new ProviderApiException(response.getCode(), response.getMessage());
        }
        return response.getData();
    }
}

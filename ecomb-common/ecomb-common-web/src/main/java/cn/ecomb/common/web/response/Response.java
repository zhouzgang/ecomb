package cn.ecomb.common.web.response;

/**
 * 返回结果接口
 * @author zhouzg
 * @date 2019/8/6
 */
public interface Response<T> {

    /**
     * 获取返回结果编码
     * @return
     */
    int fetchCode();

    /**
     * 设置返回结果编码
     * @param code
     * @return
     */
    Response fillCode(int code);

    /**
     * 获取返回结果提示语
     * @return
     */
    String fetchMessage();

    /**
     * 设置返回结果提示语
     * @param message
     * @return
     */
    Response fillMessage(String message);

    /**
     * 获取返回数据
     * @return
     */
    T getData();

    /**
     * 设置返回数据
     * @param data
     * @return
     */
    Response setData(T data);
}

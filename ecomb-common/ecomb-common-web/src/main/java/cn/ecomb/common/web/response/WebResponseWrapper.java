package cn.ecomb.common.web.response;


import cn.ecomb.common.provider.api.exception.CommonErrorCode;
import cn.ecomb.common.provider.api.exception.ErrorResult;

/**
 * 返回结果包装类
 * @author zhouzg
 */
public class WebResponseWrapper {

	public static int SUCCESS_CODE = 200;
	public static int OLD_SUCCESS_CODE = 0;
	public static String SUCCESS_MESSAGE = "success";

	private WebResponseWrapper() {
	}

	public static <E> WebResponse<E> error() {
		return new WebResponse<>(CommonErrorCode.ERROR);
	}

	public static <E> WebResponse<E> error(ErrorResult result) {
		return new WebResponse<>(result);
	}

	public static <E> WebResponse<E> result(int code, String message) {
		return new WebResponse<>(code, message);
	}

	public static <E> WebResponse<E> ok() {
		return new WebResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE);
	}

	public static <E> WebResponse<E> ok(E o) {
		return new WebResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, o);
	}


	public static <E> WebResponse<E> oldOk(E o) {
		return new WebResponse<>(OLD_SUCCESS_CODE, SUCCESS_MESSAGE, o);
	}
}

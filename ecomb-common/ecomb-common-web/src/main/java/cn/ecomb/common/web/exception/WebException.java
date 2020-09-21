package cn.ecomb.common.web.exception;

import cn.ecomb.common.provider.api.exception.AbstractException;
import cn.ecomb.common.provider.api.exception.ErrorResult;

/**
 * web 层异常
 * @author brian.zhou
 * @date 2020/9/21
 */
public class WebException extends AbstractException {

	public WebException(int code, String message) {
		super(code, message);
	}

	public WebException(ErrorResult errorResult) {
		super(errorResult);
	}

	@Override
	public Object get() {
		return this;
	}
}

package cn.ecomb.app.advice.exception;

import cn.ecomb.common.provider.api.exception.AbstractException;
import cn.ecomb.common.provider.api.exception.ErrorResult;

/**
 * @author brian.zhou
 * @date 2020/11/10
 */
public class AdviceException extends AbstractException {

	public AdviceException(ErrorResult errorResult) {
		super(errorResult);
	}

	public AdviceException(int code, String message) {
		super(code, message);
	}
}

package cn.ecomb.common.provider.api.exception;

/**
 * 服务层异常
 * @author brian.zhou
 * @date 2020/10/10
 */
public class ServiceException extends AbstractException{

	public ServiceException(ErrorResult errorResult) {
		super(errorResult);
	}

	public ServiceException(int code, String message) {
		super(code, message);
	}
}

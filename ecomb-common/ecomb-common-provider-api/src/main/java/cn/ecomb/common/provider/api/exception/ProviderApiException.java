package cn.ecomb.common.provider.api.exception;

public class ProviderApiException extends AbstractException {

    public ProviderApiException(ErrorResult errorResult) {
        super(errorResult);
    }

    public ProviderApiException(int code, String message) {
        super(code, message);
    }
}

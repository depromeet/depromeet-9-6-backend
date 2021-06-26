package com.depromeet.articlereminder.exception;

public class IllegalLoginParamException extends RuntimeException {
    public IllegalLoginParamException() {
        super();
    }

    public IllegalLoginParamException(String message) {
        super(message);
    }

    public IllegalLoginParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalLoginParamException(Throwable cause) {
        super(cause);
    }

    protected IllegalLoginParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

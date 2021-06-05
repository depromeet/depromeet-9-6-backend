package com.depromeet.articlereminder.exception;

public class AlarmNotFoundException extends RuntimeException {
    public AlarmNotFoundException() {
        super();
    }

    public AlarmNotFoundException(String message) {
        super(message);
    }

    public AlarmNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlarmNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AlarmNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

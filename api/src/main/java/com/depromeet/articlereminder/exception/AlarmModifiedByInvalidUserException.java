package com.depromeet.articlereminder.exception;

public class AlarmModifiedByInvalidUserException extends RuntimeException {
    public AlarmModifiedByInvalidUserException() {

    }

    public AlarmModifiedByInvalidUserException(String message) {
        super(message);
    }

    public AlarmModifiedByInvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlarmModifiedByInvalidUserException(Throwable cause) {
        super(cause);
    }

    protected AlarmModifiedByInvalidUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

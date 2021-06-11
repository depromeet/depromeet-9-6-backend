package com.depromeet.articlereminder.exception;

public class BadgeNotFoundException extends RuntimeException {
    public BadgeNotFoundException() {
        super();
    }

    public BadgeNotFoundException(String message) {
        super(message);
    }

    public BadgeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadgeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BadgeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

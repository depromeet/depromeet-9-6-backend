package com.depromeet.articlereminder.exception;

public class LinkModifiedByInvalidUserException extends RuntimeException {
    public LinkModifiedByInvalidUserException() {
        super();
    }

    public LinkModifiedByInvalidUserException(String message) {
        super(message);
    }

    public LinkModifiedByInvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkModifiedByInvalidUserException(Throwable cause) {
        super(cause);
    }

    protected LinkModifiedByInvalidUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

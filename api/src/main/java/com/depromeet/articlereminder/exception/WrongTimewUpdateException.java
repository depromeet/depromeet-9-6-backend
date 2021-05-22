package com.depromeet.articlereminder.exception;

public class WrongTimewUpdateException extends RuntimeException {
    public WrongTimewUpdateException() {
        super();
    }

    public WrongTimewUpdateException(String message) {
        super(message);
    }

    public WrongTimewUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongTimewUpdateException(Throwable cause) {
        super(cause);
    }

    protected WrongTimewUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

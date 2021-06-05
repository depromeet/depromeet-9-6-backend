package com.depromeet.articlereminder.exception;

public class LinkHasBeenAlreadyReadException extends RuntimeException {
    public LinkHasBeenAlreadyReadException() {
        super();
    }

    public LinkHasBeenAlreadyReadException(String message) {
        super(message);
    }

    public LinkHasBeenAlreadyReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkHasBeenAlreadyReadException(Throwable cause) {
        super(cause);
    }

    protected LinkHasBeenAlreadyReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

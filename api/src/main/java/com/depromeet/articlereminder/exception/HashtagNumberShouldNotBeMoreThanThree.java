package com.depromeet.articlereminder.exception;

public class HashtagNumberShouldNotBeMoreThanThree extends RuntimeException {
    public HashtagNumberShouldNotBeMoreThanThree(String s) {
    }

    public HashtagNumberShouldNotBeMoreThanThree() {
        super();
    }

    public HashtagNumberShouldNotBeMoreThanThree(String message, Throwable cause) {
        super(message, cause);
    }

    public HashtagNumberShouldNotBeMoreThanThree(Throwable cause) {
        super(cause);
    }

    protected HashtagNumberShouldNotBeMoreThanThree(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

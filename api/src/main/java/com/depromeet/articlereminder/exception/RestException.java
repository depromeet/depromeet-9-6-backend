package com.depromeet.articlereminder.exception;

public class RestException extends RuntimeException {
    String status;
    String comment;

    RestException() {

    }

    public RestException(String status, String comment) {
        super();
        this.status = status;
        this.comment = comment;
    }

    public RestException(String comment) {
        super();
        this.comment = comment;
    }

    public RestException(ExceptionCode ec) {
        super();
        this.status = ec.status;
        this.comment = ec.comment;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return getComment();
    }
}

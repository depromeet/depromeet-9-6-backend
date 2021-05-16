package com.depromeet.articlereminder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // TODO Exception Code 정의
    NOT_FOUND_EXCEPTION("402", "not_found_exception");

    String status;
    String comment;

    ExceptionCode(String status, String comment) {
        this.status = status;
        this.comment = comment;
    }
}

package com.depromeet.articlereminder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // TODO Exception Code 정의
    LINK_NOT_FOUND_EXCEPTION("400", "해당 링크를 찾을 수 없습니다"),
    USER_NOT_FOUND_EXCEPTION("400", "해당 사용자를 찾을 수 없습니다"),
    LINK_MODIFIED_BY_INVALID_USER_EXCEPTION("402", "링크를 수정할 수 없는 사용자입니다.");

    String status;
    String comment;

    ExceptionCode(String status, String comment) {
        this.status = status;
        this.comment = comment;
    }
}

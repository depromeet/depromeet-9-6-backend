package com.depromeet.articlereminder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // TODO Exception Code 정의
    LINK_NOT_FOUND_EXCEPTION("400", "해당 링크를 찾을 수 없습니다"),
    USER_NOT_FOUND_EXCEPTION("400", "해당 사용자를 찾을 수 없습니다"),
    ALARM_NOT_FOUND_EXCEPTION("400", "해당 알람을 찾을 수 없습니다"),
    HASHTAG_SHOULD_NOT_BE_MORE_THAN_THREE("401", "하나의 링크에 저장할 수 있는 해시태그 개수는 최대 3개입니다."),
    LINK_MODIFIED_BY_INVALID_USER_EXCEPTION("402", "링크를 수정할 수 없는 사용자입니다."),
    LINK_HAS_BEEN_READ_EXCEPTION("402", "해당 링크는 이미 읽음 완료 표시된 링크입니다."),
    ALARM_MODIFIED_BY_INVALID_USER_EXCEPTION("402", "알람을 수정할 수 없는 사용자입니다."),
    ;

    String status;
    String comment;

    ExceptionCode(String status, String comment) {
        this.status = status;
        this.comment = comment;
    }
}

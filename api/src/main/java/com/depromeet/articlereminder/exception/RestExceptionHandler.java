package com.depromeet.articlereminder.exception;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.depromeet.articlereminder.exception.ExceptionCode;

import static com.depromeet.articlereminder.exception.ExceptionCode.*;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest webRequest) {
        log.error(" exception class : " + ex.getClass());
        log.error(" exception message : " + ex.getMessage());

        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            log.error(stackTraceElement.toString());
        }

        return new ResponseEntity<>(BaseResponse.of("500", ex.getMessage(), ex.getStackTrace()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({LinkNotFoundException.class})
    public ResponseEntity<Object> handleLinkNotFoundException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        LINK_NOT_FOUND_EXCEPTION.status, LINK_NOT_FOUND_EXCEPTION.comment, null),
                        new HttpHeaders(),
                        HttpStatus.OK
        );
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        USER_NOT_FOUND_EXCEPTION.status, USER_NOT_FOUND_EXCEPTION.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({AlarmNotFoundException.class})
    public ResponseEntity<Object> handleAlarmNotFoundException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        ALARM_NOT_FOUND_EXCEPTION.status, ALARM_NOT_FOUND_EXCEPTION.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({HashtagNumberShouldNotBeMoreThanThree.class})
    public ResponseEntity<Object> handleHashtagNumberShouldNotBeMoreThanThreeException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        HASHTAG_SHOULD_NOT_BE_MORE_THAN_THREE.status, HASHTAG_SHOULD_NOT_BE_MORE_THAN_THREE.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({LinkHasBeenAlreadyReadException.class})
    public ResponseEntity<Object> handleLinkHasBeenAlreadyReadException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        LINK_HAS_BEEN_READ_EXCEPTION.status, LINK_HAS_BEEN_READ_EXCEPTION.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({LinkModifiedByInvalidUserException.class})
    public ResponseEntity<Object> handleLinkModifiedByInvalidUserException() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        LINK_MODIFIED_BY_INVALID_USER_EXCEPTION.status, LINK_MODIFIED_BY_INVALID_USER_EXCEPTION.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    @ExceptionHandler({AlarmModifiedByInvalidUserException.class})
    public ResponseEntity<Object> handleAlarmModifiedBy() {
        return new ResponseEntity<>(
                BaseResponse.of(
                        ALARM_MODIFIED_BY_INVALID_USER_EXCEPTION.status, ALARM_MODIFIED_BY_INVALID_USER_EXCEPTION.comment, null),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

}

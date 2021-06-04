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

        return new ResponseEntity<>("error", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
}

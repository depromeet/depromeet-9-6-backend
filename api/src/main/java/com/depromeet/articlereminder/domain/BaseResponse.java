package com.depromeet.articlereminder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseResponse<T> {

    public static final BaseResponse<String> OK = new BaseResponse<>("", "", "OK");

    private final String status;
    private final String comment;
    private final T data;

    public static <T> BaseResponse<T> of(T data) {
        return new BaseResponse<>("", "", data);
    }
}

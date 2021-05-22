package com.depromeet.articlereminder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ApiModel(description = "ResponseBody")
public class BaseResponse<T> {

    public static final BaseResponse<String> OK = new BaseResponse<>("", "", "OK");

    @ApiModelProperty(notes = "Response status (미리 정의된 status)",
            example = "201",
            required = true,
            position = 0)
    private final String status;

    @ApiModelProperty(notes = "Response message",
            example = "링크 등록에 성공했습니다.",
            required = true,
            position = 1)
    private final String comment;

    @ApiModelProperty(notes = "Actual data",
            example = "각 DTO",
            required = true,
            position = 2)
    private final T data;

    public static <T> BaseResponse<T> of(T data) {
        return new BaseResponse<>("", "", data);
    }

    public static <T> BaseResponse<T> of(String status, String comment, T data) {
        return new BaseResponse<>(status, comment, data);
    }
}

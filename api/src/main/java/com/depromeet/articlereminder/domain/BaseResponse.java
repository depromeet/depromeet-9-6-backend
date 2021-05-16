package com.depromeet.articlereminder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BaseResponse<T> {

    @JsonInclude
    @JsonProperty("status")
    long status;

    @JsonProperty("comment")
    String comment;

    @JsonProperty("data")
    T data;

    public BaseResponse(T t) {
        data = t;
    }

    @JsonIgnoreType
    public static class RESULT {
        public static final long OK = 0;
    }
}

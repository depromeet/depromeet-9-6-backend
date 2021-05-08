package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "링크 내 hashtagDTO")
@Builder
@Getter
public class HashtagDTO {
    @ApiModelProperty(notes = "해시 태그 id",
            example = "1",
            required = true,
            position = 0)
    public Long hashtagId; // 해시태그 id


    @ApiModelProperty(notes = "해시 태그",
            example = "디자인",
            required = true,
            position = 1)
    public String hashtagName; // 해시태그명

    @ApiModelProperty(notes = "해시 태그 등록 시각",
            example = "2021-05-01 21:33:22",
            required = true,
            position = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime createdAt; // 등록 시각 (생성 시각)
}

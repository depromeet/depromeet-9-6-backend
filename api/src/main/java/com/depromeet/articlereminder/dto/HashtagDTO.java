package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
@Getter
public class HashtagDTO {
    private Long hashtagId; // 해시태그 id
    private String hashtagName; // 해시태그명

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 등록 시각 (생성 시각)
}

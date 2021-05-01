package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class LinkResponse {
    private Long linkId; // 링크 id
    private Long userId; // 사용자 id
    private String linkURL; // 링크 url
    private boolean isRead; // 읽음 상태
    private boolean hasReminder; // 개별 알람 설정 여부
    private List<HashtagDTO> hashtags; // 해시 태크 id

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 등록 시각 (생성 시각)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime completedAt; // 읽음 완료 시각
}

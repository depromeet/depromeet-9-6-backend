package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class LinkAlarmResponse {
    private Long linkId; // 링크 id
    private Long alarmId; // 개별 알람 id

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime notifyTime; // 알람 시각

    private boolean isEnabled; // 활성화 여부

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 알람이 생성된 시각
}

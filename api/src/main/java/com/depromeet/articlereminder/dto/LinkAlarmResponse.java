package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LinkAlarmResponse {
    private Long linkId;
    private Long alarmId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime notifyTime;

    private boolean hasAlarm; // 활성화 여부
}

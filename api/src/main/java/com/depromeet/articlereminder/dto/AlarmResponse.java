package com.depromeet.articlereminder.dto;

import com.depromeet.articlereminder.domain.RepeatedDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AlarmResponse {

    private Long alarmId; // 알람 id
    private Long userId; // 사용자 id

    private String notifyTime; // 알람 시각

    private RepeatedDate repeatedDate; // 반복 요일 (매일 / 공휴일 제외 매일 / 평일 / 공휴일 제외 평일 / 주말 / 공휴일 제외 주말

    private boolean isEnabled; // 활성화 여부

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 알람이 생성된 시각
}

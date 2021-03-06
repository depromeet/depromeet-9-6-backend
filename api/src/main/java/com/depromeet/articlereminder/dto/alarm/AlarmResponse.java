package com.depromeet.articlereminder.dto.alarm;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "어플 알람 조회 시 ResponseBody")
@Data
public class AlarmResponse {

    @ApiModelProperty(notes = "알람 id",
            example = "1",
            required = true,
            position = 0)
    private Long alarmId; // 알람 id

    @ApiModelProperty(notes = "사용자 id",
            example = "1",
            required = true,
            position = 1)
    private Long userId; // 사용자 id

    @ApiModelProperty(notes = "알람 시각",
            example = "08:30",
            required = true,
            position = 2)
    private String notifyTime; // 알람 시각

    @ApiModelProperty(notes = "반복 요일",
            example = "WEEKDAYS",
            allowableValues = "EVERYDAY, EVERYDAY_EXCEPT_HOLIDAYS, WEEKDAYS, WEEKDAYS_EXCEPT_HOLIDAYS, WEEKENDS, WEEKENDS_EXCEPT_HOLIDAYS",
            required = true,
            position = 3)
    private RepeatedDate repeatedDate; // 반복 요일 (매일 / 공휴일 제외 매일 / 평일 / 공휴일 제외 평일 / 주말 / 공휴일 제외 주말

    @ApiModelProperty(notes = "알람 활성화 여부",
            example = "true",
            required = true,
            position = 4)
    private boolean isEnabled; // 활성화 여부

    @ApiModelProperty(notes = "등록 시각 (알람이 생성된 시각)",
            example = "2021-05-05 11:33:22",
            required = true,
            position = 5)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 알람이 생성된 시각

    public AlarmResponse(Alarm alarm) {
        alarmId = alarm.getId();
        userId = alarm.getMember().getId();
        notifyTime = alarm.getNotifyTime();
        repeatedDate = alarm.getRepeatedDate();
        isEnabled = alarm.getAlarmStatus().toString().equals("ENABLED");
        createdAt = alarm.getCreatedAt();
    }
}

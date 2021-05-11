package com.depromeet.articlereminder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@ApiModel(description = "어플 알람 추가 시 RequestBody")
@Data
public class AlarmDTO {
    @ApiModelProperty(
            dataType = "string",
            notes = "알람 시각 (hh:mm 의 포맷)",
            example = "08:30" ,
            required = true)
    private String notifyTime; // 알람 시각

    @ApiModelProperty(
            dataType = "string",
            allowableValues = "EVERYDAY, EVERYDAY_EXCEPT_HOLIDAYS, WEEKDAYS, WEEKDAYS_EXCEPT_HOLIDAYS, WEEKENDS, WEEKENDS_EXCEPT_HOLIDAYS",
            notes = "반복 요일(매일 / 매일 & 공휴일 제외 / 평일 / 평일 & 공휴일 제외 / 주말 / 주말 & 공휴일 제외)", example = "EVERYDAY",required = true)
    private String repeatedDate; // 반복 요일

    @ApiModelProperty(
            dataType = "boolean",
            value = "활성화 여부 (true / false)", required = true)
    private boolean isEnabled; // 활성화 여부
}

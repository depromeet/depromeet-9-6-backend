package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(description = "개별 링크 알람 조회 시 ResponseBody")
@Builder
@Getter
public class LinkAlarmResponse {

    @ApiModelProperty(notes = "링크 id",
            example = "1",
            required = true,
            position = 0)
    private Long linkId; // 링크 id


    @ApiModelProperty(notes = "개별 링크 알람 id",
            example = "1",
            required = true,
            position = 1)
    private Long alarmId; // 개별 알람 id

    @ApiModelProperty(notes = "개별 링크 알람 시각",
            example = "2021-05-11 11:33",
            required = true,
            position = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime notifyTime; // 알람 시각

    @ApiModelProperty(notes = "개별 알람 활성화 여부",
            example = "true",
            required = true,
            position = 3)
    private boolean isEnabled; // 활성화 여부

    @ApiModelProperty(notes = "등록 시각 (알람이 생성된 시각)",
            example = "2021-05-05 11:33:22",
            required = true,
            position = 4)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 알람이 생성된 시각
}

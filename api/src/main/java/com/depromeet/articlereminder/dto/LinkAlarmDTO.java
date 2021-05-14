package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@ApiModel(description = "개별 링크 알람 추가 시 RequestBody")
@Data
public class LinkAlarmDTO {

    @ApiModelProperty(notes = "개별 링크 알람 받을 시각",
            example = "2021-05-05 21:30",
            required = true,
            position = 0)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime notifiyTime; // 알림 받을 시각
}

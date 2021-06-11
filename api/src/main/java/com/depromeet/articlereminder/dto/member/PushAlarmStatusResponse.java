package com.depromeet.articlereminder.dto.member;

import com.depromeet.articlereminder.domain.member.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PushAlarmStatusResponse {

    @ApiModelProperty(notes = "푸시 알림 활성화 여부",
            example = "true",
            required = true,
            position = 1)
    private boolean alarmEnabled; // 푸시 알림 활성화 여부

    public PushAlarmStatusResponse(Member updatedMember) {
        alarmEnabled = updatedMember.getStatus().toString().equals("ENABLED");
    }
}

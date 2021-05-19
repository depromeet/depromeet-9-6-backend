package com.depromeet.articlereminder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "MyPage Response")
@Builder
@Getter
public class UserMyPageResponse {

    @ApiModelProperty(notes = "사용자 id",
            example = "1",
            required = true,
            position = 0)
    private Long userId; // 사용자 id

    @ApiModelProperty(notes = "가입 일시",
            example = "2021-04-01 11:33:22",
            required = true,
            position = 1)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 가입 일시

    @ApiModelProperty(notes = "사용자 닉네임",
            example = "칠성파",
            required = true,
            position = 2)
    private String nickName; // 사용자 닉네임

    @ApiModelProperty(notes = "총 읽은 아티클 갯수",
            example = "555",
            required = true,
            position = 3)
    private Long totalReadCount; // 총 읽은 개수

    @ApiModelProperty(notes = "획득한 총 포인트",
            example = "1055",
            required = true,
            position = 3)
    private Long totalPoint; // 총 포인트

    @ApiModelProperty(notes = "이번 시즌 읽은 갯수",
            example = "72",
            required = true,
            position = 4)
    private Long seasonCount; // 이번 시즌 읽은 개수

    @ApiModelProperty(notes = "푸시 알림 활성화 여부",
            example = "true",
            required = true,
            position = 5)
    private boolean alarmEnabled; // 푸시 알림 활성화 여부

    private BadgeResponse badge; // 사용자의 포인트 뱃지
}

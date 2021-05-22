package com.depromeet.articlereminder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(description = "TOP 10 랭킹")
@Builder
@Getter
public class UserRankingResponse {

    @ApiModelProperty(notes = "순위",
            example = "1",
            required = true,
            position = 0)
    private int rank; // 순위

    @ApiModelProperty(notes = "사용자 id",
            example = "1",
            required = true,
            position = 1)
    private Long userId; // 사용자 id

    @ApiModelProperty(notes = "사용자 닉네임",
            example = "칠성파",
            required = true,
            position = 2)
    private String nickName; // 사용자 닉네임

    @ApiModelProperty(notes = "이번 시즌 획득한 총 포인트",
            example = "1055",
            required = true,
            position = 3)
    private Long totalPoint; // 이번 시즌 획득한 총 포인트

    @ApiModelProperty(notes = "이번 시즌 읽은 갯수",
            example = "72",
            required = true,
            position = 4)
    private Long seasonCount; // 이번 시즌 읽은 개수

    private BadgeResponse badge; // 사용자의 포인트 뱃지
}

package com.depromeet.articlereminder.dto.ranking;


import com.depromeet.articlereminder.dto.badge.BadgeDTO;
import com.depromeet.articlereminder.dto.badge.BadgeResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import java.util.*;

@ApiModel(description = "랭킹 조회 시 ResponseBody")
@Builder
@Getter
public class RankingResponse {

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

    @ApiModelProperty(notes = "지난 시즌 대비 순위 변동",
            example = "+ / - / 0",
            required = true,
            position = 5)
    private String fluctuation; // 지난 시즌 대비 변동 여부 ( +,-,0)

    private BadgeDTO badge; // 사용자의 포인트 뱃지

    @ApiModelProperty(notes = "이번 시즌 TOP10",
            example = "TOP 10 리스트",
            required = true,
            position = 5)
    private List<UserRankingResponse> top10;

}

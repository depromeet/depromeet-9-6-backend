package com.depromeet.articlereminder.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RakingResponse {

    private Long rank; // 순위
    private Long userId; // 사용자 id
    private String nickName; // 사용자 닉네임
    private Long totalReadCount; // 총 읽은 개수
    private Long totalPoint; // 총 포인트
    private Long seasonCount; // 이번 시즌 읽은 개수
    private Long fluctuation; // 지난 시즌 대비 변동값

    private PointBadgeResponse badge; // 사용자의 포인트 뱃지
}

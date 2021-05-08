package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.BadgeCategory;
import com.depromeet.articlereminder.dto.BadgeResponse;
import com.depromeet.articlereminder.dto.RankingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"ranking"})
@RestController
@RequestMapping(value = "/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
public class RankingController {

    @ApiOperation("랭킹 TOP 10을 조회합니다. 인증이 필요한 요청입니다. 순위별 오름차순으로 정렬")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("")
    public ResponseEntity<List<RankingResponse>> getRankingTop10() {
        BadgeResponse pointBadge = BadgeResponse.builder()
                .badgeId(1L)
                .badgeName("0~500 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5000점 이하인 경우 획득")
                .createdAt(LocalDateTime.now())
                .build();

        RankingResponse top1 = RankingResponse.builder()
                .rank(1)
                .userId(10L)
                .totalPoint(9999L)
                .seasonCount(100L)
                .nickName("일성파")
                .badge(pointBadge)
                .build();
        RankingResponse top2 = RankingResponse.builder()
                .rank(2)
                .userId(9L)
                .totalPoint(9995L)
                .seasonCount(99L)
                .nickName("이성파")
                .badge(pointBadge)
                .build();
        RankingResponse top3 = RankingResponse.builder()
                .rank(3)
                .userId(8L)
                .totalPoint(9990L)
                .seasonCount(98L)
                .nickName("삼성파")
                .badge(pointBadge)
                .build();
        RankingResponse top4 = RankingResponse.builder()
                .rank(4)
                .userId(7L)
                .totalPoint(9800L)
                .seasonCount(90L)
                .nickName("사성파")
                .badge(pointBadge)
                .build();
        RankingResponse top5 = RankingResponse.builder()
                .rank(5)
                .userId(6L)
                .totalPoint(9750L)
                .seasonCount(85L)
                .nickName("오성파")
                .badge(pointBadge)
                .build();
        RankingResponse top6 = RankingResponse.builder()
                .rank(6)
                .userId(5L)
                .totalPoint(9600L)
                .seasonCount(70L)
                .nickName("육성파")
                .badge(pointBadge)
                .build();
        RankingResponse top7 = RankingResponse.builder()
                .rank(7)
                .userId(4L)
                .totalPoint(9400L)
                .seasonCount(60L)
                .nickName("칠성파")
                .badge(pointBadge)
                .build();
        RankingResponse top8 = RankingResponse.builder()
                .rank(8)
                .userId(3L)
                .totalPoint(5000L)
                .seasonCount(50L)
                .nickName("팔성파")
                .badge(pointBadge)
                .build();
        RankingResponse top9 = RankingResponse.builder()
                .rank(9)
                .userId(2L)
                .totalPoint(4000L)
                .seasonCount(30L)
                .nickName("구성파")
                .badge(pointBadge)
                .build();
        RankingResponse top10 = RankingResponse.builder()
                .rank(10)
                .userId(11L)
                .totalPoint(3532L)
                .seasonCount(21L)
                .nickName("십성파")
                .badge(pointBadge)
                .build();

        List<RankingResponse> ranking = Stream.of(top1, top2, top3, top4, top5, top6, top7, top8, top9, top10).collect(Collectors.toList());
        return ResponseEntity.ok(ranking);
    }

    @ApiOperation("특정 사용자의 랭킹을 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("{id}")
    public ResponseEntity<RankingResponse> getRanking(@PathVariable Long id) {
        BadgeResponse pointBadge = BadgeResponse.builder()
                .badgeId(1L)
                .badgeName("0~500 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5000점 이하인 경우 획득")
                .createdAt(LocalDateTime.now())
                .build();

        RankingResponse rankingResponse = RankingResponse.builder()
                .rank(152)
                .userId(1L)
                .totalPoint(4235L)
                .seasonCount(53L)
                .nickName("칠성파")
                .badge(pointBadge)
                .fluctuation(15L)
                .build();
        return ResponseEntity.ok(rankingResponse);
    }
}

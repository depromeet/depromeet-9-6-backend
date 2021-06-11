package com.depromeet.articlereminder.domain.badge;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "뱃지 히스토리 Response")
@Getter
public class BadgeHistoryResponse {

    @ApiModelProperty(notes = "뱃지 id",
            example = "1",
            required = true,
            position = 0)
    private Long badgeId; // 뱃지 id


    @ApiModelProperty(notes = "시즌 포인트",
            example = "5235",
            required = true,
            position = 1)
    private int seasonalPoint; // 시즌 포인트

    @ApiModelProperty(notes = "뱃지 URL",
            example = "https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
            required = true,
            position = 2)
    private String badgeURL; // 뱃지 URL

    @ApiModelProperty(notes = "뱃지 이름",
            example = "0~5000 포인트 뱃지",
            required = true,
            position = 3)
    private String badgeName; // 뱃지 이름

    @ApiModelProperty(notes = "뱃지 카테고리",
            example = "POINT",
            allowableValues = "POINT, SEASON",
            required = true,
            position = 4)
    private BadgeCategory category; // 뱃지 카테고리

    @ApiModelProperty(notes = "뱃지 획득 조건",
            example = "포인트가 5000점 이하인 경우 획득",
            required = true,
            position = 5)
    private String conditions; // 뱃지 획득 조건

    @ApiModelProperty(notes = "뱃지 등록 시각 (뱃지 획득 시각)",
            example = "2021-05-05 11:33:22",
            required = true,
            position = 6)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 뱃지 획득 일자

    public BadgeHistoryResponse(MemberBadge memberBadge) {
        badgeId = memberBadge.getId();
        seasonalPoint = 5235;
        badgeName = memberBadge.getBadge().getName();
        badgeURL = memberBadge.getBadge().getImageUrl();
        category = memberBadge.getBadge().getBadgeCategory();
        conditions = memberBadge.getBadge().getConditions();
        createdAt = memberBadge.getCreatedAt();
    }
}

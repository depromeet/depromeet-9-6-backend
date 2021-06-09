package com.depromeet.articlereminder.domain.badge;


import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.depromeet.articlereminder.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "imageUrl", "badgeCategory", "conditions", "seasonInfo"})
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id; // 뱃지 id

    private String name; // 뱃지 이름

    private String imageUrl; // 뱃지 이미지 url

    @Enumerated(EnumType.STRING)
    private BadgeCategory badgeCategory; // 시즌 뱃지인지 포인트 뱃지인지 구분

    private String conditions; // 획득 조건

    private String seasonInfo; // 시즌 정보 (2021년 5월 / 2021년 6월 .... )


    public static Badge createBadge(String name, String imageUrl, BadgeCategory badgeCategory, String conditions, String seasonInfo) {
        Badge badge = new Badge();

        badge.changeName(name);
        badge.changeImageUrl(imageUrl);
        badge.changeBadgeCategory(badgeCategory);
        badge.changeConditions(conditions);
        badge.changeSeasonInfo(seasonInfo);

        return badge;
    }

    private void changeSeasonInfo(String seasonInfo) {
        this.seasonInfo = seasonInfo;
    }


    private void changeConditions(String conditions) {
        this.conditions = conditions;
    }

    private void changeBadgeCategory(BadgeCategory badgeCategory) {
        this.badgeCategory = badgeCategory;
    }

    private void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void changeName(String name) {
        this.name = name;
    }

}

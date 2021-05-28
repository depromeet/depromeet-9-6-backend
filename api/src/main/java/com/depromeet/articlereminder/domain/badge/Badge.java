package com.depromeet.articlereminder.domain.badge;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id; // 뱃지 id

    private String name; // 뱃지 이름

    private String imageUrl; // 뱃지 이미지 url

    @Enumerated(value = EnumType.STRING)
    private BadgeCategory badgeCategory; // 시즌 뱃지인지 포인트 뱃지인지 구분

    private String conditions; // 획득 조건

    private String seasonInfo; // 시즌 정보 (2021년 5월 / 2021년 6월 .... )

    @CreatedDate
    private LocalDateTime createdAt; // 생성 일시

}

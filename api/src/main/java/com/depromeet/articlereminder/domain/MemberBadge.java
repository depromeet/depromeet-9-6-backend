package com.depromeet.articlereminder.domain;


import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBadge { // 뱃지 획득 테이블 Acqustition

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge; // 뱃지

    @CreatedDate
    private LocalDateTime createdAt; // 생성 일시

}

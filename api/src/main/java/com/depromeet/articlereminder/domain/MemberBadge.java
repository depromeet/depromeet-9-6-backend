package com.depromeet.articlereminder.domain;


import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
public class MemberBadge extends BaseEntity { // 뱃지 획득 테이블 Acqustition

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge; // 뱃지

    public void changeMember(Member member) {
        this.member = member;
    }


    public static MemberBadge createMemberBadge(Member member, Badge badge) {
        MemberBadge memberBadge = new MemberBadge();
        memberBadge.obtainBadge(member, badge);

        return memberBadge;
    }

    private void obtainBadge(Member member, Badge badge) {
        this.member = member;
        this.badge = badge;
    }

    public MemberBadge changeBadge(Badge badge) {
        this.badge = badge;
        return this;
    }
}

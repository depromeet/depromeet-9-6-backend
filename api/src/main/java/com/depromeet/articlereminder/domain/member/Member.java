package com.depromeet.articlereminder.domain.member;

import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(of = {"id", "email", "name", "token", "tokenStartTime", "tokenExpiredTime", "totalReadCount", "totalPoint", "lastAccessedAt", "status"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 사용자 id

    private String email; // 이메일

    private String name; // 닉네임

    private String token; // 토큰값

    private LocalDateTime tokenStartTime; // 토큰 시작 시각??

    private LocalDateTime tokenExpiredTime; // 토큰 만료 시각

    private String pushToken;

    private int totalReadCount; // 총 읽은 아티클 수

    private int totalPoint; // 총 획득한 포인트

    private LocalDateTime lastAccessedAt; // 최근 접속 일시

    @Enumerated(EnumType.STRING)
    private AlarmStatus status; // 어플 알림 활성화 여부

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;


//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Link> links = new ArrayList<>(); // 멤버 <--> 링크 양방향 연관관계

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Alarm> alarms = new ArrayList<>(); // 멤버 <--> 알람 양방향 연관관계

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberBadge> memberBadges = new ArrayList<>(); // 멤버 <--> 뱃지 양방향 연관관계

    public Member(){}

    public Member(String name) {
        this.name = name;
    }

    /**
     * 어플 알림 설정 메서드
     */
    public Member changeAlarmStatus(AlarmStatus status) {
        this.status = status;
        return this;
    }

    public void addMemberBadge(MemberBadge memberBadge) {
        memberBadges.add(memberBadge);
        memberBadge.changeMember(this);
    }

    public void changeTotalPoint(int point) {
        this.totalPoint += point;
    }

    public void changeTotalCount() {
        this.totalReadCount += 1;
    }

    public void changeInitialAlarmStatus() {
        this.status = AlarmStatus.ENABLED;
    }


}

package com.depromeet.articlereminder.domain.member;

import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
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
@ToString(of = {"id", "loginId", "name", "token", "tokenStartTime", "tokenExpiredTime", "totalReadCount", "totalPoint", "lastAccessedAt", "status"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 사용자 id

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // 소셜 로그인 타입

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    //20210607 topojs8 oauth 인증시 메일 비동의 할수 있어서 email -> long값으로 대체
    private String loginId;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberBadge> memberBadges = new ArrayList<>(); // 멤버 <--> 뱃지 양방향 연관관계

    public Member(){}

    public Member(String name) {
        this.name = name;
    }

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

    public void changeDeviceType(String deviceType) {
        this.deviceType = DeviceType.valueOf(deviceType);
    }

    public void changeSocialType(String socialType) {
        this.socialType = SocialType.valueOf(socialType);
    }

    public void changePushToken(String pushToken) {
        this.pushToken = pushToken;
    }

}

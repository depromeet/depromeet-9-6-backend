package com.depromeet.articlereminder.domain.member;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.link.Link;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 사용자 id

    private String email; // 이메일

    private String name; // 닉네임

    private String token; // 토큰값

    private Date tokenStartTime; // 토큰 시작 시각??

    private Date tokenExpiredTime; // 토큰 만료 시각

    private int totalReadCount; // 총 읽은 아티클 수

    private int totalPoint; // 총 획득한 포인트

    private LocalDateTime lastAccessedAt; // 최근 접속 일시

    @Enumerated(value = EnumType.STRING)
    private AlarmStatus status; // 어플 알림 활성화 여부

    @CreatedDate
    private LocalDateTime createdAt; // 생성 일시


//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Link> links = new ArrayList<>(); // 멤버 <--> 링크 양방향 연관관계

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Alarm> alarms = new ArrayList<>(); // 멤버 <--> 알람 양방향 연관관계

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Badge> badges = new ArrayList<>(); // 멤버 <--> 뱃지 양방향 연관관계

}
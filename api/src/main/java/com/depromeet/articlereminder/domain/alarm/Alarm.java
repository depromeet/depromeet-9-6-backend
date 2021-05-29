package com.depromeet.articlereminder.domain.alarm;

import com.depromeet.articlereminder.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;


@Entity
@Getter
@Setter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id; // 알람 id

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    private String notifyTime; // 알림 시각 (08:30)

    @Enumerated(value = EnumType.STRING)
    private AlarmStatus alarmStatus; // 알람 활성화 여부

    @Enumerated(value = EnumType.STRING)
    private RepeatedDate repeatedDate; // 반복 일자

    @CreatedDate
    private LocalDateTime createdAt; // 생성 일시

}

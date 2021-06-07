package com.depromeet.articlereminder.domain.alarm;

import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.exception.AlarmModifiedByInvalidUserException;
import com.depromeet.articlereminder.exception.LinkModifiedByInvalidUserException;
import com.google.api.client.util.DateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "notifyTime", "alarmStatus", "repeatedDate"})
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id; // 알람 id

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    private LocalDateTime notifyTime; // 알림 시각 (08:30)

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus; // 알람 활성화 여부

    @Enumerated(EnumType.STRING)
    private RepeatedDate repeatedDate; // 반복 일자


    /**
     * 알람 생성
     * @param member
     * @param notifyTime
     * @param repeatedDate
     * @return
     */
    public static Alarm createAlarm(Member member, LocalDateTime notifyTime, String repeatedDate) {
        Alarm alarm = new Alarm();
        alarm.changeAlarmStatus(AlarmStatus.ENABLED);
        alarm.changeNotifyTime(notifyTime);
        alarm.changeRepeatedDate(RepeatedDate.valueOf(repeatedDate));
        alarm.changeMember(member);

        return alarm;
    }

    public Alarm update(Member member, String alarmStatus , LocalDateTime notifyTime, String repeatedDate) {
        this.isValidUser(member);

        this.changeAlarmStatus(AlarmStatus.valueOf(alarmStatus));
        this.changeNotifyTime(notifyTime);
        this.changeRepeatedDate(RepeatedDate.valueOf(repeatedDate));

        return this;
    }

    public Alarm delete(Member member) {
        this.isValidUser(member);

        // FIXME FREE 필요?
        return this;
    }

    private void changeMember(Member member) {
        this.member = member;
    }

    private void changeRepeatedDate(RepeatedDate repeatedDate) {
        this.repeatedDate = repeatedDate;
    }

    private void changeNotifyTime(LocalDateTime notifyTime) {
        this.notifyTime = notifyTime;
    }

    private void changeAlarmStatus(AlarmStatus status) {
        this.alarmStatus = status;
    }

    private void isValidUser(Member member) {
        if (this.member != null && !this.member.equals(member)) {
            throw new AlarmModifiedByInvalidUserException();
        }
    }



}

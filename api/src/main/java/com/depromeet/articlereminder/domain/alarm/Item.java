package com.depromeet.articlereminder.domain.alarm;

import com.depromeet.articlereminder.domain.AlramState;
import com.depromeet.articlereminder.domain.RepeatedDate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // joined 정규화스타일, single 통짜 테이블, per 테이블 나누기
@DiscriminatorColumn(name = "dtype") // 구분값.
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String userEmail;
    private Date notifyTime;
    private Date createTime;

    /**
     * 푸쉬 알림 시간 수정
     *
     * @param RepeatedDate
     */
    public void updateNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }
}

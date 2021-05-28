package com.depromeet.articlereminder.domain.alarm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class PushAlarm extends Alarm {

    private RepeatedDate repeatedDate;
    private AlarmState alramState;
}

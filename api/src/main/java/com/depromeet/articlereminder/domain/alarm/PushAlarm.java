package com.depromeet.articlereminder.domain.alarm;

import com.depromeet.articlereminder.domain.AlramState;
import com.depromeet.articlereminder.domain.RepeatedDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class PushAlarm extends Alarm {

    private RepeatedDate repeatedDate;
    private AlramState alramState;
}

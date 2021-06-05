package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.dto.alarm.AlarmRequest;

import java.util.List;

public interface AlarmService {
    List<Alarm> findAlarmsByUserId(Long userId);

    Alarm saveAlarm(Long userId, AlarmRequest alarmRequest);

    Alarm getAlarm(Long userId, Long alarmId);

    Alarm updateAlarm(Long userId, Long alarmId, AlarmRequest alarmRequest);

    void deleteAlarm(Long userId, Long alarmId);

    List<Alarm> findAllAlarams();

}

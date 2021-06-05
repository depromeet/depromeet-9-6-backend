package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.alarm.Alarm;

import java.util.List;

public interface AlarmService {
    List<Alarm> findAlarmsByUserId(Long userId);
}

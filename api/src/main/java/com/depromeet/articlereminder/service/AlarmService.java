package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Transactional
    public Alarm saveAlarm(Alarm item) {
       return alarmRepository.save(item);
    }

    @Transactional // 준영속 엔티티 변경감지(더티체크) 방식으로 수정, em.merge()와 동작방식 동일
    public Alarm updateAlarm(Long itemId){
        Alarm findItem = alarmRepository.findOne(itemId); // 바뀐걸 jpa가 아는 시점
        return findItem;
    }

    public List<Alarm> findAlarms() {
        return alarmRepository.findAll();
    }

    public Alarm findOne(Long itemId) {
        return alarmRepository.findOne(itemId);
    }

}
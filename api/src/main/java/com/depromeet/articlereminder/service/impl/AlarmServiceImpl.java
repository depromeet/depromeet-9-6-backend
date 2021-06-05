package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.alarm.AlarmRequest;
import com.depromeet.articlereminder.exception.AlarmNotFoundException;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.AlarmRepository;
import com.depromeet.articlereminder.repository.MemberRepository;
import com.depromeet.articlereminder.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Alarm> findAlarmsByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return alarmRepository.findAllByMember(member);
    }

    @Override
    public Alarm saveAlarm(Long userId, AlarmRequest alarmRequest) {
        Member member = memberRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException());

        Alarm alarm = Alarm.createAlarm(member, alarmRequest.getNotifyTime(), alarmRequest.getRepeatedDate());
        Alarm savedAlarm = alarmRepository.save(alarm);

        return alarmRepository.findById(savedAlarm.getId()).get(); // FIXME Optional .get() 고치기
    }

    @Override
    public Alarm getAlarm(Long userId, Long alarmId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new AlarmNotFoundException());

        return alarm;
    }
}

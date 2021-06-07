package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.FirebaseCloudMessageService;
import com.depromeet.articlereminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final AlarmService alarmService;

    // 5분마다 확인
    @Scheduled(cron = "0 */5 * * * *")
    //@Scheduled(fixedRateString = "5", initialDelay = 10000)
    public ResponseEntity<String> pushFcmMessage() throws Exception {

        List<Alarm> alarmList = alarmService.findAllAlarams();

        ResponseEntity<String> response = null;
        for(int i=0; i< alarmList.size(); ++i){
            if(alarmList.get(i).getAlarmStatus()== AlarmStatus.ENABLED)
            {
                alarmList.get(i).getMember().getName();
                response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                        alarmList.get(i).getMember().getToken(),"통계성 알람입니다.", "통계성 알람 내용입니다.");
            }
        }

        return response;
    }
}

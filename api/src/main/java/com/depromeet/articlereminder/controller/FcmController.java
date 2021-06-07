package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.FirebaseCloudMessageService;
import com.depromeet.articlereminder.service.MemberService;
import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final AlarmService alarmService;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;
    private static final Integer WEEKEND_START_FRIDAY_CUT_OFF_HOUR = 22;
    private static final Integer WEEKEND_END_SUNDAY_CUT_OFF_HOUR = 23;
    private static List<Integer> weekendDaysList = Arrays.asList(FRIDAY, SATURDAY, SUNDAY);
    // 5분마다 확인
    @Scheduled(cron = "0 */5 * * * *")
    //@Scheduled(fixedRateString = "5", initialDelay = 10000)
    public ResponseEntity<String> pushFcmMessage() throws Exception {

        List<Alarm> alarmList = alarmService.findAllAlarams();
        ResponseEntity<String> response = null;


        for (int i = 0; i < alarmList.size(); ++i) {
            if (alarmList.get(i).getAlarmStatus() == AlarmStatus.ENABLED) {
                switch (alarmList.get(i).getRepeatedDate()){
                    case EVERYDAY:
                        if( alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().minusMinutes(1L)) &&
                                alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().plusMinutes(1L)))
                        response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                alarmList.get(i).getMember().getToken(), "링줍 알람 입니다", "링크를 봐주세요~");
                        break;
                    case WEEKDAYS:
                        if(!isWeekend(LocalDateTime.now()))
                        if( alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().minusMinutes(1L)) &&
                                alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().plusMinutes(1L)))
                            response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                    alarmList.get(i).getMember().getToken(), "링줍 알람 입니다", "링크를 봐주세요~");
                        break;
                    case WEEKENDS:
                        if(isWeekend(LocalDateTime.now()))
                        if( alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().minusMinutes(1L)) &&
                                alarmList.get(i).getNotifyTime().isBefore(LocalDateTime.now().plusMinutes(1L)))
                            response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                    alarmList.get(i).getMember().getToken(), "링줍 알람 입니다", "링크를 봐주세요~");
                        break;
                    case EVERYDAY_EXCEPT_HOLIDAYS:
                        break;
                    case WEEKDAYS_EXCEPT_HOLIDAYS:
                        break;
                    case WEEKENDS_EXCEPT_HOLIDAYS:
                        break;

                }
            }
        }

        return response;
    }

    public static  boolean isWeekend(LocalDateTime dateTime) {
        // System.out.print("Date - "+dateTime+" , ");
        if(weekendDaysList.contains(dateTime.getDayOfWeek().getValue()) ){
            if(SATURDAY ==  dateTime.getDayOfWeek().getValue()){
                return true;
            }
            if(FRIDAY == dateTime.getDayOfWeek().getValue() && dateTime.getHour() >=WEEKEND_START_FRIDAY_CUT_OFF_HOUR){
                return true;
            }else if(SUNDAY == dateTime.getDayOfWeek().getValue() && dateTime.getHour()  < WEEKEND_END_SUNDAY_CUT_OFF_HOUR ){
                return   true;
            }
        }
        //Checks if dateTime falls in between Friday's 22:00 GMT and Sunday's 23:00 GMT
        return false;
    }
}

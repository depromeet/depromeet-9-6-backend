package com.depromeet.articlereminder.controller.schedule;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.FirebaseCloudMessageService;
import com.depromeet.articlereminder.service.MemberService;
import com.depromeet.articlereminder.util.DateUtil;
import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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

    // 50초 마다 확인
    @Scheduled(fixedRateString = "50000", initialDelay = 10000)
    public ResponseEntity<String> pushFcmMessage() throws Exception {

        List<Alarm> alarmList = alarmService.findAllAlarams();
        ResponseEntity<String> response = null;


        for (int i = 0; i < alarmList.size(); ++i) {
            LocalTime alaramTime = DateUtil.getStringTOLocalDateTime(alarmList.get(i).getNotifyTime());
            LocalTime now = LocalTime.now();
            DayOfWeek CurrentDay = DayOfWeek.of(LocalDateTime.now().getDayOfMonth() % 7 + 1);
            String nowDay = CurrentDay.getDisplayName(TextStyle.FULL, Locale.US).toUpperCase();
            if (alarmList.get(i).getAlarmStatus() == AlarmStatus.ENABLED) {
                switch (alarmList.get(i).getRepeatedDate()) {
                    case EVERYDAY:
                        if (alaramTime.getHour() == now.getHour() && alaramTime.getMinute() == now.getMinute())
                            response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                    "링줍 EVERYDAY 알람 입니다", "링크를 봐주세요~");
                        break;
                    case WEEKDAYS:
                        if (nowDay.equals("MONDAY") || nowDay.equals("TUESDAY") || nowDay.equals("WEDNESDAY") || nowDay.equals("THURSDAY") || nowDay.equals("FRIDAY"))
                            if (alaramTime.getHour() == now.getHour() && alaramTime.getMinute() == now.getMinute())
                                response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                        "링줍 EVERYDAY 알람 입니다", "링크를 봐주세요~");
                        break;
                    case WEEKENDS:
                        if (nowDay.equals("SATURDAY") || nowDay.equals("SUNDAY"))
                            if (alaramTime.getHour() == now.getHour() && alaramTime.getMinute() == now.getMinute())
                                response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                        "링줍 EVERYDAY 알람 입니다", "링크를 봐주세요~");
                        break;
                    // TODO 주말 체크 알고리즘
                    //    case EVERYDAY_EXCEPT_HOLIDAYS:
                    //        break;
                    //    case WEEKDAYS_EXCEPT_HOLIDAYS:
                    //        break;
                    //    case WEEKENDS_EXCEPT_HOLIDAYS:
                    //        break;
                    case TEST:
                        response = firebaseCloudMessageService.sendMessageTo(alarmList.get(i).getMember().getPushToken(),
                                "링줍 EVERYDAY 알람 입니다", "링크를 봐주세요~");
                        break;

                }
            }
        }

        return response;
    }

    public static boolean isWeekend(LocalDateTime dateTime) {
        // System.out.print("Date - "+dateTime+" , ");
        if (weekendDaysList.contains(dateTime.getDayOfWeek().getValue())) {
            if (SATURDAY == dateTime.getDayOfWeek().getValue()) {
                return true;
            }
            if (FRIDAY == dateTime.getDayOfWeek().getValue() && dateTime.getHour() >= WEEKEND_START_FRIDAY_CUT_OFF_HOUR) {
                return true;
            } else if (SUNDAY == dateTime.getDayOfWeek().getValue() && dateTime.getHour() < WEEKEND_END_SUNDAY_CUT_OFF_HOUR) {
                return true;
            }
        }
        //Checks if dateTime falls in between Friday's 22:00 GMT and Sunday's 23:00 GMT
        return false;
    }
}

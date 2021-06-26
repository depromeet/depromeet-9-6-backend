package com.depromeet.articlereminder.controller.schedule;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.FirebaseCloudMessageService;
import com.depromeet.articlereminder.service.LinkService;
import com.depromeet.articlereminder.service.MemberService;
import com.depromeet.articlereminder.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final MemberService memberService;
    private final LinkService linkService;

    // 일요일 오후 6시에 실행
    @Scheduled(cron = "0 0 18 * * ?* SUN")
    public ResponseEntity<String> pushFcmStaticsMessage() throws Exception {
        ResponseEntity<String> response = null;

        List<Member> memberList = memberService.findMembers();
        LocalTime now = LocalTime.now();
        for (int i = 0; i < memberList.size(); ++i) {

            //members Links
            List<Link> linkList = linkService.getLinksByUserId(memberList.get(i));
            for (int j = 0; j<linkList.size(); ++j) {
                //해당 멤버의 링크가 completed인지 개수 파악 후 발송!
                int weekReadTotalCount=0;
                if(linkList.get(j).getCompletedAt().isBefore(ChronoLocalDateTime.from(now))&&
                        linkList.get(j).getCompletedAt().isAfter(ChronoLocalDateTime.from(now.minusHours(168)))){
                    weekReadTotalCount+=1;
                }

                if(weekReadTotalCount>5){
                    response = firebaseCloudMessageService.sendMessageTo("읽으신 개수입니다."+weekReadTotalCount,
                            "링줍 통계 알람 입니다", "링크를 봐주세요~");
                }
                else if(weekReadTotalCount<5){
                    response = firebaseCloudMessageService.sendMessageTo("읽으신 개수입니다."+weekReadTotalCount,
                            "링줍 통계 알람 입니다", "링크를 봐주세요~");
                } else if (memberList.get(i).getLastAccessedAt().isBefore(ChronoLocalDateTime.from(now.minusHours(120)))) {
                    response = firebaseCloudMessageService.sendMessageTo("읽으신 개수입니다."+weekReadTotalCount,
                            "링줍 통계 알람 입니다", "링크를 봐주세요~");
                }
                else if (memberList.get(i).getLastAccessedAt().isBefore(ChronoLocalDateTime.from(now.minusHours(120))) && weekReadTotalCount==0) {
                    response = firebaseCloudMessageService.sendMessageTo("읽으신 개수입니다."+weekReadTotalCount,
                            "링줍 통계 알람 입니다", "링크를 봐주세요~");
                }
            }
        }
        return response;
    }
}

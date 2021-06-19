package com.depromeet.articlereminder;

import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.badge.BadgeCategory;
import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/*
userA
once Alarm
repeated Alarm
userB
normal Badge
season Badge
*/
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws ParseException {
        initService.dbInit1();
        initService.badgeInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final HttpSession httpSession;

//        public void dbInit1() throws ParseException {
//            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date expired = transFormat.parse("2021-05-15 07:15:37");
//            Date start = transFormat.parse("2021-05-15 07:15:37");
//
//            Member member = createMember("topojs8@naver.com", "오준석", "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
//                    expired ,start);
//            em.persist(member);
//
//            SessionUtil.setLoginMemberId(httpSession,member.getEmail());
//            SessionUtil.setTokenExpiredTime(httpSession,member.getTokenExpiredTime());
//            SessionUtil.setToken(httpSession,member.getToken());
//        }
//
//        private Member createMember(String email, String name, String Token, Date TOKEN_EXPIRED_TIME, Date TOKEN_START_TIME) {
//            Member member = new Member();
//            member.setEmail(email);
//            member.setName(name);
//            member.setToken(Token);
//            member.setTokenExpiredTime(TOKEN_EXPIRED_TIME);
//            member.setTokenStartTime(TOKEN_START_TIME);
//            return member;
//        }


        private Member createMember(Long loginId, String name, String Token, LocalDateTime TOKEN_EXPIRED_TIME, LocalDateTime TOKEN_START_TIME) {
            Member member = new Member();
            member.setLoginId(loginId);
            member.setName(name);
            member.setToken(Token);
            member.setTokenExpiredTime(TOKEN_EXPIRED_TIME);
            member.setTokenStartTime(TOKEN_START_TIME);
            member.changeInitialAlarmStatus();
            return member;
        }

        private Hashtag createHashtag(String name) {
            Hashtag hashtag = new Hashtag(name);
            return hashtag;
        }

        public void dbInit1() throws ParseException {
            LocalDateTime expired = LocalDateTime.now().plusMonths(3L);
            LocalDateTime start = LocalDateTime.now();

            Member member1 = createMember(123456L, "링줍줍", "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
                    expired, start);
            em.persist(member1);

            Member member2 = createMember(567890L, "디프만", "depMPdmg9encwSaphKzmRa7k6jzz-te5S9gopcJ8AAAF5a6mGRasdq",
                    expired, start);
            em.persist(member2);

            Hashtag hashtag1 = createHashtag("디자인");
            em.persist(hashtag1);

            Hashtag hashtag2 = createHashtag("UX/UI");
            em.persist(hashtag2);

            Hashtag hashtag3 = createHashtag("스타트업");
            em.persist(hashtag3);

            LinkHashtag linkHashtag1 = LinkHashtag.createLinkHashTag(hashtag1);
            LinkHashtag linkHashtag2 = LinkHashtag.createLinkHashTag(hashtag2);
            LinkHashtag linkHashtag3 = LinkHashtag.createLinkHashTag(hashtag3);

            Link link = Link.createLink(member1, "https://brunch.co.kr/@delight412/351", Arrays.asList(linkHashtag1, linkHashtag2, linkHashtag3));
            em.persist(link);

            Link link2 = Link.createLink(member1, "https://dbbymoon.tistory.com/3", Arrays.asList(linkHashtag1));
            em.persist(link2);

            Alarm alarm1 = Alarm.createAlarm(member1, "08:30", "WEEKDAYS");
            em.persist(alarm1);

            Alarm alarm2 = Alarm.createAlarm(member1, "12:30", "EVERYDAY_EXCEPT_HOLIDAYS");
            em.persist(alarm2);

            Alarm alarm3 = Alarm.createAlarm(member1, "09:30", "WEEKENDS");
            em.persist(alarm3);

            Badge badge = Badge.createBadge("포인트0", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/dount_00.png", BadgeCategory.POINT, "포인트 0 - 읽음 0개", null);
            em.persist(badge);

            MemberBadge memberBadge1 = MemberBadge.createMemberBadge(member1, badge);
            em.persist(memberBadge1);

            MemberBadge memberBadge2 = MemberBadge.createMemberBadge(member2, badge);
            em.persist(memberBadge2);

        }

        public void badgeInit() throws ParseException {
//            Badge badge1 = Badge.createBadge("POINT_0", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/dount_00.png", BadgeCategory.POINT, "0 포인트 획득", null);
//            em.persist(badge1);
            Badge badge2 = Badge.createBadge("POINT_100", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_100.png", BadgeCategory.POINT, "100 포인트 획득", null);
            em.persist(badge2);
            Badge badge3 = Badge.createBadge("POINT_2000", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_2000.png", BadgeCategory.POINT, "2000 포인트 획득", null);
            em.persist(badge3);
            Badge badge4 = Badge.createBadge("POINT_3000", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_3000.png", BadgeCategory.POINT, "3000 포인트 획득", null);
            em.persist(badge4);
            Badge badge5 = Badge.createBadge("POINT_5000", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_5000.png", BadgeCategory.POINT, "5000 포인트 획득", null);
            em.persist(badge5);
            Badge badge6 = Badge.createBadge("POINT_10000", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_10000.png", BadgeCategory.POINT, "10000 포인트 획득", null);
            em.persist(badge6);


            Badge season1 = Badge.createBadge("SEASON_2021_03", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_10000.png", BadgeCategory.SEASON, "2021년 3월", "2021-03");
            em.persist(season1);
            Badge season2 = Badge.createBadge("SEASON_2021_04", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_10000.png", BadgeCategory.SEASON, "2021년 4월", "2021-04");
            em.persist(season2);
            Badge season3 = Badge.createBadge("SEASON_2021_05", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_10000.png", BadgeCategory.SEASON, "2021년 5월", "2021-05");
            em.persist(season3);
            Badge season4 = Badge.createBadge("SEASON_2021_06", "https://link-reminder.s3.ap-northeast-2.amazonaws.com/donut_10000.png", BadgeCategory.SEASON, "2021년 6월", "2021-06");
            em.persist(season4);
        }

    }
}
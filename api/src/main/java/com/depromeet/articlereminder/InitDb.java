////package com.depromeet.articlereminder;
////
////import com.depromeet.articlereminder.domain.member.Member;
////import com.depromeet.articlereminder.util.SessionUtil;
////import lombok.RequiredArgsConstructor;
////import org.springframework.stereotype.Component;
////import org.springframework.transaction.annotation.Transactional;
////
////import javax.annotation.PostConstruct;
////import javax.persistence.EntityManager;
////import javax.servlet.http.HttpSession;
////import java.text.ParseException;
////import java.text.SimpleDateFormat;
////import java.util.Date;
////
/////*
////userA
////once Alarm
////repeated Alarm
////userB
////normal Badge
////season Badge
////*/
////@Component
////@RequiredArgsConstructor
////public class InitDb {
////
////    private final InitService initService;
////
////    @PostConstruct
////    public void init() throws ParseException {
////        initService.dbInit1();
////    }
////
////    @Component
////    @Transactional
////    @RequiredArgsConstructor
////    static class InitService {
////
////        private final EntityManager em;
////        private final HttpSession httpSession;
////
////        public void dbInit1() throws ParseException {
////            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            Date expired = transFormat.parse("2021-05-15 07:15:37");
////            Date start = transFormat.parse("2021-05-15 07:15:37");
////
////            Member member = createMember("topojs8@naver.com", "오준석", "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
////                    expired ,start);
////            em.persist(member);
////
////            SessionUtil.setLoginMemberId(httpSession,member.getEmail());
////            SessionUtil.setTokenExpiredTime(httpSession,member.getTokenExpiredTime());
////            SessionUtil.setToken(httpSession,member.getToken());
////        }
////
////        private Member createMember(String email, String name, String Token, Date TOKEN_EXPIRED_TIME, Date TOKEN_START_TIME) {
////            Member member = new Member();
////            member.setEmail(email);
////            member.setName(name);
////            member.setToken(Token);
////            member.setTokenExpiredTime(TOKEN_EXPIRED_TIME);
////            member.setTokenStartTime(TOKEN_START_TIME);
////            return member;
////        }
////    }
////}
////
//
//private Member createMember(String email, String name, String Token, Date TOKEN_EXPIRED_TIME, Date TOKEN_START_TIME) {
//        Member member = new Member();
//        member.setEmail(email);
//        member.setName(name);
//        member.setToken(Token);
//        member.setTokenExpiredTime(TOKEN_EXPIRED_TIME);
//        member.setTokenStartTime(TOKEN_START_TIME);
//        return member;
//        }
//
//private Hashtag createHashtag(String name) {
//        Hashtag hashtag = new Hashtag(name);
//        return hashtag;
//        }
//
//public void dbInit1() throws ParseException {
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date expired = transFormat.parse("2021-05-15 07:15:37");
//        Date start = transFormat.parse("2021-05-30 07:15:37");
//
//        Member member = createMember("cyj95428@naver.com", "최예진", "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
//        expired ,start);
//        em.persist(member);
//
////            SessionUtil.setLoginMemberId(httpSession,member.getEmail());
////            SessionUtil.setTokenExpiredTime(httpSession,member.getTokenExpiredTime());
////            SessionUtil.setToken(httpSession,member.getToken());
//
//        Hashtag hashtag1 = createHashtag("디자인");
//        em.persist(hashtag1);
//
//        Hashtag hashtag2 = createHashtag("UX/UI");
//        em.persist(hashtag2);
//
//        Hashtag hashtag3 = createHashtag("스타트업");
//        em.persist(hashtag3);
//
//        LinkHashtag linkHashtag1 = LinkHashtag.createLinkHashTag(hashtag1);
//        LinkHashtag linkHashtag2 = LinkHashtag.createLinkHashTag(hashtag2);
//        LinkHashtag linkHashtag3 = LinkHashtag.createLinkHashTag(hashtag3);
////
//        Link link = Link.createLink(member, "https://brunch.co.kr/@delight412/351", Arrays.asList(linkHashtag1, linkHashtag2, linkHashtag3));
////            Link link = Link.createLink(member, "https://brunch.co.kr/@delight412/351");
//        em.persist(link);
//
////            Link link2 = Link.createLink(member, "https://dbbymoon.tistory.com/3");
//        Link link2 = Link.createLink(member, "https://dbbymoon.tistory.com/3", Arrays.asList(linkHashtag1));
//        em.persist(link2);
//
//        }
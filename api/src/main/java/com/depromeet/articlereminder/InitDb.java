package com.depromeet.articlereminder;

import com.depromeet.articlereminder.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() throws ParseException {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date expired = transFormat.parse("2021-05-15 07:15:37");
            Date start = transFormat.parse("2021-05-15 07:15:37");

            Member member = createMember("topojs8@naver.com", "오준석", "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
                    expired ,start);
            em.persist(member);
        }

        private Member createMember(String email, String name, String Token, Date TOKEN_EXPIRED_TIME, Date TOKEN_START_TIME) {
            Member member = new Member();
            member.setEmail(email);
            member.setName(name);
            member.setToken(Token);
            member.setTokenExpiredTime(TOKEN_EXPIRED_TIME);
            member.setTokenStartTime(TOKEN_START_TIME);
            return member;
        }
    }
}


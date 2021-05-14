package com.depromeet.articlereminder.util;

import javax.servlet.http.HttpSession;
import java.util.Date;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";
    private static final String TOKEN_EXPIRED_TIME = "TOKEN_EXPIRED_TIME";
    private static final String TOKEN= "TOKEN";

    // 인스턴스화 방지
    private SessionUtil() {
    }

    /**
     * 로그인한 고객의 아이디를 세션에서 꺼낸다.
     *
     * @param session 사용자의 세션
     * @return 로그인한 고객의 id 또는 null
     * @author jun
     */
    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }

    /**
     * 로그인한 관리자 id를 세션에서 꺼낸다.
     * 로그인 하지 않았다면 null이 반환된다
     *
     * @param session 사용자의 세션
     * @return 로그인한 사장님 id 또는 null
     * @author topojs8
     */
    public static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }


    /**
     * 로그인 한 고객의 id를 세션에 저장한다.
     *
     * @param session 사용자의 session
     * @param id      로그인한 고객의 id
     * @author jun
     */
    public static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }

    public static String getToken(HttpSession session) {
        return (String) session.getAttribute(TOKEN);
    }

    public static Date getTokenExpiredTime(HttpSession session) {
        return (Date) session.getAttribute(TOKEN_EXPIRED_TIME);
    }

    public static void setToken(HttpSession session, String token) {
        session.setAttribute(TOKEN, token);
    }

    public static void setTokenExpiredTime(HttpSession session, Date expiredTime) {
        session.setAttribute(TOKEN_EXPIRED_TIME, expiredTime);
    }
}

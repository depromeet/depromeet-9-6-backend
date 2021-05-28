package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.repository.MemberRepository;
import com.depromeet.articlereminder.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // {basaeurl}/oauth2/authorization/kakao
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();



        Map<String, Object> attributes2 = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> attributes3 = (Map<String, Object>) attributes2.get("profile");

        Member member = new Member();
        member.setName(attributes3.get("nickname").toString());
        member.setEmail(attributes2.get("email").toString());
        member.setToken(userRequest.getAccessToken().getTokenValue());

        // default ExpriedTIME: 현재시간 + 6시간
        member.setTokenExpiredTime(Date.from(userRequest.getAccessToken().getExpiresAt()));
        member.setTokenStartTime(Date.from(userRequest.getAccessToken().getIssuedAt()));

        SessionUtil.setLoginMemberId(httpSession,member.getEmail());
        SessionUtil.setTokenExpiredTime(httpSession,member.getTokenExpiredTime());
        SessionUtil.setToken(httpSession,member.getToken());

        memberService.validateDuplicateMember(member);
        memberRepository.save(member);

        log.debug("accesstoekn :: " + userRequest.getAccessToken().getTokenValue());
        log.debug("attributes :: " + attributes);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id");
    }
    


}
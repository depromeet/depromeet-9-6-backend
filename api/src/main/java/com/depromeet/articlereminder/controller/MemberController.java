package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.aop.LoginCheck;
import com.depromeet.articlereminder.domain.Member;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;


@Api(tags = {"members"})
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/kakaoLogin")
    public String kakaoMemberCreate() {
        return "redirect:/oauth2/authorization/kakao";
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @GetMapping("/members/info")
    public ResponseEntity<Member> memberInfo(String email, HttpSession session) {
        Member memberInfo = memberService.findOneByEmail(email);
        return new ResponseEntity<Member>(memberInfo, HttpStatus.OK);
    }

}

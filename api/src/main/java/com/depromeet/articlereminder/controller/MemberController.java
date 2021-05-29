package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.badge.BadgeCategory;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.*;
import com.depromeet.articlereminder.exception.InvalidAccessTokenException;
import com.depromeet.articlereminder.jwt.UserAssembler;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"members"})
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final UserAssembler userAssembler;

    @ApiOperation("회원 가입")
    @PostMapping("register")
    public ResponseEntity<MemberInfoResponse> register(@RequestBody MemberRegisterDTO userDto) {
        Member user = new Member();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(AlarmStatus.ENABLED);
        user.setTokenExpiredTime(LocalDateTime.now().plusDays(100L));
        long userId = memberService.join(user);
        memberService.update(userId, userAssembler.toLoginResponse(user).getToken());

        return ResponseEntity.ok(userAssembler.toUserResponse(user));
    }

    @ApiOperation("로그인을 하여 토큰을 발급받습니다.")
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody MemberDTO userDto) {

        boolean isValid = true;
        String token = userDto.getToken();

        Member member = memberService.findOne(userDto.getUserId());

        if (token != null) {
            // TODO 카카오 로그인 유효성 검사
            // https://kapi.kakao.com/v1/user/access_token_info
            // isValid = memberService.checkLoginToken(socialType, token);

            // 저장된 토큰값이 맞는지, 기간이 유효한지 확인
//            if(userDto.getToken() != member.getToken())
//                throw new InvalidAccessTokenException();
            if (member.getTokenExpiredTime().isBefore(LocalDateTime.now()))
                throw new InvalidAccessTokenException();
        }
        if (!isValid) {
            throw new InvalidAccessTokenException();
        }
        return ResponseEntity.ok(userAssembler.toLoginResponse(member));
    }
}

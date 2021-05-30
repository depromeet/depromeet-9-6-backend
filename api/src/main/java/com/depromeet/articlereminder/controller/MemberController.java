package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.badge.BadgeCategory;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.*;
import com.depromeet.articlereminder.exception.InvalidAccessTokenException;
import com.depromeet.articlereminder.exception.UserNotFoundException;
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

    @ApiOperation("로그인시 email로 회원을 찾고 없다면 가입, 있다면 로그인 하여 토큰을 발급받습니다.")
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody MemberLoginDTO userDto) {
        Member user = new Member();
        // 존재한다면 로그인
        if (!memberService.findMemberCheckByEmail(userDto.getEmail())) {
            String token = userDto.getToken();
            user = memberService.findOne(userDto.getUserId());

            if (token != null) {
                // TODO 카카오 로그인 유효성 검사
                if (user.getTokenExpiredTime().isBefore(LocalDateTime.now()))
                    return ResponseEntity.ok(userAssembler.toLoginResponse(user, "토큰시간이 만료되었습니다. 재로그인 해주세요."));
            }
            return ResponseEntity.ok(userAssembler.toLoginResponse(user, "정상 로그인되었습니다."));
        } else { // 존재하지 않으면 회원가입
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus(AlarmStatus.ENABLED);
            user.setTokenExpiredTime(LocalDateTime.now().plusDays(100L));
            long userId = memberService.join(user);
            memberService.update(userId, userAssembler.toLoginResponse(user, "").getToken(), user.getTokenExpiredTime());
            return ResponseEntity.ok(userAssembler.toLoginResponse(user, "정상 가입되었습니다."));
        }
    }

    @ApiOperation("로그아웃")
    @PutMapping("logout")
    public ResponseEntity<LoginResponse> logout(@RequestBody MemberLoginDTO userDto) {
        // 토큰 만료시간을 현재 시간으로 바꿔서 재로그인 유도
        Member user = memberService.findByEmail(userDto.getEmail());
        user.setTokenExpiredTime(LocalDateTime.now());
        memberService.update(user.getId(), userAssembler.toLoginResponse(user, "").getToken(), user.getTokenExpiredTime());
        return ResponseEntity.ok(userAssembler.toLoginResponse(user, "로그아웃 되었습니다."));
    }
}

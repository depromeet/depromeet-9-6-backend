package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.domain.member.MemberStatus;
import com.depromeet.articlereminder.dto.*;
import com.depromeet.articlereminder.jwt.UserAssembler;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Api(tags = {"members"})
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final UserAssembler userAssembler;

    @ApiOperation("로그인시 email로 회원을 찾고 없다면 가입, 있다면 로그인 하여 토큰을 발급받습니다.")
    @PostMapping("login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody MemberLoginDTO userDto) {
        Member user = new Member();
        // 존재한다면 로그인
        if (!memberService.findMemberCheckByEmail(userDto.getEmail())) {
            String token = userDto.getToken();
            user = memberService.findOne(userDto.getUserId());

            if (token != null) {
                // TODO 카카오 로그인 유효성 검사
                if (user.getTokenExpiredTime().isBefore(LocalDateTime.now()))
                return ResponseHandler.generateResponse("토큰시간이 만료되었습니다. 재로그인 해주세요","403", userAssembler.toLoginResponse(user));
            }
            // 로그인시 만료시간 늘림
            user = memberService.findByEmail(userDto.getEmail());
            user.setMemberStatus(MemberStatus.LOGIN);
            user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
            memberService.update(user);
            return ResponseHandler.generateResponse("정상 로그인되었습니다", "200", userAssembler.toLoginResponse(user));
        } else { // 존재하지 않으면 회원가입
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus(AlarmStatus.ENABLED);
            user.setMemberStatus(MemberStatus.CREATED);
            user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
            memberService.join(user);
            return ResponseHandler.generateResponse("정상 가입되었습니다.", "201", userAssembler.toLoginResponse(user));
        }
    }

    @ApiOperation("로그아웃")
    @PutMapping("logout")
    public ResponseEntity<Object> logout(@RequestBody MemberLoginDTO userDto) {
        // 토큰 만료시간을 현재 시간으로 바꿔서 재로그인 유도
        Member user = memberService.findByEmail(userDto.getEmail());
        user.setMemberStatus(MemberStatus.LOGOUT);
        user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
        memberService.update(user);
        return ResponseHandler.generateResponse("로그아웃되었습니다.", "204", userAssembler.toLoginResponse(user));
    }

    @ApiOperation("사용자 삭제 - db에 들어가있는 사용자 삭제하시라고 만든 임시 API 입니다! 로그인에 이용하신 email로 삭제 부탁드려요")
    @DeleteMapping("withdraw")
    public ResponseEntity<Object> deleteMember(@RequestHeader("email") String email) {
        memberService.withdraw(email);
        return ResponseHandler.generateResponse( email + "에 해당하는 사용자가 삭제되었습니다." , "204", null);
    }
}

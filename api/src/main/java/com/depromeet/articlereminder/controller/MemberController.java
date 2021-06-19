package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.domain.member.MemberStatus;
import com.depromeet.articlereminder.dto.*;
import com.depromeet.articlereminder.dto.member.AppleMemberIdResponse;
import com.depromeet.articlereminder.dto.member.PushTokenRequest;
import com.depromeet.articlereminder.jwt.UserAssembler;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Api(tags = {"members"})
@RestController
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
        if (!memberService.findMemberCheckByEmail(userDto.getLoginId())) {
            String token = userDto.getToken();

            // TODO 클라이언트가 종료되어 token이 날아갔을 때, userDto의 token이 공백으로 들어오는 경우, userId 0인 경우 처리
            user = memberService.findByLoginId(userDto.getLoginId());

            if (token != null) {
                // TODO 카카오 로그인 유효성 검사
                if (user.getTokenExpiredTime().isBefore(LocalDateTime.now())) {
                    return ResponseHandler.generateResponse("토큰시간이 만료되었습니다. 재로그인 해주세요","403", userAssembler.toLoginResponse(user));
                }
            }
            // 로그인 시 만료시간 늘림
            user = memberService.findByLoginId(userDto.getLoginId());
            user.setMemberStatus(MemberStatus.LOGIN);
            user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
            user.setPushToken(userDto.getPushToken());
            user.setToken(userAssembler.toLoginResponse(user).getToken());
            memberService.update(user);
            return ResponseHandler.generateResponse("정상 로그인되었습니다", "200", userAssembler.toLoginResponse(user));
        } else { // 존재하지 않으면 회원가입
            user.setName(userDto.getName());
            user.setLoginId(userDto.getLoginId());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus(AlarmStatus.ENABLED);
            user.setMemberStatus(MemberStatus.CREATED);
            user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
            user.setPushToken(userDto.getPushToken());

            memberService.join(user);

            // TODO 사용자 회원가입 시 accessToken 저장
            return ResponseHandler.generateResponse("정상 가입되었습니다.", "201", userAssembler.toLoginResponse(user));
        }
    }

//    @ApiOperation("로그아웃")
//    @PutMapping("logout")
//    @Transactional
//    public ResponseEntity<Object> logout(@RequestBody MemberLoginDTO userDto) {
//        // 토큰 만료시간을 현재 시간으로 바꿔서 재로그인 유도
//        Member user = memberService.findByLoginId(userDto.getLoginId());
//        user.setMemberStatus(MemberStatus.LOGOUT);
//        user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));
//        memberService.update(user);
//        return ResponseHandler.generateResponse("로그아웃되었습니다.", "204", userAssembler.toLoginResponse(user));
//    }

    @ApiOperation("로그아웃")
    @PutMapping("logout")
    @Transactional
    public ResponseEntity<Object> logout(@RequestHeader("loginId") Long loginId) {
        // 토큰 만료시간을 현재 시간으로 바꿔서 재로그인 유도
        Member user = memberService.findByLoginId(loginId);

        user.setMemberStatus(MemberStatus.LOGOUT);

        // TODO 로그아웃 시 토큰 만료 시키도록 변경 필요
        user.setTokenExpiredTime(LocalDateTime.now().plusHours(100L));

        memberService.update(user);

        return ResponseHandler.generateResponse("로그아웃되었습니다.", "203", null);
    }

    @ApiOperation("사용자 삭제 - db에 들어가있는 사용자 삭제하시라고 만든 임시 API 입니다! 로그인에 이용하신 loginId로 삭제 부탁드려요")
    @DeleteMapping("withdraw")
    @Transactional
    public ResponseEntity<Object> deleteMember(@RequestHeader("loginId") Long loginId) {
        memberService.withdraw(loginId);
        return ResponseHandler.generateResponse( loginId + "에 해당하는 사용자가 삭제되었습니다." , "204", null);
    }

    @ApiOperation("사용자 FCM pushToken 만료 시 업데이트")
    @PutMapping(value = "/renewal/pushToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Transactional
    public ResponseEntity<Object> updatePushToken(@RequestHeader("loginId") Long loginId,
                                                  PushTokenRequest pushTokenRequest) {
        Member member = memberService.updatePushToken(loginId, pushTokenRequest.getPushToken());

        return ResponseHandler.generateResponse("사용자의 pushToken이 업데이트되었습니다.", "203", userAssembler.toLoginResponse(member));
    }

    @ApiOperation("Sign in with Apple 을 위한 loginId <--> userIdentifier 매핑 API (임시)")
    @GetMapping(value = "/mapping")
    public ResponseEntity<Object> getLoginIdForAppleUser(@RequestHeader("userIdentifier") String userIdentifier) {
        AppleMemberIdResponse loginId = memberService.getLoginId(userIdentifier);
        return ResponseHandler.generateResponse("userIdentifier " + userIdentifier + "에 해당하는 loginId를 조회하는데 성공했습니다.", "200", loginId);
    }

}

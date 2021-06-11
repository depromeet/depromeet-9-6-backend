package com.depromeet.articlereminder.controller.member;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.badge.BadgeHistoryResponse;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.badge.BadgeResponse;
import com.depromeet.articlereminder.dto.member.PushAlarmStatusResponse;
import com.depromeet.articlereminder.dto.member.UserMyPageResponse;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"members"})
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    //    @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 정보를(마이페이지를) 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("/v1/members/mypage/info")
    @Transactional
    public ResponseEntity<Object> userPageInfo(@RequestHeader(name = "Authorization") String authorization,
                                                         @RequestHeader(name = "userId") Long userId) {
        Member member = memberService.getMyPageUserInfo(userId);
        UserMyPageResponse myPageResponse = new UserMyPageResponse(member);
        return ResponseHandler.generateResponse("사용자 정보 조회에 성공했습니다.", "200", myPageResponse);
    }

//        @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 뱃지 히스토리를 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("/v1/members/mypage/badges")
    @Transactional
    public ResponseEntity<Object> userBadges(@RequestHeader(name = "Authorization") String authorization,
                                                          @RequestHeader(name = "userId") Long userId,
                                                          @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                          @RequestParam(required = false, defaultValue = "12") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<MemberBadge> badges = memberService.getMemberBadges(userId, pageable);
        Page<BadgeHistoryResponse> badgeMap = badges.map(BadgeHistoryResponse::new);
        return ResponseHandler.generateResponse("사용자 뱃지 리스트 조회에 성공했습니다.","200", badgeMap);
    }

    @ApiOperation("사용자의 푸시 알람 활성화 / 비활성화 여부를 등록합니다. - 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("/v1/members/mypage/alarm")
    @Transactional
    public ResponseEntity<Object> postPushAlarmStatus(@RequestHeader(name = "Authorization") String authorization,
                                                                @RequestHeader(name = "userId") Long userId,

                                                                @ApiParam(name = "alarmEnabled",
                                                                        type = "string",
                                                                        example = "T",
                                                                        value = "푸시 알람 활성화 여부 (활성화 : T, 비활성화 : F)",
                                                                        required = true)
                                                                @RequestParam(required = true) String alarmEnabled) {

        Member updatedMember = memberService.updateAlarmStatus(userId, alarmEnabled);
        PushAlarmStatusResponse userMyPageResponse = new PushAlarmStatusResponse(updatedMember);
        String enabledFlag = alarmEnabled.equalsIgnoreCase("T") ? "활성화" : "비활성화";
        return ResponseHandler.generateResponse("푸시 알람 " + enabledFlag + " 등록에 성공했습니다.", "201",userMyPageResponse);
    }


}

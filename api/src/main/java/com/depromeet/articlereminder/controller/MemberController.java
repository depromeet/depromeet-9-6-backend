package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.aop.LoginCheck;
import com.depromeet.articlereminder.domain.BadgeCategory;
import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.Member;
import com.depromeet.articlereminder.dto.BadgeResponse;
import com.depromeet.articlereminder.dto.UserMyPageResponse;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

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

    @GetMapping("kakaoLogin")
    public String kakaoMemberCreate() {
        return "redirect:/oauth2/authorization/kakao";
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @GetMapping("members/info")
    public ResponseEntity<Member> memberInfo(@RequestParam(required = false) String email) {
        Member memberInfo = memberService.findOneByEmail(email);
        return new ResponseEntity<Member>(memberInfo, HttpStatus.OK);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 정보를(마이페이지를) 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("mypage/info")
    public BaseResponse<UserMyPageResponse> userPageInfo(@RequestParam(required = false)String email) {
        BadgeResponse badge = BadgeResponse.builder()
                .badgeId(1L)
                .badgeName("0~5000 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5000점 이하인 경우 획득")
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .build();

        UserMyPageResponse myPageResponse = UserMyPageResponse.builder()
                .userId(1L)
                .nickName("칠성파")
                .totalPoint(4235L)
                .seasonCount(53L)
                .createdAt(LocalDateTime.now().minusDays(60L))
                .totalReadCount(52L)
                .badge(badge)
                .build();

        return BaseResponse.of("202", "사용자 정보 조회에 성공했습니다.", myPageResponse);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 뱃지 히스토리를 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("mypage/badges")
    public BaseResponse<Page<BadgeResponse>> userBadges(@RequestParam(required = false) String email,
                                                          @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                          @RequestParam(required = false, defaultValue = "12") int pageSize) {
        BadgeResponse badge1 = BadgeResponse.builder()
                .badgeId(1L)
                .userId(1L)
                .badgeName("0~5000 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5000점 이하인 경우 획득")
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .build();

        BadgeResponse badge2 = BadgeResponse.builder()
                .badgeId(2L)
                .userId(1L)
                .badgeName("5001~10000 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5001~10000점인 경우 획득")
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .build();

        BadgeResponse badge3 = BadgeResponse.builder()
                .badgeId(3L)
                .userId(1L)
                .badgeName("0~500 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.SEASON)
                .conditions("2021년 4월 시즌 뱃지 획득")
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .build();

        List<BadgeResponse> badges = Stream.of(badge1, badge2, badge3).collect(Collectors.toList());
        Page<BadgeResponse> page = new PageImpl<>(badges);

        return BaseResponse.of("202", "사용자 뱃지 리스트 조회에 성공했습니다.", page);
    }

    @ApiOperation("사용자의 푸시 알람 활성화 / 비활성화 여부를 등록합니다. - 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("mypage/alarm")
    public BaseResponse<UserMyPageResponse> postPushAlarmStatus(@RequestHeader(name = "Authorization") String authorization,
                                                                @RequestHeader(name = "userId") Long userId,

                                                                @ApiParam(name = "alarmEnabled",
                                                                        type = "string",
                                                                        example = "T",
                                                                        value = "푸시 알람 활성화 여부 (활성화 : T, 비활성화 : F)",
                                                                        required = true)
                                                                @RequestParam(required = true) String alarmEnabled) {

        BadgeResponse badge = BadgeResponse.builder()
                .badgeId(1L)
                .badgeName("0~5000 포인트 뱃지")
                .badgeURL("https://s3-alpha-sig.figma.com/img/14e9/9e13/1932c8a44e20ccbc191fb7d9976b8dc9?Expires=1621209600&Signature=OgaQ2TVqUg8niOuCrSYD8DX7LxzEv2jGvGgeUQNg~CSdE0mq9yz~6b788lC2dO1zFj5R~fXnnfIBsA6FX4x9vzcnIqOrmtVCthwXyJ5AmZBLWedvH9cra5bqF~Up36-Tbym7TVKHvFzATtgCOKX4xzsCD234EGD~y5r9bHiEdS8Z3cINvEt5MW2mZx19-Gi4qXe~l0G-wRNjZdGkUd5ygMSAi8ZwGsad2UuYW8IPAUVAF0aZD19ElsZAWKuarxzLehUiN7MWpKFGTAKoPXl2kybOfw2n3H9MtNqzEcg0GBGT60l6WpZ7LKU7aVpCtip-itDcFkA9ZHc1hysvO~-mdg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
                .category(BadgeCategory.POINT)
                .conditions("포인트가 5000점 이하인 경우 획득")
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .build();

        UserMyPageResponse myPageResponse = UserMyPageResponse.builder()
                .userId(1L)
                .nickName("칠성파")
                .totalPoint(4235L)
                .seasonCount(53L)
                .createdAt(LocalDateTime.now().minusDays(60L))
                .totalReadCount(52L)
                .alarmEnabled("T".equals(alarmEnabled))
                .badge(badge)
                .build();

        return BaseResponse.of("201", "푸시 알람 활성화 여부 등록에 성공했습니다.", myPageResponse);
    }

}

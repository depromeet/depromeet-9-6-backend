package com.depromeet.articlereminder.controller.member;

import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.badge.BadgeCategory;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.badge.BadgeResponse;
import com.depromeet.articlereminder.dto.member.UserMyPageResponse;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.BadgeRepository;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Api(tags = {"members"})
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    private final BadgeRepository badgeRepository;

    //    @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 정보를(마이페이지를) 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("/v1/members/mypage/info")
    @Transactional
    public BaseResponse<UserMyPageResponse> userPageInfo(@RequestHeader(name = "Authorization") String authorization,
                                                         @RequestHeader(name = "userId") Long userId) {
        Member member = memberService.getMyPageUserInfo(userId);
        UserMyPageResponse myPageResponse = new UserMyPageResponse(member);
        return BaseResponse.of("200", "사용자 정보 조회에 성공했습니다.", myPageResponse);
    }

//        @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("사용자의 뱃지 히스토리를 조회합니다. - 사용자 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("/v1/members/mypage/badges")
    public BaseResponse<Page<BadgeResponse>> userBadges(@RequestHeader(name = "Authorization") String authorization,
                                                        @RequestHeader(name = "userId") Long userId,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                        @RequestParam(required = false, defaultValue = "12") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
//        Page<Badge> badges = badgeRepository.findByMember();

//        badgeRepository.findBy
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
    @PostMapping("/v1/members/mypage/alarm")
    @Transactional
    public BaseResponse<UserMyPageResponse> postPushAlarmStatus(@RequestHeader(name = "Authorization") String authorization,
                                                                @RequestHeader(name = "userId") Long userId,

                                                                @ApiParam(name = "alarmEnabled",
                                                                        type = "string",
                                                                        example = "T",
                                                                        value = "푸시 알람 활성화 여부 (활성화 : T, 비활성화 : F)",
                                                                        required = true)
                                                                @RequestParam(required = true) String alarmEnabled) {

        Member updatedMember = memberService.updateAlarmStatus(userId, alarmEnabled);
        UserMyPageResponse userMyPageResponse = new UserMyPageResponse(updatedMember);
        return BaseResponse.of("201", "푸시 알람 활성화 여부 등록에 성공했습니다.", userMyPageResponse);
    }
}

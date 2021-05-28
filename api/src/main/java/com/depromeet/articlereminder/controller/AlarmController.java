package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.aop.LoginCheck;
import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.depromeet.articlereminder.dto.AlarmDTO;
import com.depromeet.articlereminder.dto.AlarmResponse;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"alarms"})
@RestController
@RequestMapping(value = "/v1/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @ApiOperation("어플 알람 리스트를 가져옵니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("")
    public BaseResponse<List<AlarmResponse>> getAlarms(@RequestHeader(name = "Authorization") String authorization,
                                                       @RequestHeader(name = "userId") Long userId) {
        AlarmResponse alarmResponse1 = AlarmResponse.builder()
                .alarmId(1L)
                .userId(1L)
                .notifyTime("08:30")
                .repeatedDate(RepeatedDate.EVERYDAY)
                .isEnabled(true)
                .createdAt(LocalDateTime.now().minusDays(5L))
                .build();

        AlarmResponse alarmResponse2 = AlarmResponse.builder()
                .alarmId(2L)
                .userId(1L)
                .notifyTime("09:00")
                .repeatedDate(RepeatedDate.EVERYDAY)
                .isEnabled(false)
                .createdAt(LocalDateTime.now().minusDays(3L))
                .build();

        AlarmResponse alarmResponse3 = AlarmResponse.builder()
                .alarmId(3L)
                .userId(1L)
                .notifyTime("09:30")
                .repeatedDate(RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS)
                .isEnabled(true)
                .createdAt(LocalDateTime.now().minusDays(2L))
                .build();

        List<AlarmResponse> alarmList = Stream.of(alarmResponse3, alarmResponse2, alarmResponse1).collect(Collectors.toList());

        return BaseResponse.of("202", "어플 알람 리스트 조회에 성공했습니다.", alarmList);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("어플 알람을 추가합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("")
    public BaseResponse<AlarmResponse> postAlarm(@RequestHeader(required = true) Long userId,
                                          @RequestBody AlarmDTO alarmDTO) {
        return BaseResponse.of("201", "어플 알람 등록에 성공했습니다.",
                AlarmResponse.builder()
                        .alarmId(1L)
                        .userId(userId)
                        .notifyTime(alarmDTO.getNotifyTime())
                        .repeatedDate(RepeatedDate.valueOf(alarmDTO.getRepeatedDate()))
                        .isEnabled(alarmDTO.isEnabled())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @ApiOperation("특정한 어플 알람의 세부 내용을 조회합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("{alarmId}")
    public BaseResponse<AlarmResponse> getAlarm(@RequestHeader(name = "Authorization") String authorization,
                                                  @RequestHeader(name = "userId") Long userId,
                                                  @PathVariable Long alarmId) {
        AlarmResponse alarmResponse3 = AlarmResponse.builder()
                .alarmId(3L)
                .userId(1L)
                .notifyTime("09:30")
                .repeatedDate(RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS)
                .isEnabled(true)
                .createdAt(LocalDateTime.now().minusDays(2L))
                .build();

        return BaseResponse.of("202", alarmId + " 알람 세부 조회에 성공했습니다.", alarmResponse3);
    }

    @ApiOperation("특정한 어플 알람을 수정합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 401, message = "Access token is not valid"),
//            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PutMapping("{alarmId}")
    public BaseResponse<AlarmResponse> putAlarm(@RequestHeader(name = "Authorization") String authorization,
                                                  @RequestHeader(name = "userId") Long userId,
                                                  @PathVariable Long alarmId,
                                                  @RequestBody AlarmDTO alarmDTO) {
        return BaseResponse.of(
                "203",
                alarmId + " 알람 수정에 성공했습니다.",
                AlarmResponse.builder()
                        .alarmId(alarmId)
                        .userId(userId)
                        .notifyTime(alarmDTO.getNotifyTime())
                        .repeatedDate(RepeatedDate.valueOf(alarmDTO.getRepeatedDate()))
                        .isEnabled(alarmDTO.isEnabled())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @ApiOperation("특정한 어플 알람을 삭제합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 401, message = "Access token is not valid"),
//            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @DeleteMapping("{alarmId}")
    public BaseResponse<Object> deleteAlarm(@RequestHeader(name = "Authorization") String authorization,
                                              @RequestHeader(name = "userId") Long userId,
                                              @PathVariable Long alarmId) {
        return BaseResponse.of("204", alarmId + " 알람 삭제에 성공했습니다.", null);
    }
}

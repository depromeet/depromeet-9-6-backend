package com.depromeet.articlereminder.controller.alarm;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.alarm.RepeatedDate;
import com.depromeet.articlereminder.dto.alarm.AlarmRequest;
import com.depromeet.articlereminder.dto.alarm.AlarmResponse;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.service.AlarmService;
import com.depromeet.articlereminder.service.MemberService;
import java.util.List;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Api(tags = {"alarms"})
@RestController
@RequestMapping(value = "/v1/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final MemberService memberService;

    @ApiOperation("어플 알람 리스트를 가져옵니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("")
    public ResponseEntity<Object> getAlarms(
                                        @RequestHeader(name = "Authorization") String authorization,
                                        @RequestHeader(name = "userId") Long userId) {
        System.out.println("??????" + userId);
        List<Alarm> alarms = alarmService.findAlarmsByUserId(userId);

        List<AlarmResponse> alarmResponse = alarms.stream()
                .map(AlarmResponse::new)
                .collect(Collectors.toList());

        return ResponseHandler.generateResponse("사용자의 알람 리스트 조회에 성공했습니다.", "200", alarmResponse);
    }

    //@LoginCheck(type = LoginCheck.UserType.USER)
    @ApiOperation("어플 알람을 추가합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("")
    @Transactional
    public ResponseEntity<Object> postAlarm(@RequestHeader(required = true) Long userId,
                                                 @RequestBody AlarmRequest alarmRequest) {
        Alarm savedAlarm = alarmService.saveAlarm(userId, alarmRequest);
        AlarmResponse alarmResponse = new AlarmResponse(savedAlarm);
        return ResponseHandler.generateResponse(
                "어플 알람 등록에 성공했습니다.",
                "201",
                alarmResponse
        );
    }

    @ApiOperation("특정한 어플 알람의 세부 내용을 조회합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("{alarmId}")
    public ResponseEntity<Object> getAlarm(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long alarmId) {
        Alarm alarm = alarmService.getAlarm(userId, alarmId);
        AlarmResponse alarmResponse = new AlarmResponse(alarm);

        return ResponseHandler.generateResponse( alarmId + " 알람 세부 조회에 성공했습니다.", "200", alarmResponse);
    }

    @ApiOperation("특정한 어플 알람을 수정합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PutMapping("{alarmId}")
    @Transactional
    public ResponseEntity<Object> putAlarm(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long alarmId,
                                                @RequestBody AlarmRequest alarmRequest) {

        Alarm alarm = alarmService.updateAlarm(userId, alarmId, alarmRequest);
        AlarmResponse alarmResponse = new AlarmResponse(alarm);

        return ResponseHandler.generateResponse(
                alarmId + " 알람 수정에 성공했습니다.",
                "203",
                alarmResponse
        );
    }

    @ApiOperation("특정한 어플 알람을 삭제합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @DeleteMapping("{alarmId}")
    @Transactional
    public ResponseEntity<Object> deleteAlarm(@RequestHeader(name = "Authorization") String authorization,
                                            @RequestHeader(name = "userId") Long userId,
                                            @PathVariable Long alarmId) {
        alarmService.deleteAlarm(userId, alarmId);
        return ResponseHandler.generateResponse(alarmId + " 알람 삭제에 성공했습니다.", "204", null);
    }
}

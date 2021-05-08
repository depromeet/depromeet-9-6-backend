package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.RepeatedDate;
import com.depromeet.articlereminder.dto.AlarmDTO;
import com.depromeet.articlereminder.dto.AlarmResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"alarms"})
@RestController
@RequestMapping(value = "/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

    @ApiOperation("어플 알람 리스트를 가져옵니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("")
    public ResponseEntity<List<AlarmResponse>> getAlarms(@RequestParam(required = true) Long userId) {
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

        return ResponseEntity.ok(alarmList);
    }

    @ApiOperation("어플 알람을 추가합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PostMapping("")
    public ResponseEntity<String> postAlarm(@RequestParam(required = true) Long userId,
                                          @RequestBody AlarmDTO alarmDTO) {
        return new ResponseEntity<>("알람 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("특정한 어플 알람의 세부 내용을 조회합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("{id}")
    public ResponseEntity<AlarmResponse> getAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId) {
        AlarmResponse alarmResponse3 = AlarmResponse.builder()
                .alarmId(3L)
                .userId(1L)
                .notifyTime("09:30")
                .repeatedDate(RepeatedDate.EVERYDAY_EXCEPT_HOLIDAYS)
                .isEnabled(true)
                .createdAt(LocalDateTime.now().minusDays(2L))
                .build();

        return ResponseEntity.ok(alarmResponse3);
    }

    @ApiOperation("특정한 어플 알람을 수정합니다. - 어플 알람 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PutMapping("{id}")
    public ResponseEntity<AlarmResponse> putAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId,
                                                         @RequestBody AlarmDTO alarmDTO) {
        return ResponseEntity.ok(
                AlarmResponse.builder()
                .alarmId(id)
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
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId) {
        return ResponseEntity.noContent().build();
    }
}

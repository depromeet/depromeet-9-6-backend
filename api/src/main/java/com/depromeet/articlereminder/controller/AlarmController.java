package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.RepeatedDate;
import com.depromeet.articlereminder.dto.AlarmDTO;
import com.depromeet.articlereminder.dto.AlarmResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("어플 알람 리스트를 가져옵니다.")
    @GetMapping("")
    public ResponseEntity<List<AlarmResponse>> getAlarms(@RequestParam(required = true) Long userId) {
        AlarmResponse alarmResponse1 = AlarmResponse.builder()
                                                    .alarmId(1L)
                                                    .userId(1L)
                                                    .notifyTime("08:30")
                                                    .repeatedDate(RepeatedDate.EVERYDAYS)
                                                    .isEnabled(true)
                                                    .createdAt(LocalDateTime.now().minusDays(3L))
                                                    .build();

        AlarmResponse alarmResponse2 = AlarmResponse.builder()
                                                    .alarmId(2L)
                                                    .userId(1L)
                                                    .notifyTime("09:00")
                                                    .repeatedDate(RepeatedDate.EVERYDAYS)
                                                    .isEnabled(false)
                                                    .createdAt(LocalDateTime.now().minusDays(2L))
                                                    .build();

        AlarmResponse alarmResponse3 = AlarmResponse.builder()
                                                    .alarmId(3L)
                                                    .userId(1L)
                                                    .notifyTime("09:30")
                                                    .repeatedDate(RepeatedDate.EVERYDAYS_EXCEPT_HOLIDAYS)
                                                    .isEnabled(true)
                                                    .createdAt(LocalDateTime.now().minusDays(2L))
                                                    .build();

        List<AlarmResponse> alarmList = Stream.of(alarmResponse1, alarmResponse2, alarmResponse3).collect(Collectors.toList());

        return ResponseEntity.ok(alarmList);
    }

    @ApiOperation("어플 알람을 추가합니다.")
    @PostMapping("")
    public ResponseEntity<Void> postAlarm(@RequestParam(required = true) Long userId,
                                          @RequestBody AlarmDTO alarmDTO) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정한 어플 알람의 세부 내용을 조회합니다. - 어플 알람 id 필요" )
    @GetMapping("{id}")
    public ResponseEntity<AlarmResponse> getAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId) {
        AlarmResponse alarmResponse3 = AlarmResponse.builder()
                .alarmId(3L)
                .userId(1L)
                .notifyTime("09:30")
                .repeatedDate(RepeatedDate.EVERYDAYS_EXCEPT_HOLIDAYS)
                .isEnabled(true)
                .createdAt(LocalDateTime.now().minusDays(2L))
                .build();

        return ResponseEntity.ok(alarmResponse3);
    }

    @ApiOperation("특정한 어플 알람을 수정합니다. - 어플 알람 id 필요")
    @PutMapping("{id")
    public ResponseEntity<AlarmResponse> putAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId,
                                                         @RequestBody AlarmDTO alarmDTO) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정한 어플 알람을 삭제합니다. - 어플 알람 id 필요")
    @DeleteMapping("{id")
    public ResponseEntity<AlarmResponse> deleteAlarm(@PathVariable Long id,
                                                         @RequestParam(required = true) Long userId) {
        return ResponseEntity.ok().build();
    }
}

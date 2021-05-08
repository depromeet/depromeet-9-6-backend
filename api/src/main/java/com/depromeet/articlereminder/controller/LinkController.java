package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.Hashtag;
import com.depromeet.articlereminder.domain.Link;
import com.depromeet.articlereminder.dto.*;
import io.swagger.annotations.*;
import io.swagger.models.Response;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.io.PushbackReader;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"links"})
@RestController
@RequestMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
public class LinkController {

    @ApiOperation("사용자가 저장한 링크 리스트를 가져옵니다. - 다 읽은 링크만을 조회하고 싶다면 completed 파라미터를 T로 주시면 됩니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("")
    public ResponseEntity<Page<LinkResponse>> getLinks(@RequestParam(required = true) Long userId,

                                                       @ApiParam(name = "completed",
                                                               type = "string",
                                                               example = "F",
                                                               value = "다 읽은 링크만 가져올지 flag(다 읽은 링크 : T, 모두 포함 : F)",
                                                                required = false)
                                                       @RequestParam(required = false, defaultValue = "F") String completed,

                                                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") int pageSize) {

        HashtagDTO hashtagDTO1 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("디자인")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO2 = HashtagDTO.builder()
                .hashtagId(2L)
                .hashtagName("포트폴리오")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO3 = HashtagDTO.builder()
                .hashtagId(3L)
                .hashtagName("스타트업")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO4 = HashtagDTO.builder()
                .hashtagId(4L)
                .hashtagName("UX")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO5 = HashtagDTO.builder()
                .hashtagId(5L)
                .hashtagName("UI")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();


        List<HashtagDTO> hashtags = Stream.of(hashtagDTO1, hashtagDTO2, hashtagDTO3).collect(Collectors.toList());
        List<HashtagDTO> hashtags2 = Stream.of(hashtagDTO1, hashtagDTO4, hashtagDTO5).collect(Collectors.toList());

        LinkResponse linkDTO1 = LinkResponse.builder()
                .linkId(1L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@delight412/351")
                .isCompleted(false)
                .hasReminder(true)
                .hashtags(hashtags)
                .createdAt(LocalDateTime.now().minusDays(3L))
                .completedAt(null)
                .build();

        LinkResponse linkDTO2 = LinkResponse.builder()
                .linkId(2L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@dalgudot/94")
                .isCompleted(false)
                .hasReminder(false)
                .hashtags(hashtags2)
                .createdAt(LocalDateTime.now().minusDays(1L))
                .completedAt(null)
                .build();

        List<LinkResponse> links = Stream.of(linkDTO2, linkDTO1).collect(Collectors.toList());
        Page<LinkResponse> page = new PageImpl<>(links);
        return ResponseEntity.ok(page);
    }

    @ApiOperation("새로운 링크를 등록(추가)합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PostMapping("")
    public ResponseEntity<String> postLink(@RequestParam(required = true) Long userId,
                                         @RequestBody LinkDTO linkDTO) {
        return new ResponseEntity<>("링크 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("특정 링크에 대해 상세 조회를 합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("{id}")
    public ResponseEntity<LinkResponse> getLink(@PathVariable Long id,
                                                @RequestParam(required = true) Long userId) {
        HashtagDTO hashtagDTO1 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("디자인")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO4 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("UX")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO5 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("UI")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        List<HashtagDTO> hashtags2 = Stream.of(hashtagDTO1, hashtagDTO4, hashtagDTO5).collect(Collectors.toList());
        LinkResponse linkDTO2 = LinkResponse.builder()
                .linkId(2L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@dalgudot/94")
                .isCompleted(false)
                .isCompleted(false)
                .hasReminder(false)
                .hashtags(hashtags2)
                .createdAt(LocalDateTime.now().minusDays(1L))
                .completedAt(null)
                .build();

        return ResponseEntity.ok(linkDTO2);
    }

    @ApiOperation("특정 링크에 대해 수정합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PutMapping("{id}")
    public ResponseEntity<LinkResponse> putLink(@PathVariable Long id,
                                        @RequestParam(required = true) Long userId,
                                        @RequestBody LinkDTO linkDTO) {
        List<HashtagDTO> hashtags = linkDTO.getHashtags()
                .stream()
                .map(item -> HashtagDTO.builder().hashtagId(1L).hashtagName(item.toString()).createdAt(LocalDateTime.now()).build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                LinkResponse.builder()
                .linkId(id)
                .userId(userId)
                .linkURL(linkDTO.getLinkURL())
                .hashtags(hashtags)
                .isCompleted(false)
                .hasReminder(false)
                .completedAt(null)
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    @ApiOperation("특정 링크에 대해 삭제합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteLink(@PathVariable Long id,
                                        @RequestParam(required = true) Long userId) {
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("특정 링크에 대해 읽음 완료 표시를 합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PatchMapping("{id}")
    public ResponseEntity<LinkResponse> patchLink(@PathVariable Long id,
                                          @RequestParam(required = true) Long userId,

                                          @ApiParam(name = "completed",
                                                   type = "string",
                                                   example = "T",
                                                   value = "읽음 완료 flag (읽음 완료 : T, 읽지 않음 : F)",
                                                   required = true)
                                          @RequestBody String completed) {
        HashtagDTO hashtagDTO1 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("디자인")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO4 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("UX")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO5 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("UI")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        List<HashtagDTO> hashtags2 = Stream.of(hashtagDTO1, hashtagDTO4, hashtagDTO5).collect(Collectors.toList());
        LinkResponse linkDTO2 = LinkResponse.builder()
                .linkId(2L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@dalgudot/94")
                .isCompleted(completed.equals("T"))
                .hasReminder(false)
                .hashtags(hashtags2)
                .createdAt(LocalDateTime.now().minusDays(1L))
                .completedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(linkDTO2);
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 조회합니다 - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("{id}/alarm")
    public ResponseEntity<LinkAlarmResponse> getLinkAlarm(@PathVariable Long id,
                                                           @RequestParam(required = true) Long userId) {

        LinkAlarmResponse linkAlarm = LinkAlarmResponse.builder()
                                        .linkId(1L)
                                        .alarmId(1L)
                                        .notifyTime(LocalDateTime.now().plusDays(2L))
                                        .isEnabled(true)
                                        .createdAt(LocalDateTime.now().minusDays(3L))
                                        .build();
        return ResponseEntity.ok(linkAlarm);
    }

    @ApiOperation("특정 링크에 개별 알람을 등록(추가)합니다 - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PostMapping("{id}/alarm")
    public ResponseEntity<String> postLinkAlarm(@PathVariable Long id,
                                              @RequestParam(required = true) Long userId,
                                              @RequestBody LinkAlarmDTO linkAlarmDTO) {
        return new ResponseEntity<>("개별 링크 알람 등록에 성공했습니다.", HttpStatus.OK);
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 수정합니다 - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PutMapping("{id}/alarm")
    public ResponseEntity<LinkAlarmResponse> putLinkAlarm(@PathVariable Long id,
                                             @RequestParam(required = true) Long userId,
                                             @RequestBody LinkAlarmDTO linkAlarmDTO) {
        return ResponseEntity.ok(
                LinkAlarmResponse.builder()
                .linkId(id)
                .alarmId(1L)
                .notifyTime(linkAlarmDTO.getNotifiyTime())
                .isEnabled(true)
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 삭제합니다 - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Access token is not valid"),
            @ApiResponse(code = 403, message = "Requested user is not author of link"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping("{id}/alarm")
    public ResponseEntity<Object> deleteLinkAlarm(@PathVariable Long id,
                                                @RequestParam(required = true) Long userId) {
        return ResponseEntity.noContent().build();
    }


}

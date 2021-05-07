package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.Hashtag;
import com.depromeet.articlereminder.domain.Link;
import com.depromeet.articlereminder.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
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

    @ApiOperation("사용자가 저장한 링크 리스트를 가져옵니다. - 다 읽은 링크만을 조회하고 싶다면 completed 파라미터를 T로 주시면 됩니다.")
    @GetMapping("")
    public ResponseEntity<Page<LinkResponse>> getLinks(@RequestParam(required = true) Long userId,
                                                       @RequestParam(required = false, defaultValue = "F") String completed,
                                                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") int pageSize) {

        HashtagDTO hashtagDTO1 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("디자인")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO2 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("포트폴리오")
                .createdAt(LocalDateTime.now().minusDays(20L))
                .build();

        HashtagDTO hashtagDTO3 = HashtagDTO.builder()
                .hashtagId(1L)
                .hashtagName("스타트업")
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


        List<HashtagDTO> hashtags = Stream.of(hashtagDTO1, hashtagDTO2, hashtagDTO3).collect(Collectors.toList());
        List<HashtagDTO> hashtags2 = Stream.of(hashtagDTO1, hashtagDTO4, hashtagDTO5).collect(Collectors.toList());

        LinkResponse linkDTO1 = LinkResponse.builder()
                .linkId(1L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@delight412/351")
                .isRead(false)
                .hasReminder(true)
                .hashtags(hashtags)
                .createdAt(LocalDateTime.now().minusDays(3L))
                .completedAt(null)
                .build();

        LinkResponse linkDTO2 = LinkResponse.builder()
                .linkId(2L)
                .userId(1L)
                .linkURL("https://brunch.co.kr/@dalgudot/94")
                .isRead(false)
                .hasReminder(false)
                .hashtags(hashtags2)
                .createdAt(LocalDateTime.now().minusDays(1L))
                .completedAt(null)
                .build();

        List<LinkResponse> links = Stream.of(linkDTO1, linkDTO2).collect(Collectors.toList());
        Page<LinkResponse> page = new PageImpl<>(links);
        return ResponseEntity.ok(page);
    }

    @ApiOperation("새로운 링크를 추가합니다.")
    @PostMapping("")
    public ResponseEntity<Void> postLink(@RequestParam(required = true) Long userId,
                                         @RequestBody LinkDTO linkDTO) {


//        LinkResponse newLink = LinkResponse.builder()
//                                        .linkId(3L)
//                                        .userId(userId)
//                                        .linkURL(linkDTO.getLinkURL())
//                                        .isRead(false)
//                                        .hasReminder(false)
//                                        .hashtags()
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 대해 상세 조회를 합니다. - 링크 id 필요")
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
                .isRead(false)
                .hasReminder(false)
                .hashtags(hashtags2)
                .createdAt(LocalDateTime.now().minusDays(1L))
                .completedAt(null)
                .build();

        return ResponseEntity.ok(linkDTO2);
    }

    @ApiOperation("특정 링크에 대해 수정합니다. - 링크 id 필요")
    @PutMapping("{id}")
    public ResponseEntity<Void> putLink(@PathVariable Long id,
                                        @RequestParam(required = true) Long userId,
                                        @RequestBody LinkDTO linkDTO) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 대해 삭제합니다. - 링크 id 필요")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id,
                                        @RequestParam(required = true) Long userId) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 대해 읽음 완료 표시를 합니다. - 링크 id 필요")
    @PatchMapping("{id}")
    public ResponseEntity<Void> patchLink(@PathVariable Long id,
                                                   @RequestParam(required = true) Long userId) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 조회합니다 - 링크 id 필요")
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

    @ApiOperation("특정 링크에 개별 알람을 추가합니다 - 링크 id 필요")
    @PostMapping("{id}/alarm")
    public ResponseEntity<Void> postLinkAlarm(@PathVariable Long id,
                                              @RequestParam(required = true) Long userId,
                                              @RequestBody LinkAlarmDTO linkAlarmDTO) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 수정합니다 - 링크 id 필요")
    @PutMapping("{id}/alarm")
    public ResponseEntity<Void> putLinkAlarm(@PathVariable Long id,
                                             @RequestParam(required = true) Long userId,
                                             @RequestBody LinkAlarmDTO linkAlarmDTO) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation("특정 링크에 존재하는 개별 알람을 삭제합니다 - 링크 id 필요")
    @DeleteMapping("{id}/alarm")
    public ResponseEntity<Void> deleteLinkAlarm(@PathVariable Long id,
                                                @RequestParam(required = true) Long userId) {
        return ResponseEntity.ok().build();
    }


}

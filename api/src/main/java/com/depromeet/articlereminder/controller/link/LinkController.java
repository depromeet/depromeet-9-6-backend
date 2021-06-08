package com.depromeet.articlereminder.controller.link;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.link.LinkStatus;
import com.depromeet.articlereminder.dto.hashtag.HashtagDTO;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import com.depromeet.articlereminder.dto.link.LinkResponse;
import com.depromeet.articlereminder.dto.link.ReadLinkResponse;
import com.depromeet.articlereminder.service.LinkService;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(tags = {"links"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/links", produces = MediaType.APPLICATION_JSON_VALUE)
public class LinkController {

    private final LinkService linkService;

    @ApiOperation("사용자가 저장한 링크 리스트를 가져옵니다. - 다 읽은 링크만을 조회하고 싶다면 completed 파라미터를 T로 주시면 됩니다. 인증이 필요한 요청입니다. 생성일 역순으로 정렬")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("")
    @Transactional
    public ResponseEntity<Object> getLinks(@RequestHeader(name = "Authorization") String authorization,
                                                       @RequestHeader(name = "userId") Long userId,

                                                       @ApiParam(name = "completed",
                                                               type = "string",
                                                               example = "F",
                                                               value = "다 읽은 링크만 가져올지 flag(다 읽은 링크 : T, 안 읽은 링크 : F, 모두 포함 : ALL)",
                                                               required = false)
                                                       @RequestParam(required = false, defaultValue = "F") String completed,

                                                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());

        Page<Link> linkPage = linkService.findAllByUserAndStatus(userId, completed, pageable);
        Page<LinkResponse> map = linkPage.map(LinkResponse::new);
        return ResponseHandler.generateResponse("사용자가 저장한 링크 리스트 조회에 성공했습니다.", "200", map);
    }

    @ApiOperation("새로운 링크를 등록(추가)합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("")
    @Transactional
    public ResponseEntity<Object> postLink(@RequestHeader(name = "Authorization") String authorization,
                                               @RequestHeader(name = "userId") Long userId,
                                               @RequestBody LinkRequest linkRequest) {
        Link savedLink =  linkService.saveLink(userId, linkRequest);
        LinkResponse linkResponse = new LinkResponse(savedLink);
        return ResponseHandler.generateResponse(
                "새로운 링크 등록에 성공했습니다.",
                "201",
                linkResponse
        );
    }

    @ApiOperation("특정 링크에 대해 상세 조회를 합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("{linkId}")
    @Transactional
    public ResponseEntity<Object> getLink(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long linkId) {
        Link link = linkService.getLink(linkId);
        LinkResponse linkResponse = new LinkResponse(link);
        return ResponseHandler.generateResponse("링크 상세 조회에 성공했습니다.", "202", linkResponse);
    }

    @ApiOperation("특정 링크에 대해 수정합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PutMapping("{linkId}")
    @Transactional
    public ResponseEntity<Object> putLink(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long linkId,
                                        @RequestBody LinkRequest linkRequest) {

        Link updateLink = linkService.updateLink(userId, linkId, linkRequest);
        LinkResponse linkResponse = new LinkResponse(updateLink);

        return ResponseHandler.generateResponse("링크 수정에 성공했습니다.", "203", linkResponse);
    }

    /**
     * 링크 삭제
     * @param authorization
     * @param userId
     * @param linkId
     * @return
     */
    @ApiOperation("특정 링크에 대해 삭제합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @DeleteMapping("{linkId}")
    @Transactional
    public ResponseEntity<Object> deleteLink(@RequestHeader(name = "Authorization") String authorization,
                                             @RequestHeader(name = "userId") Long userId,
                                             @PathVariable Long linkId) {
        linkService.deleteLink(userId, linkId);
        return ResponseHandler.generateResponse(linkId + " 링크 삭제에 성공했습니다.", "204", null);
    }

    @ApiOperation("특정 링크에 대해 읽음 완료 표시를 합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PatchMapping("{linkId}")
    public ResponseEntity<Object> patchLink(@RequestHeader(name = "Authorization") String authorization,
                                                  @RequestHeader(name = "userId") Long userId,
                                                  @PathVariable Long linkId,

                                          @ApiParam(name = "completed",
                                                   type = "string",
                                                   example = "T",
                                                   value = "읽음 완료 flag (읽음 완료 : T, 읽지 않음 : F)",
                                                   required = true)
                                          @RequestBody String completed) {

        // TODO 7일 연속 접속 없을 때 포인트 지급
        Link completedLink = linkService.markAsRead(userId, linkId);
        Long seasonCount = linkService.getReadCountOfSeason(userId);

        ReadLinkResponse readLinkResponse = new ReadLinkResponse(completedLink.getStatus().toString().equals("READ"), seasonCount);
        return ResponseHandler.generateResponse( linkId + " 링크 읽음 완료 표시에 성공하였습니다.","203", readLinkResponse);
    }


    @ApiOperation("사용자가 오늘 읽은 링크 갯수를 조회합니다. - 인증이 필요한 요청입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @GetMapping("count")
    public ResponseEntity<Object> getReadCount(@RequestHeader(name = "Authorization") String authorization,
                                               @RequestHeader(name = "userId") Long userId) {
        Long count = linkService.getReadCountOfToday(userId);
        return ResponseHandler.generateResponse("사용자가 오늘 읽은 아티클 개수 조회에 성공했습니다.", "200", count);
    }

}

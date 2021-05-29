package com.depromeet.articlereminder.controller.link;

import com.depromeet.articlereminder.domain.BaseResponse;
import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.dto.hashtag.HashtagDTO;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import com.depromeet.articlereminder.dto.link.LinkResponse;
import com.depromeet.articlereminder.service.LinkService;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    public BaseResponse<Page<LinkResponse>> getLinks(@RequestHeader(name = "Authorization") String authorization,
                                                     @RequestHeader(name = "userId") Long userId,

                                                     @ApiParam(name = "completed",
                                                               type = "string",
                                                               example = "F",
                                                               value = "다 읽은 링크만 가져올지 flag(다 읽은 링크 : T, 모두 포함 : F)",
                                                                required = false)
                                                       @RequestParam(required = false, defaultValue = "F") String completed,

                                                     @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Link> linkPage = linkService.findAllByUserAndStatus(userId, getLinkStatus(completed), pageable);
        Page<LinkResponse> map = linkPage.map(LinkResponse::new);
        return BaseResponse.of("202", "사용자가 저장한 링크 리스트 조회에 성공했습니다.", map);
    }

    private String getLinkStatus(String completed) {
        return "T".equals(completed) ? "READ" : "UNREAD";
    }

    @ApiOperation("새로운 링크를 등록(추가)합니다. 인증이 필요한 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "header")
    })
    @PostMapping("")
    @Transactional
    public BaseResponse<LinkResponse> postLink(@RequestHeader(name = "Authorization") String authorization,
                                               @RequestHeader(name = "userId") Long userId,
                                               @RequestBody LinkRequest linkRequest) {
        Link savedLink =  linkService.saveLink(userId, linkRequest);
        LinkResponse linkResponse = new LinkResponse(savedLink);

        return BaseResponse.of(
                "201",
                "새로운 링크 등록에 성공했습니다.",
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
    public BaseResponse<LinkResponse> getLink(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long linkId) {
        Link link = linkService.getLink(linkId);

        LinkResponse linkResponse = new LinkResponse(link);
        return BaseResponse.of("202", "링크 상세 조회에 성공했습니다.", linkResponse);
    }

    @ApiOperation("특정 링크에 대해 수정합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
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
    @PutMapping("{linkId}")
    @Transactional
    public BaseResponse<LinkResponse> putLink(@RequestHeader(name = "Authorization") String authorization,
                                                @RequestHeader(name = "userId") Long userId,
                                                @PathVariable Long linkId,
                                        @RequestBody LinkRequest linkRequest) {

        Link updateLink = linkService.updateLink(userId, linkId, linkRequest);
        LinkResponse linkResponse = new LinkResponse(updateLink);

        return BaseResponse.of("203", "링크 수정에 성공했습니다.", linkResponse);

    }

    @ApiOperation("특정 링크에 대해 삭제합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
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
    @DeleteMapping("{linkId}")
    @Transactional
    public BaseResponse<Object> deleteLink(@RequestHeader(name = "Authorization") String authorization,
                                             @RequestHeader(name = "userId") Long userId,
                                             @PathVariable Long linkId) {
        linkService.deleteLink(userId, linkId);

        return BaseResponse.of("204",linkId + " 링크 삭제에 성공했습니다.", null);
    }

    @ApiOperation("특정 링크에 대해 읽음 완료 표시를 합니다. - 링크 id 필요, 인증이 필요한 요청입니다.")
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
    @PatchMapping("{linkId}")
    @Transactional
    public BaseResponse<LinkResponse> patchLink(@RequestHeader(name = "Authorization") String authorization,
                                                  @RequestHeader(name = "userId") Long userId,
                                                  @PathVariable Long linkId,

                                          @ApiParam(name = "completed",
                                                   type = "string",
                                                   example = "T",
                                                   value = "읽음 완료 flag (읽음 완료 : T, 읽지 않음 : F)",
                                                   required = true)
                                          @RequestBody String completed) {

        // TODO 포인트 지급
        Link completedLink = linkService.markAsRead(userId, linkId);
        LinkResponse linkResponse = new LinkResponse(completedLink);

        return BaseResponse.of("201", linkId + " 링크 읽음 완료 표시에 성공하였습니다.", linkResponse);
    }

}

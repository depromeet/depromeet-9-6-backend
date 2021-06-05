package com.depromeet.articlereminder.dto.link;

import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.dto.hashtag.HashtagDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(description = "링크 조회 시 ResponseBody")
@Data
public class LinkResponse {
    @ApiModelProperty(notes = "링크 id",
            example = "1",
            required = true,
            position = 0)
    private Long linkId; // 링크 id


    @ApiModelProperty(notes = "사용자 id",
            example = "1",
            required = true,
            position = 1)
    private Long userId; // 사용자 id

    @ApiModelProperty(notes = "링크 URL",
            example = "https://brunch.co.kr/@delight412/351",
            required = true,
            position = 2)
    private String linkURL; // 링크 url


    @ApiModelProperty(notes = "읽음 완료 상태",
            example = "false",
            required = true,
            position = 3)
    private boolean isCompleted; // 읽음 상태

    private List<HashtagDTO> hashtags; // 해시 태그 id

    @ApiModelProperty(notes = "등록 시각",
            example = "2021-05-01 11:33:22",
            required = true,
            position = 6)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt; // 등록 시각 (생성 시각)


    @ApiModelProperty(notes = "읽음 완료 시각",
            example = "2021-05-11 21:33:22",
            required = true,
            position = 6)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime completedAt; // 읽음 완료 시각

    public LinkResponse(Link link) {
        linkId = link.getId();
        userId = link.getMember().getId();
        linkURL = link.getLinkURL();
        isCompleted = link.getStatus().toString().equals("READ");
        createdAt = link.getCreatedAt();
        completedAt = link.getCompletedAt();
        hashtags = link.getLinkHashtags().stream()
                            .map(HashtagDTO::new)
                            .collect(Collectors.toList());
    }
}

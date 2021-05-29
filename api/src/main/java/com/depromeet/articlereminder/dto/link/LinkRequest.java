package com.depromeet.articlereminder.dto.link;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@ApiModel(description = "링크 추가(줍줍) 시 RequestBody")
@Data
public class LinkRequest {
    @ApiModelProperty(notes = "줍줍할 링크 URL",
            example = "https://brunch.co.kr/@delight412/351",
            required = true,
            position = 0)
    private String linkURL; // 링크 url

    @ApiModelProperty(notes = "해시태그 리스트", example = "[\"디자인\", \"스타트업\"]", required = true, position = 1)
    private List<String> hashtags; // 해시 태그 리스트
}

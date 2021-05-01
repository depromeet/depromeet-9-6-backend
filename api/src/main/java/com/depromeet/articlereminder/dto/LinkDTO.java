package com.depromeet.articlereminder.dto;

import com.depromeet.articlereminder.domain.Hashtag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class LinkDTO {
    private String linkURL; // 링크 url
    private List<String> hashtags; // 해시 태그 리스트
}

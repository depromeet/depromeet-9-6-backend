package com.depromeet.articlereminder.dto;

import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.dto.hashtag.HashtagDTO;
import lombok.Data;

@Data
public class LinkHashtagDTO {

    private HashtagDTO hashtag;

    public LinkHashtagDTO(LinkHashtag linkHashtag) {
        hashtag = new HashtagDTO(linkHashtag.getHashtag());
    }

}

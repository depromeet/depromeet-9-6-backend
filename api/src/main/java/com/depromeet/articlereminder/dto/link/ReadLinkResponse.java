package com.depromeet.articlereminder.dto.link;

import lombok.Data;

@Data
public class ReadLinkResponse {
    private boolean completed;
    private Long seasonCount;

    public ReadLinkResponse(boolean completed, Long seasonCount) {
        this.completed = completed;
        this.seasonCount = seasonCount;
    }
}

package com.depromeet.articlereminder.dto.member;

import lombok.Data;

@Data
public class AppleMemberIdResponse {

    private Long loginId;

    private String nickName;

    public AppleMemberIdResponse(Long loginId, String nickName) {
        this.loginId = loginId;
        this.nickName = nickName;
    }
}

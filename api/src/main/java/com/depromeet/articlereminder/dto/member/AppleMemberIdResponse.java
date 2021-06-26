package com.depromeet.articlereminder.dto.member;

import lombok.Data;

@Data
public class AppleMemberIdResponse {

    private String loginId;

    private String nickName;

    public AppleMemberIdResponse(String loginId, String nickName) {
        this.loginId = loginId;
        this.nickName = nickName;
    }
}

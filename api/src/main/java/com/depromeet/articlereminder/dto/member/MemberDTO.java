package com.depromeet.articlereminder.dto.member;

import lombok.Data;

@Data
public class MemberDTO {

    private long userId;

    private String name;

    private String email;

    // private SocialType socialType;

    private String token;
}

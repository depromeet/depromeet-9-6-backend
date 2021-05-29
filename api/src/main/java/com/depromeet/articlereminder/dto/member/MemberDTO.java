package com.depromeet.articlereminder.dto.member;

import lombok.Data;

@Data
public class MemberDTO {

    private long userId;

    private String token;

    private String name;

    private String email;


}

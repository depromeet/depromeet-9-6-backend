package com.depromeet.articlereminder.dto;

import lombok.Data;

@Data
public class MemberLoginDTO {

    private long userId;
    private String token;
    private String name;
    private String email;
}
package com.depromeet.articlereminder.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private Long userId;

//    private String Comment;
}
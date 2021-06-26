package com.depromeet.articlereminder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "로그인 / 회원가입 Response")
public class LoginResponse {
    @ApiModelProperty(notes = "사용자의 accessToken",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1ZXJKd3QiLCJJRCI6Nn0.DhTT-dnsFdOcY5GDVE00r3tsTNhOP8RSXA24p6Cjx3Y",
            required = true,
            position = 0)
    private String token;

    @ApiModelProperty(notes = "DB에 저장된 사용자 id (pk)",
            example = "1",
            required = true,
            position = 1)
    private Long userId;

    @ApiModelProperty(notes = "사용자 닉네임",
            example = "링줍러1",
            required = true,
            position = 2)
    private String name;
}
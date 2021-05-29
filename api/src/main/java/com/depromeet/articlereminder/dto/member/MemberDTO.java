package com.depromeet.articlereminder.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberDTO {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String email;
}

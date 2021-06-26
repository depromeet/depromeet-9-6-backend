package com.depromeet.articlereminder.dto;

import com.depromeet.articlereminder.domain.member.DeviceType;
import com.depromeet.articlereminder.domain.member.SocialType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "로그인 / 회원가입 Request")
public class MemberLoginDTO {

     @ApiModelProperty(notes = "로그인 id",
             example = "9876543121",
             required = true,
             position = 0)
     private String loginId;

     @ApiModelProperty(notes = "토큰",
             example = "qwertyuiopasdf123a",
             required = true,
             position = 3)
     private String token;

     @ApiModelProperty(notes = "소셜 로그인 타입 (KAKAO / APPLE - 대소문자 구분 없이 보내셔도 됩니다)",
             example = "KAKAO",
             required = true,
             position = 2)
     private String socialType;

     @ApiModelProperty(notes = "디바이스 타입 (AOS / iOS - 대소문자 구분 없이 보내셔도 됩니다)",
             example = "AOS",
             required = true,
             position = 1)
     private String deviceType;

     @ApiModelProperty(notes = "fireBase pushToken",
             example = "depMPdmg9encwSaphKzmRa7k6jzz-VjSte5S9gopcJ8AAAF5a6mGRg",
             required = true,
             position = 4)
     private String pushToken;

     @ApiModelProperty(notes = "사용자 닉네임",
             example = "링줍러1",
             required = true,
             position = 5)
     private String name;
}

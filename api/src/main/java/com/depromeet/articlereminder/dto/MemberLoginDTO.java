package com.depromeet.articlereminder.dto;

import com.depromeet.articlereminder.domain.member.DeviceType;
import com.depromeet.articlereminder.domain.member.SocialType;
import lombok.Data;

@Data
public class MemberLoginDTO {

     private String loginId;
     private String token;
     private String socialType;
     private String deviceType;
     private String pushToken;
     private String name;
}

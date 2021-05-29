package com.depromeet.articlereminder.jwt;

import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.LoginResponse;
import com.depromeet.articlereminder.dto.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAssembler {

    private final JwtService jwtService;

    public MemberInfoResponse toUserResponse(Member user) {
        if (user == null) {
            return null;
        }
        MemberInfoResponse userResponse = new MemberInfoResponse();
        userResponse.setUserId(user.getId());
        userResponse.setName(user.getName());
        //userResponse.setState(user.getState());
        userResponse.setStatus(user.getStatus().name());
        userResponse.setCreatedAt(user.getCreatedAt());
        //userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public LoginResponse toLoginResponse(Member user, String comment) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setToken(jwtService.create(user.getId()));
        loginResponse.setComment(comment);
        return loginResponse;
    }
}


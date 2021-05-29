package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.jwt.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(value = "Token")
@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class TokenController {
    private final JwtService jwtService;

    @ApiOperation("(개발 환경 전용) User 의 token 을 조회합니다.")
    @GetMapping("/{userId}/token")
    public ResponseEntity<String> getUserToken(@PathVariable Long userId) {
        return ResponseEntity.ok(jwtService.create(userId));
    }
}

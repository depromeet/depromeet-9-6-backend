package com.depromeet.articlereminder.jwt;

import lombok.Value;

@Value(staticConstructor = "of")
public class JwtSettings {
    String issuer;
    String secret;
}

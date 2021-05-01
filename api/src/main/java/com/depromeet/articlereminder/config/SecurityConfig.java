package com.depromeet.articlereminder.config;

import com.depromeet.articlereminder.service.KakaoOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final KakaoOAuth2UserService kakaoOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .oauth2Login().userInfoEndpoint().userService(kakaoOAuth2UserService)
                .and()
                .defaultSuccessUrl("/swagger-ui.html")
                .failureUrl("/loginFailure");
    }
}

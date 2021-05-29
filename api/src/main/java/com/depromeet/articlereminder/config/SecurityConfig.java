//package com.depromeet.articlereminder.config;
//
//import com.depromeet.articlereminder.service.KakaoOAuth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
//
//@RequiredArgsConstructor
////@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final KakaoOAuth2UserService kakaoOAuth2UserService;
//
////    private static final String[] AUTH_WHITELIST = {
////            "/",
////            "/h2-console/**",
////            "/swagger-resources/**",
////            "/swagger-ui.html",
////            "/v2/api-docs",
////            "/webjars/**"
////    };
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers(AUTH_WHITELIST).permitAll();
////        http.csrf().disable();
////        http.headers().frameOptions().disable();
////        http
////                .oauth2Login().userInfoEndpoint().userService(kakaoOAuth2UserService)
////                .and()
////                .defaultSuccessUrl("/swagger-ui.html")
////                .failureUrl("/loginFailure");
////    }
//
//}

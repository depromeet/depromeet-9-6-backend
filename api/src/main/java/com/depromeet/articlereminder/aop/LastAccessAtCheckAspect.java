package com.depromeet.articlereminder.aop;

import com.depromeet.articlereminder.common.ResponseHandler;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.jwt.UserAssembler;
import com.depromeet.articlereminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;


@Component
@Aspect
@Order
@Log4j2
@RequiredArgsConstructor
public class LastAccessAtCheckAspect {

    private final MemberService memberService;
    private final UserAssembler userAssembler;


    //except schedule controller
    @Pointcut("execution(* com.depromeet.articlereminder.controller.*.*(..))" +
            "|| execution(* com.depromeet.articlereminder.controller.member.*.*(..))" +
            "|| execution(* com.depromeet.articlereminder.controller.ranking.*.*(..))" +
            "|| execution(* com.depromeet.articlereminder.controller.link.*.*(..))" +
            "|| execution(* com.depromeet.articlereminder.controller.alarm.*.*(..))")
    public void updateAccessAt() throws Throwable {

    }

    @Around("updateAccessAt()")
    public Object doAccessAt(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        Long id = null;
        String token = null;
        String parameterName;

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        for (int i = 0; i < method.getParameters().length; i++) {
            parameterName = method.getParameters()[i].getName();
            if (parameterName.equals("userId"))
                id = (Long) modifiedArgs[i];
            if (parameterName.equals("token"))
                token = (String) modifiedArgs[i];
        }

        
        // Authorization argument만 찾기
        Member member = memberService.findByToken(modifiedArgs[0].toString());

        if (member.getTokenExpiredTime().isBefore(LocalDateTime.now())) {
            return ResponseHandler.generateResponse("토큰시간이 만료되었습니다. 재로그인 해주세요","403", userAssembler.toLoginResponse(member));
        }

        return proceedingJoinPoint.proceed(modifiedArgs);

    }
}
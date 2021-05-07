package com.depromeet.articlereminder.controller;

import com.depromeet.articlereminder.domain.Member;
import com.depromeet.articlereminder.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api(tags = {"members"})
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());

        memberService.join(member);
        return "redirect:/";
    }

    @PostMapping("/members/login")
    public String login(){
        return null;
    }

}

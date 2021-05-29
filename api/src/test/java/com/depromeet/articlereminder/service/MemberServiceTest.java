package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("userId로 회원 조회 테스트")
    void findById() throws Exception {
        // given
        Member member = new Member("member1");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }
}
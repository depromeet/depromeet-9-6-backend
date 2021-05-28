package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa 데이터 변경시 트랜잭션 안에서 동작 한다. 읽기 시에만 true
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    @Transactional // default가 false
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail()); // 멀티스레드 환경이라 unique 제약조건 설정.
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     *
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     *
     * @return
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public Member findOneByEmail(String email) {
        return (Member) memberRepository.findByEmail(email);
    }


    /**
     * 회원 변경
     *
     * @param id
     * @param name
     */
    @Transactional // 변경 감지로 jpa 영속성 컨텍스트가 관리
    public void update(Long id, String token) {
        Member member = memberRepository.findById(id).get();
        member.setToken(token);
    }
}

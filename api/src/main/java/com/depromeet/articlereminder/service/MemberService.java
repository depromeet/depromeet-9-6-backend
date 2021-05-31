package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.member.UserMyPageResponse;
import com.depromeet.articlereminder.exception.LinkNotFoundException;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public boolean findMemberCheckByEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (!findMembers.isEmpty()) {
            return false;
        }
        return true;
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

//    public Member findOneByEmail(String email) {
//        return (Member) memberRepository2.findOneByEmail(email);
//    }

    @Transactional // 변경 감지로 jpa 영속성 컨텍스트가 관리
    public Member update(Member member) {
        Member memberTemp = findById(member.getId());
        member.setToken(member.getToken());
        return memberTemp;
    }

    /**
     * userId로 사용자 조회
     * @param userId
     * @return
     */
    public Member findById(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다"));
        return member;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).stream()
                .filter(member -> email.equals(member.getEmail()))
                .findAny()
                .orElse(null);
    }

    /**
     * 어플 알람 활성화 / 비활성화 메서드
     * @param userId
     * @param alarmEnabled
     * @return
     */
    public Member updateAlarmStatus(Long userId, String alarmEnabled) {
        Member member = memberRepository.findById(userId)
                .map(m -> m.changeAlarmStatus(getAlarmEnabled(alarmEnabled)))
                .orElseThrow(() -> new UserNotFoundException(userId));

        return memberRepository.save(member);
    }

    private AlarmStatus getAlarmEnabled(String alarmEnabled) {
        return "T".equals(alarmEnabled) ? AlarmStatus.ENABLED : AlarmStatus.DISABLED;
    }

    public Member getMyPageUserInfo(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }
}

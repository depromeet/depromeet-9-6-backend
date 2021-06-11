package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.domain.member.MemberIdentifier;
import com.depromeet.articlereminder.dto.member.AppleMemberIdResponse;
import com.depromeet.articlereminder.dto.member.UserMyPageResponse;
import com.depromeet.articlereminder.exception.LinkNotFoundException;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.MemberRepository;
import com.depromeet.articlereminder.service.impl.MemberBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true) // jpa 데이터 변경시 트랜잭션 안에서 동작 한다. 읽기 시에만 true
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LinkService linkService;
    private final AlarmService alarmService;
    private final MemberBadgeService memberBadgeService;
    private final MemberIdentifierService memberIdentifierService;

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
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public boolean findMemberCheckByEmail(long loginId) {
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
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

    public Member findByLoginId(Long loginId) {
        return memberRepository.findByLoginId(loginId).stream()
                .filter(member -> loginId.equals(member.getLoginId()))
                .findAny()
                .orElse(null);
    }

    @Transactional
    public void withdraw(Long loginId) {
        Member member = findByLoginId(loginId);

        linkService.deleteUserLinks(member);
        alarmService.deleteUserAlarms(member);

        memberRepository.delete(member);
    }

    @Transactional
    public Member updatePushToken(Long loginId, String pushToken) {
        Member member = findByLoginId(loginId);

        member.changePushToken(pushToken);

        return memberRepository.save(member);
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
        return memberRepository.findByIdAndGetBadge(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public Page<MemberBadge> getMemberBadges(Long userId, Pageable pageable) {
        Member member = findById(userId);
        return memberBadgeService.findMemberBadgesByUserId(member, pageable);
    }


    @Transactional
    public AppleMemberIdResponse getLoginId(String userIdentifier) {
        MemberIdentifier identifier = memberIdentifierService.getLoginIdByUserIdentifier(userIdentifier);

        Long loginId = identifier.getLoginId();

        Member member = findByLoginId(loginId);

        return new AppleMemberIdResponse(loginId, (member == null) ? null : member.getName());
    }
}

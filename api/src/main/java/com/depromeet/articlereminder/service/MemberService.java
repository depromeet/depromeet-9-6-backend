package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.alarm.AlarmStatus;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.domain.member.MemberIdentifier;
import com.depromeet.articlereminder.dto.member.AppleMemberIdResponse;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        memberBadgeService.obtainInitialBadge(member);

        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public boolean findMemberCheckByEmail(String loginId) {
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        if (!findMembers.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member update(Member member) {
        Member memberTemp = findById(member.getId());
        member.setToken(member.getToken());
        return memberTemp;
    }

    public Member findById(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다"));
        return member;
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).stream()
                .filter(member -> loginId.equals(member.getLoginId()))
                .findAny()
                .orElse(null);
    }

    @Transactional
    public void withdraw(String loginId) {
        Member member = findByLoginId(loginId);

        linkService.deleteUserLinks(member);
        alarmService.deleteUserAlarms(member);

        memberRepository.delete(member);
    }

    @Transactional
    public Member updatePushToken(String loginId, String pushToken) {
        Member member = findByLoginId(loginId);

        member.changePushToken(pushToken);

        return memberRepository.save(member);
    }

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

        String loginId = identifier.getLoginId();

        Member member = findByLoginId(loginId);

        return new AppleMemberIdResponse(loginId, (member == null) ? null : member.getName());
    }
}

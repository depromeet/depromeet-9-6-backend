package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.exception.BadgeNotFoundException;
import com.depromeet.articlereminder.repository.MemberBadgeRepository;

import com.depromeet.articlereminder.service.BadgeService;
import com.depromeet.articlereminder.service.MemberBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBadgeServiceImpl implements MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeService badgeService;

    @Override
    public Page<MemberBadge> findMemberBadgesByUserId(Member member, Pageable pageable) {
        return memberBadgeRepository.findAllByMember(member, pageable);
    }

    @Override
    @Transactional
    public void obtainInitialBadge(Member member) {
        Badge initialBadge = badgeService.getBadge(1L);
        MemberBadge newMemberBadge = MemberBadge.createMemberBadge(member, initialBadge);

        memberBadgeRepository.save(newMemberBadge);
    }

    @Override
    @Transactional
    public MemberBadge changeMemberPointBadge(Member member, int origin ,int currentPoint) {
        if ((origin > 100 && (currentPoint > 100 && currentPoint < 2000)) ||
                (origin  > 2000 && (currentPoint > 2000 &&  currentPoint < 2999)) ||
                (origin > 3000 && (currentPoint > 3000 && currentPoint < 4999)) ||
                (origin > 5000 && (currentPoint > 5000 && currentPoint < 9999)) ||
            origin > 10000) {
            return null;
        }

        Optional<MemberBadge> pointBadge = Optional.ofNullable(memberBadgeRepository.findMemberBadgeByMember(member)
                .orElseThrow(() -> new BadgeNotFoundException()));

        Badge badge = null;

        // FIXME ENUM으로 고치기
        if (origin < 100 && currentPoint >= 100) {
            badge = badgeService.getBadge(2L);
        } else if (origin < 2000 && currentPoint >= 2000) {
            badge = badgeService.getBadge(3L);
        } else if (origin < 3000 && currentPoint >= 3000) {
            badge = badgeService.getBadge(4L);
        } else if (origin < 5000 && currentPoint >= 5000) {
            badge = badgeService.getBadge(5L);
        } else if (origin < 10000 && currentPoint >= 10000) {
            badge = badgeService.getBadge(6L);
        }
        MemberBadge changed = pointBadge.get().changeBadge(badge);
        return memberBadgeRepository.save(changed);
    }
}

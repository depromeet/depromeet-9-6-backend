package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.repository.MemberBadgeRepository;

import com.depromeet.articlereminder.service.BadgeService;
import com.depromeet.articlereminder.service.MemberBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

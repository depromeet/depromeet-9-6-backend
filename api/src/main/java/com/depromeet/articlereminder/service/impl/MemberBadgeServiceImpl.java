package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.repository.MemberBadgeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBadgeServiceImpl implements MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    @Override
    public Page<MemberBadge> findMemberBadgesByUserId(Member member, Pageable pageable) {
        return memberBadgeRepository.findAllByMember(member, pageable);
    }
}

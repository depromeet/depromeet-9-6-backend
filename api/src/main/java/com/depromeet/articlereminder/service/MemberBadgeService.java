package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.member.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberBadgeService {
    Page<MemberBadge> findMemberBadgesByUserId(Member member, Pageable pageable);

    void obtainInitialBadge(Member member);
}

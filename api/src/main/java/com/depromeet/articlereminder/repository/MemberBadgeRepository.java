package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.MemberBadge;
import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    @Query(
            value = "select mb from MemberBadge mb join fetch Badge b " +
                    " on mb.badge.id = b.id" +
                    " where mb.member = :member AND b.badgeCategory = 'SEASON'",
            countQuery = "select count(mb.id) from MemberBadge mb join fetch Badge b " +
                    " on mb.badge.id = b.id " +
                    " where mb.member = :member AND b.badgeCategory = 'SEASON'"
    )
    Page<MemberBadge> findAllByMember(@Param("member") Member member, Pageable pageable);
}

package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
    List<Member> findByLoginId(Long loginId);

    @Query(
            value = "select m From Member m join fetch m.memberBadges" +
                    " where m.id = :userId"
    )
    Optional<Member> findByIdAndGetBadge(@Param("userId") Long userId);
}

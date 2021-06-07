package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
    List<Member> findByLoginId(Long loginId);
}

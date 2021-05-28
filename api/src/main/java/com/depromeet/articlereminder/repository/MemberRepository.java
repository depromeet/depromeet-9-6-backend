package com.depromeet.articlereminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // select m form Member m where m.name = ?
    List<Member> findByName(String name);
    Member findByEmail(String name);

    //Member findByName(String name);
}

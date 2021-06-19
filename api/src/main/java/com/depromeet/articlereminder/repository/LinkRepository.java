package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.link.LinkStatus;
import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    // FIXME QueryDSL로 status 동적으로 처리하기
    @Query(
            value = "select l from Link l join fetch l.member where l.member = :member",
            countQuery = "select count(l.linkURL) from Link l where l.member = :member"
    )
    Page<Link> findByMember(@Param("member") Member member, Pageable pageable);

    @Query(
            value = "select l from Link l join fetch l.member where l.member = :member AND l.status = 'READ'",
            countQuery = "select count(l.linkURL) from Link l where l.member = :member AND l.status = 'READ'"
    )
    Page<Link> findByMemberAndRead(@Param("member") Member member, Pageable pageable);

    @Query(
            value = "select l from Link l join fetch l.member where l.member = :member AND l.status = 'UNREAD'",
            countQuery = "select count(l.linkURL) from Link l where l.member = :member AND l.status = 'UNREAD'"
    )
    Page<Link> findByMemberAndUnread(@Param("member") Member member, Pageable pageable);

    @Query("select count(l.linkURL) from Link l where l.member = :member " +
            "and l.status = :status AND date_format(l.completedAt, '%Y-%m-%d') LIKE %:time%")
    Long findReadCountOfToday(@Param("member") Member member, @Param("status") LinkStatus status, @Param("time") String time);

    @Query("select count(l.linkURL) from Link l where l.member = :member " +
            "and l.status = :status AND date_format(l.completedAt, '%Y-%m') LIKE %:time%")
    Long findReadCountOfSeason(@Param("member") Member member, @Param("status") LinkStatus status, @Param("time") String time);

    @Query(value = "select l from Link l where l.member = :member")
    List<Link> findByMemberId(@Param("member") Member member);
}

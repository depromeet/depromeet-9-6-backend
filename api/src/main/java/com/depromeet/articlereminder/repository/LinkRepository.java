package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.link.LinkStatus;
import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.time.LocalDate;

public interface LinkRepository extends JpaRepository<Link, Long> {
    // FIXME QueryDSL로 status 동적으로 처리하기
    @Query(
            value = "select l from Link l left join fetch l.member",
            countQuery = "select count(l.linkURL) from Link l"
    )
    Page<Link> findByMember(Member member, Pageable pageable);

    @Query(
            value = "select l from Link l left join fetch l.member where l.status = 'READ'",
            countQuery = "select count(l.linkURL) from Link l"
    )
    Page<Link> findByMemberAndRead(Member member, LinkStatus read, Pageable pageable);

    @Query(
            value = "select l from Link l left join fetch l.member where l.status = 'UNREAD'",
            countQuery = "select count(l.linkURL) from Link l"
    )
    Page<Link> findByMemberAndUnread(Member member, LinkStatus read, Pageable pageable);


    @Query("select count(l.linkURL) from Link l where l.member = :member " +
            "and l.status = :status AND SUBSTRING(l.completedAt, 0, 10) LIKE %:time%")
    Long findCountOfReadToday(@Param("member") Member member, @Param("status") LinkStatus status, @Param("time") String time);
    // 1. findAll
    // 2. findById
    // 3. save
    // 4. update
    // 5. delete
    // 6. makeRead
}

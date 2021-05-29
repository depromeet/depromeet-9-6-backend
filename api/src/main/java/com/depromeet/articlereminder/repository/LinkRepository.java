package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.link.LinkStatus;
import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LinkRepository extends JpaRepository<Link, Long> {
//
    @Query(
            value = "select l from Link l left join fetch l.member m",
            countQuery = "select count(l.linkURL) from Link l"
    )
    Page<Link> findByMemberAndStatus(Member member, LinkStatus status, Pageable pageable);

    @Query("select count(l.linkURL) from Link l where l.member = :member " +
            "and l.status = :status AND l.completedAt LIKE %:time%")
    Long findCountOfReadToday(@Param("member") Member member, @Param("status") LinkStatus status, @Param("time") String time);
    // 1. findAll
    // 2. findById
    // 3. save
    // 4. update
    // 5. delete
    // 6. makeRead
}

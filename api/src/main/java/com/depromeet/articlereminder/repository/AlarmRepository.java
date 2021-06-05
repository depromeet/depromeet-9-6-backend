package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.alarm.Alarm;
import com.depromeet.articlereminder.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query(
            value = "select a From Alarm a where a.member= :member"
    )
    List<Alarm> findAllByMember(@Param("member") Member member);

    @Query(
            value = "select a From Alarm a join fetch a.member m"
    )
    List<Alarm> findAll();
}

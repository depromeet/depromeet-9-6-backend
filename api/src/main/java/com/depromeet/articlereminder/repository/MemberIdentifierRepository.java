package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.member.MemberIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberIdentifierRepository extends JpaRepository<MemberIdentifier, Long> {

    @Query(
            value = "select mi From MemberIdentifier mi where mi.appleUserIdentifier = :identifier"
    )
    Optional<MemberIdentifier> findByAppleUserIdentifier(@Param("identifier") String identifier);

//    Optional<MemberIdentifier> findMemberIdentifierByAppleUserIdentifier(String appleUserIdentifier);

    Optional<MemberIdentifier> findById(Long id);
}

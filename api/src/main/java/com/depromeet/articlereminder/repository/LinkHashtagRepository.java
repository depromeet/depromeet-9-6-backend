package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.link.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkHashtagRepository extends JpaRepository<LinkHashtag, Long> {
    @Query(
            value = "select lh from LinkHashtag lh where lh.link = :link"
    )
    List<LinkHashtag> findAllByLink(@Param("link") Link link);
}

package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LinkService {
    Page<Link> findAllByUserAndStatus(Long userId, String linkStatus, Pageable pageable);

    Link getLink(Long linkId);

    Link saveLink(Long userId, LinkRequest linkRequest);

    Hashtag saveHashtag(String current);

    Link updateLink(Long userId, Long linkId, LinkRequest linkRequest);

    void deleteLink(Long userId, Long linkId);

    Link markAsRead(Long userId, Long linkId);

    void deleteUserLinks(Member member);

    Long getReadCountOfToday(Long userId);

    Long getReadCountOfSeason(Long userId);

    List<Link> getLinksByUserId(Member member);
}

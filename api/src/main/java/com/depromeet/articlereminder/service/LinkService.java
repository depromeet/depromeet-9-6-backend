package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkService {
    public Page<Link> findAllByUserAndStatus(Long userId, String linkStatus, Pageable pageable);

    public Link getLink(Long linkId);

    public Link saveLink(Long userId, LinkRequest linkRequest);

    public Link updateLink(Long userId, Long linkId, LinkRequest linkRequest);

    public void deleteLink(Long userId, Long linkId);

    public Link markAsRead(Long userId, Long linkId);
}

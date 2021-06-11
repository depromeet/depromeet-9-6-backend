package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.badge.Badge;
import com.depromeet.articlereminder.exception.BadgeNotFoundException;
import com.depromeet.articlereminder.repository.BadgeRepository;
import com.depromeet.articlereminder.service.BadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Override
    public Badge getBadge(Long badgeId) {
        return badgeRepository.findById(badgeId).orElseThrow(() -> new BadgeNotFoundException());
    }
}

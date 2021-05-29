package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}

package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MatchPreview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPreviewRepository extends JpaRepository<MatchPreview, Long> {
}

package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MatchPreview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPreviewRepository extends JpaRepository<MatchPreview, Long> {
	
	@Query("select matchPreview from MatchPreview matchPreview where matchPreview.fixtureId =:fixtureId")
	Optional<MatchPreview> findOneByFixtureId(@Param("fixtureId") Integer fixtureId);
}

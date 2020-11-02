package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MatchPreview entity.
 */
@Repository
public interface MatchPreviewRepository extends JpaRepository<MatchPreview, Long> {

    @Query(value = "select distinct matchPreview from MatchPreview matchPreview left join fetch matchPreview.teams",
        countQuery = "select count(distinct matchPreview) from MatchPreview matchPreview")
    Page<MatchPreview> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct matchPreview from MatchPreview matchPreview left join fetch matchPreview.teams")
    List<MatchPreview> findAllWithEagerRelationships();

    @Query("select matchPreview from MatchPreview matchPreview left join fetch matchPreview.teams where matchPreview.id =:id")
    Optional<MatchPreview> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select matchPreview from MatchPreview matchPreview where matchPreview.fixtureId =:fixtureId")
	Optional<MatchPreview> findOneByFixtureId(@Param("fixtureId") Integer fixtureId);
}

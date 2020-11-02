package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Parts;
import com.betpreview.betmanage.domain.Team;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Parts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartsRepository extends JpaRepository<Parts, Long> {
	
	@Query("select parts from Parts parts where parts.matchPreview = :matchPreview")
	Optional<Parts> findOneByMatchPreview(@Param("matchPreview") MatchPreview matchPreview);
}

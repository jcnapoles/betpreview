package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.domain.Sport;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
	
	@Query("select sport from Sport sport where sport.sportName =:sportName")
    Optional<Sport> findOneBySportName(@Param("sportName") String sportName);
}

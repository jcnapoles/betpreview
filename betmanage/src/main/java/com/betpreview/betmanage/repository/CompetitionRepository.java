package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
	
	 @Query("select competition from Competition competition where competition.sportscribeId =:sportscribeId")
	 Optional<Competition> findOneBySportscribeId(@Param("sportscribeId") Integer sportscribeId);
}

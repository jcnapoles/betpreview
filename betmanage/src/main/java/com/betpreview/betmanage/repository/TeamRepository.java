package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Team;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
	@Query("select team from Team team where team.teamId =:sportscribeId")
	Optional<Team> findOneBySportscribeId(@Param("sportscribeId") Integer sportscribeId);
}

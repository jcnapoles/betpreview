package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.TeamSocial;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TeamSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSocialRepository extends JpaRepository<TeamSocial, Long> {
	@Query("select teamSocial from TeamSocial teamSocial where teamSocial.match =:match")
	Optional<TeamSocial> findOneByMatch(@Param("match") String match);
}

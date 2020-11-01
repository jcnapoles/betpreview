package com.betpreview.betmanage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betpreview.betmanage.domain.TeamSocial;

/**
 * Spring Data  repository for the TeamSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSocialRepository extends JpaRepository<TeamSocial, Long> {
	
	/*
	 * @Query("select teamSocial from TeamSocial teamSocial where teamSocial.tag =:tag"
	 * ) Optional<TeamSocial> findOneByTag(@Param("tag") String tag);
	 */
}

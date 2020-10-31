package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.TeamSocial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TeamSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSocialRepository extends JpaRepository<TeamSocial, Long> {
}

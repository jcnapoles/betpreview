package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Competition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}

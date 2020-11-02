package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Competition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Competition entity.
 */
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query(value = "select distinct competition from Competition competition left join fetch competition.teams",
        countQuery = "select count(distinct competition) from Competition competition")
    Page<Competition> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct competition from Competition competition left join fetch competition.teams")
    List<Competition> findAllWithEagerRelationships();

    @Query("select competition from Competition competition left join fetch competition.teams where competition.id =:id")
    Optional<Competition> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select competition from Competition competition where competition.sportscribeId =:sportscribeId")
	 Optional<Competition> findOneBySportscribeId(@Param("sportscribeId") Integer sportscribeId);
}

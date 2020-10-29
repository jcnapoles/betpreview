package com.betpreview.betmanage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betpreview.betmanage.domain.Team;

/**
 * Spring Data  repository for the Team entity.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "select distinct team from Team team left join fetch team.matchPreviews",
        countQuery = "select count(distinct team) from Team team")
    Page<Team> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct team from Team team left join fetch team.matchPreviews")
    List<Team> findAllWithEagerRelationships();

    @Query("select team from Team team left join fetch team.matchPreviews where team.id =:id")
    Optional<Team> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select team from Team team where team.teamId =:sportscribeId")
	Optional<Team> findOneBySportscribeId(@Param("sportscribeId") Integer sportscribeId);
}

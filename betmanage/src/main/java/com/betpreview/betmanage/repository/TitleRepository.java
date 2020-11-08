package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.Title;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Title entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
	
	@Query("select title from Title title where title.matchPreview =:matchPreview")
	List<Title> findAllByMatchPreview(@Param("matchPreview") MatchPreview matchPreview);
}

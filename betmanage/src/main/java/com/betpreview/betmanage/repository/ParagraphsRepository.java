package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Paragraphs;
import com.betpreview.betmanage.domain.Title;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Paragraphs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParagraphsRepository extends JpaRepository<Paragraphs, Long> {
	
	@Query("select paragraphs from Paragraphs paragraphs where paragraphs.matchPreview =:matchPreview")
	List<Paragraphs> findAllByMatchPreview(@Param("matchPreview") MatchPreview matchPreview);
}

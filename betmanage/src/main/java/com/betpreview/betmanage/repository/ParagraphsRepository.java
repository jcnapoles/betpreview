package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Paragraphs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Paragraphs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParagraphsRepository extends JpaRepository<Paragraphs, Long> {
}

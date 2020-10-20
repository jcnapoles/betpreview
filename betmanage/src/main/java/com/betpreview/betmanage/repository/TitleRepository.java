package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Title;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Title entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
}

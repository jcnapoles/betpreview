package com.betpreview.betmanage.repository;

import com.betpreview.betmanage.domain.Parts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Parts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartsRepository extends JpaRepository<Parts, Long> {
}

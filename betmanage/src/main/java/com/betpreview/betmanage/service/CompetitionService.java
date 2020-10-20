package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.Competition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Competition}.
 */
public interface CompetitionService {

    /**
     * Save a competition.
     *
     * @param competition the entity to save.
     * @return the persisted entity.
     */
    Competition save(Competition competition);

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Competition> findAll(Pageable pageable);


    /**
     * Get the "id" competition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Competition> findOne(Long id);

    /**
     * Delete the "id" competition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the competition corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Competition> search(String query, Pageable pageable);
}

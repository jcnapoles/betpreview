package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.MatchPreview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MatchPreview}.
 */
public interface MatchPreviewService {

    /**
     * Save a matchPreview.
     *
     * @param matchPreview the entity to save.
     * @return the persisted entity.
     */
    MatchPreview save(MatchPreview matchPreview);

    /**
     * Get all the matchPreviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatchPreview> findAll(Pageable pageable);


    /**
     * Get the "id" matchPreview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchPreview> findOne(Long id);

    /**
     * Delete the "id" matchPreview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the matchPreview corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatchPreview> search(String query, Pageable pageable);
}

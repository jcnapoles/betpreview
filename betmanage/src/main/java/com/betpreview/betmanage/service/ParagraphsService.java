package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.Paragraphs;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Paragraphs}.
 */
public interface ParagraphsService {

    /**
     * Save a paragraphs.
     *
     * @param paragraphs the entity to save.
     * @return the persisted entity.
     */
    Paragraphs save(Paragraphs paragraphs);

    /**
     * Get all the paragraphs.
     *
     * @return the list of entities.
     */
    List<Paragraphs> findAll();


    /**
     * Get the "id" paragraphs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paragraphs> findOne(Long id);

    /**
     * Delete the "id" paragraphs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paragraphs corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Paragraphs> search(String query);
}

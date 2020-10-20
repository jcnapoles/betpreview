package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.Title;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Title}.
 */
public interface TitleService {

    /**
     * Save a title.
     *
     * @param title the entity to save.
     * @return the persisted entity.
     */
    Title save(Title title);

    /**
     * Get all the titles.
     *
     * @return the list of entities.
     */
    List<Title> findAll();


    /**
     * Get the "id" title.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Title> findOne(Long id);

    /**
     * Delete the "id" title.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the title corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Title> search(String query);
}

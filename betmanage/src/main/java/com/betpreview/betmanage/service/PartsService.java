package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.Parts;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Parts}.
 */
public interface PartsService {

    /**
     * Save a parts.
     *
     * @param parts the entity to save.
     * @return the persisted entity.
     */
    Parts save(Parts parts);

    /**
     * Get all the parts.
     *
     * @return the list of entities.
     */
    List<Parts> findAll();


    /**
     * Get the "id" parts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Parts> findOne(Long id);

    /**
     * Delete the "id" parts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the parts corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Parts> search(String query);
}

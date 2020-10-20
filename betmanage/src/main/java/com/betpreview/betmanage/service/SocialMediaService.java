package com.betpreview.betmanage.service;

import com.betpreview.betmanage.domain.SocialMedia;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SocialMedia}.
 */
public interface SocialMediaService {

    /**
     * Save a socialMedia.
     *
     * @param socialMedia the entity to save.
     * @return the persisted entity.
     */
    SocialMedia save(SocialMedia socialMedia);

    /**
     * Get all the socialMedias.
     *
     * @return the list of entities.
     */
    List<SocialMedia> findAll();


    /**
     * Get the "id" socialMedia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialMedia> findOne(Long id);

    /**
     * Delete the "id" socialMedia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the socialMedia corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<SocialMedia> search(String query);
}

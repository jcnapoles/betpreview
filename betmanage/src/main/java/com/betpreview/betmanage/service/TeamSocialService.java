package com.betpreview.betmanage.service;

import java.util.List;
import java.util.Optional;

import com.betpreview.betmanage.domain.TeamSocial;

/**
 * Service Interface for managing {@link TeamSocial}.
 */
public interface TeamSocialService {

    /**
     * Save a teamSocial.
     *
     * @param teamSocial the entity to save.
     * @return the persisted entity.
     */
    TeamSocial save(TeamSocial teamSocial);

    /**
     * Get all the teamSocials.
     *
     * @return the list of entities.
     */
    List<TeamSocial> findAll();


    /**
     * Get the "id" teamSocial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeamSocial> findOne(Long id);

    /**
     * Delete the "id" teamSocial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the teamSocial corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<TeamSocial> search(String query);
    
    Optional<TeamSocial> findOneByMatch(String match);
}

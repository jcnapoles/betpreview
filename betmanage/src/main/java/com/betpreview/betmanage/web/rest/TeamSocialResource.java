package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.domain.TeamSocial;
import com.betpreview.betmanage.service.TeamSocialService;
import com.betpreview.betmanage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.betpreview.betmanage.domain.TeamSocial}.
 */
@RestController
@RequestMapping("/api")
public class TeamSocialResource {

    private final Logger log = LoggerFactory.getLogger(TeamSocialResource.class);

    private static final String ENTITY_NAME = "teamSocial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamSocialService teamSocialService;

    public TeamSocialResource(TeamSocialService teamSocialService) {
        this.teamSocialService = teamSocialService;
    }

    /**
     * {@code POST  /team-socials} : Create a new teamSocial.
     *
     * @param teamSocial the teamSocial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamSocial, or with status {@code 400 (Bad Request)} if the teamSocial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-socials")
    public ResponseEntity<TeamSocial> createTeamSocial(@RequestBody TeamSocial teamSocial) throws URISyntaxException {
        log.debug("REST request to save TeamSocial : {}", teamSocial);
        if (teamSocial.getId() != null) {
            throw new BadRequestAlertException("A new teamSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamSocial result = teamSocialService.save(teamSocial);
        return ResponseEntity.created(new URI("/api/team-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-socials} : Updates an existing teamSocial.
     *
     * @param teamSocial the teamSocial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamSocial,
     * or with status {@code 400 (Bad Request)} if the teamSocial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamSocial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-socials")
    public ResponseEntity<TeamSocial> updateTeamSocial(@RequestBody TeamSocial teamSocial) throws URISyntaxException {
        log.debug("REST request to update TeamSocial : {}", teamSocial);
        if (teamSocial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamSocial result = teamSocialService.save(teamSocial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamSocial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /team-socials} : get all the teamSocials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamSocials in body.
     */
    @GetMapping("/team-socials")
    public List<TeamSocial> getAllTeamSocials() {
        log.debug("REST request to get all TeamSocials");
        return teamSocialService.findAll();
    }

    /**
     * {@code GET  /team-socials/:id} : get the "id" teamSocial.
     *
     * @param id the id of the teamSocial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamSocial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-socials/{id}")
    public ResponseEntity<TeamSocial> getTeamSocial(@PathVariable Long id) {
        log.debug("REST request to get TeamSocial : {}", id);
        Optional<TeamSocial> teamSocial = teamSocialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamSocial);
    }

    /**
     * {@code DELETE  /team-socials/:id} : delete the "id" teamSocial.
     *
     * @param id the id of the teamSocial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-socials/{id}")
    public ResponseEntity<Void> deleteTeamSocial(@PathVariable Long id) {
        log.debug("REST request to delete TeamSocial : {}", id);
        teamSocialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/team-socials?query=:query} : search for the teamSocial corresponding
     * to the query.
     *
     * @param query the query of the teamSocial search.
     * @return the result of the search.
     */
    @GetMapping("/_search/team-socials")
    public List<TeamSocial> searchTeamSocials(@RequestParam String query) {
        log.debug("REST request to search TeamSocials for query {}", query);
        return teamSocialService.search(query);
    }
}

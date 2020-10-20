package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.betpreview.betmanage.domain.Sport}.
 */
@RestController
@RequestMapping("/api")
public class SportResource {

    private final Logger log = LoggerFactory.getLogger(SportResource.class);

    private static final String ENTITY_NAME = "sport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportService sportService;

    public SportResource(SportService sportService) {
        this.sportService = sportService;
    }

    /**
     * {@code POST  /sports} : Create a new sport.
     *
     * @param sport the sport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sport, or with status {@code 400 (Bad Request)} if the sport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sports")
    public ResponseEntity<Sport> createSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to save Sport : {}", sport);
        if (sport.getId() != null) {
            throw new BadRequestAlertException("A new sport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sport result = sportService.save(sport);
        return ResponseEntity.created(new URI("/api/sports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sports} : Updates an existing sport.
     *
     * @param sport the sport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sport,
     * or with status {@code 400 (Bad Request)} if the sport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sports")
    public ResponseEntity<Sport> updateSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to update Sport : {}", sport);
        if (sport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sport result = sportService.save(sport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sports} : get all the sports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sports in body.
     */
    @GetMapping("/sports")
    public List<Sport> getAllSports() {
        log.debug("REST request to get all Sports");
        return sportService.findAll();
    }

    /**
     * {@code GET  /sports/:id} : get the "id" sport.
     *
     * @param id the id of the sport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sports/{id}")
    public ResponseEntity<Sport> getSport(@PathVariable Long id) {
        log.debug("REST request to get Sport : {}", id);
        Optional<Sport> sport = sportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sport);
    }

    /**
     * {@code DELETE  /sports/:id} : delete the "id" sport.
     *
     * @param id the id of the sport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sports/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        log.debug("REST request to delete Sport : {}", id);
        sportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sports?query=:query} : search for the sport corresponding
     * to the query.
     *
     * @param query the query of the sport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/sports")
    public List<Sport> searchSports(@RequestParam String query) {
        log.debug("REST request to search Sports for query {}", query);
        return sportService.search(query);
    }
}

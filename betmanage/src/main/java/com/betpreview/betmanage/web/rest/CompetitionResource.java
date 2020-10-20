package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
 * REST controller for managing {@link com.betpreview.betmanage.domain.Competition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionService competitionService;

    public CompetitionResource(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    /**
     * {@code POST  /competitions} : Create a new competition.
     *
     * @param competition the competition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competition, or with status {@code 400 (Bad Request)} if the competition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitions")
    public ResponseEntity<Competition> createCompetition(@Valid @RequestBody Competition competition) throws URISyntaxException {
        log.debug("REST request to save Competition : {}", competition);
        if (competition.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competition result = competitionService.save(competition);
        return ResponseEntity.created(new URI("/api/competitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitions} : Updates an existing competition.
     *
     * @param competition the competition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competition,
     * or with status {@code 400 (Bad Request)} if the competition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitions")
    public ResponseEntity<Competition> updateCompetition(@Valid @RequestBody Competition competition) throws URISyntaxException {
        log.debug("REST request to update Competition : {}", competition);
        if (competition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Competition result = competitionService.save(competition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/competitions")
    public ResponseEntity<List<Competition>> getAllCompetitions(Pageable pageable) {
        log.debug("REST request to get a page of Competitions");
        Page<Competition> page = competitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitions/:id} : get the "id" competition.
     *
     * @param id the id of the competition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitions/{id}")
    public ResponseEntity<Competition> getCompetition(@PathVariable Long id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<Competition> competition = competitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competition);
    }

    /**
     * {@code DELETE  /competitions/:id} : delete the "id" competition.
     *
     * @param id the id of the competition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competitions?query=:query} : search for the competition corresponding
     * to the query.
     *
     * @param query the query of the competition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competitions")
    public ResponseEntity<List<Competition>> searchCompetitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Competitions for query {}", query);
        Page<Competition> page = competitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}

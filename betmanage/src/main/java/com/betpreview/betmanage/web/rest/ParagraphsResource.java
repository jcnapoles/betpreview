package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.domain.Paragraphs;
import com.betpreview.betmanage.service.ParagraphsService;
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
 * REST controller for managing {@link com.betpreview.betmanage.domain.Paragraphs}.
 */
@RestController
@RequestMapping("/api")
public class ParagraphsResource {

    private final Logger log = LoggerFactory.getLogger(ParagraphsResource.class);

    private static final String ENTITY_NAME = "paragraphs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParagraphsService paragraphsService;

    public ParagraphsResource(ParagraphsService paragraphsService) {
        this.paragraphsService = paragraphsService;
    }

    /**
     * {@code POST  /paragraphs} : Create a new paragraphs.
     *
     * @param paragraphs the paragraphs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paragraphs, or with status {@code 400 (Bad Request)} if the paragraphs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paragraphs")
    public ResponseEntity<Paragraphs> createParagraphs(@RequestBody Paragraphs paragraphs) throws URISyntaxException {
        log.debug("REST request to save Paragraphs : {}", paragraphs);
        if (paragraphs.getId() != null) {
            throw new BadRequestAlertException("A new paragraphs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paragraphs result = paragraphsService.save(paragraphs);
        return ResponseEntity.created(new URI("/api/paragraphs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paragraphs} : Updates an existing paragraphs.
     *
     * @param paragraphs the paragraphs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paragraphs,
     * or with status {@code 400 (Bad Request)} if the paragraphs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paragraphs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paragraphs")
    public ResponseEntity<Paragraphs> updateParagraphs(@RequestBody Paragraphs paragraphs) throws URISyntaxException {
        log.debug("REST request to update Paragraphs : {}", paragraphs);
        if (paragraphs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paragraphs result = paragraphsService.save(paragraphs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paragraphs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paragraphs} : get all the paragraphs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paragraphs in body.
     */
    @GetMapping("/paragraphs")
    public List<Paragraphs> getAllParagraphs() {
        log.debug("REST request to get all Paragraphs");
        return paragraphsService.findAll();
    }

    /**
     * {@code GET  /paragraphs/:id} : get the "id" paragraphs.
     *
     * @param id the id of the paragraphs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paragraphs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paragraphs/{id}")
    public ResponseEntity<Paragraphs> getParagraphs(@PathVariable Long id) {
        log.debug("REST request to get Paragraphs : {}", id);
        Optional<Paragraphs> paragraphs = paragraphsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paragraphs);
    }

    /**
     * {@code DELETE  /paragraphs/:id} : delete the "id" paragraphs.
     *
     * @param id the id of the paragraphs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paragraphs/{id}")
    public ResponseEntity<Void> deleteParagraphs(@PathVariable Long id) {
        log.debug("REST request to delete Paragraphs : {}", id);
        paragraphsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/paragraphs?query=:query} : search for the paragraphs corresponding
     * to the query.
     *
     * @param query the query of the paragraphs search.
     * @return the result of the search.
     */
    @GetMapping("/_search/paragraphs")
    public List<Paragraphs> searchParagraphs(@RequestParam String query) {
        log.debug("REST request to search Paragraphs for query {}", query);
        return paragraphsService.search(query);
    }
}

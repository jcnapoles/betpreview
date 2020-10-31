package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.domain.Parts;
import com.betpreview.betmanage.service.PartsService;
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
 * REST controller for managing {@link com.betpreview.betmanage.domain.Parts}.
 */
@RestController
@RequestMapping("/api")
public class PartsResource {

    private final Logger log = LoggerFactory.getLogger(PartsResource.class);

    private static final String ENTITY_NAME = "parts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartsService partsService;

    public PartsResource(PartsService partsService) {
        this.partsService = partsService;
    }

    /**
     * {@code POST  /parts} : Create a new parts.
     *
     * @param parts the parts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parts, or with status {@code 400 (Bad Request)} if the parts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parts")
    public ResponseEntity<Parts> createParts(@RequestBody Parts parts) throws URISyntaxException {
        log.debug("REST request to save Parts : {}", parts);
        if (parts.getId() != null) {
            throw new BadRequestAlertException("A new parts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parts result = partsService.save(parts);
        return ResponseEntity.created(new URI("/api/parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parts} : Updates an existing parts.
     *
     * @param parts the parts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parts,
     * or with status {@code 400 (Bad Request)} if the parts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parts")
    public ResponseEntity<Parts> updateParts(@RequestBody Parts parts) throws URISyntaxException {
        log.debug("REST request to update Parts : {}", parts);
        if (parts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parts result = partsService.save(parts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parts.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parts} : get all the parts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parts in body.
     */
    @GetMapping("/parts")
    public List<Parts> getAllParts() {
        log.debug("REST request to get all Parts");
        return partsService.findAll();
    }

    /**
     * {@code GET  /parts/:id} : get the "id" parts.
     *
     * @param id the id of the parts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parts/{id}")
    public ResponseEntity<Parts> getParts(@PathVariable Long id) {
        log.debug("REST request to get Parts : {}", id);
        Optional<Parts> parts = partsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parts);
    }

    /**
     * {@code DELETE  /parts/:id} : delete the "id" parts.
     *
     * @param id the id of the parts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parts/{id}")
    public ResponseEntity<Void> deleteParts(@PathVariable Long id) {
        log.debug("REST request to delete Parts : {}", id);
        partsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/parts?query=:query} : search for the parts corresponding
     * to the query.
     *
     * @param query the query of the parts search.
     * @return the result of the search.
     */
    @GetMapping("/_search/parts")
    public List<Parts> searchParts(@RequestParam String query) {
        log.debug("REST request to search Parts for query {}", query);
        return partsService.search(query);
    }
}

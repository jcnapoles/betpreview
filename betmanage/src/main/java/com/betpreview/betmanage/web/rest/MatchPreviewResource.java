package com.betpreview.betmanage.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.integration.SportScribeAPI;
import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.service.CountryService;
import com.betpreview.betmanage.service.MatchPreviewService;
import com.betpreview.betmanage.service.ParagraphsService;
import com.betpreview.betmanage.service.PartsService;
import com.betpreview.betmanage.service.SocialMediaService;
import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.service.TeamService;
import com.betpreview.betmanage.service.TeamSocialService;
import com.betpreview.betmanage.service.TitleService;
import com.betpreview.betmanage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.betpreview.betmanage.domain.MatchPreview}.
 */
@RestController
@RequestMapping("/api")
public class MatchPreviewResource {

    private final Logger log = LoggerFactory.getLogger(MatchPreviewResource.class);

    private static final String ENTITY_NAME = "matchPreview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchPreviewService matchPreviewService;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private SportService sportService;
    
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private TeamService teamService;    
   
    @Autowired
    private ParagraphsService paragraphsService;
    
    @Autowired
    private TitleService titleService;
	
    @Autowired
	private PartsService partsService;
    
    @Autowired
    private  CompetitionService competitionService;
    
    @Autowired
    private SocialMediaService socialMediaService;
    
    @Autowired
    private TeamSocialService teamSocialService;

    public MatchPreviewResource(MatchPreviewService matchPreviewService) {
        this.matchPreviewService = matchPreviewService;
    }

    /**
     * {@code POST  /match-previews} : Create a new matchPreview.
     *
     * @param matchPreview the matchPreview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchPreview, or with status {@code 400 (Bad Request)} if the matchPreview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-previews")
    public ResponseEntity<MatchPreview> createMatchPreview(@Valid @RequestBody MatchPreview matchPreview) throws URISyntaxException {
        log.debug("REST request to save MatchPreview : {}", matchPreview);
        if (matchPreview.getId() != null) {
            throw new BadRequestAlertException("A new matchPreview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchPreview result = matchPreviewService.save(matchPreview);
        return ResponseEntity.created(new URI("/api/match-previews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-previews} : Updates an existing matchPreview.
     *
     * @param matchPreview the matchPreview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchPreview,
     * or with status {@code 400 (Bad Request)} if the matchPreview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchPreview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-previews")
    public ResponseEntity<MatchPreview> updateMatchPreview(@Valid @RequestBody MatchPreview matchPreview) throws URISyntaxException {
        log.debug("REST request to update MatchPreview : {}", matchPreview);
        if (matchPreview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchPreview result = matchPreviewService.save(matchPreview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchPreview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-previews} : get all the matchPreviews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchPreviews in body.
     */
    @GetMapping("/match-previews")
    public ResponseEntity<List<MatchPreview>> getAllMatchPreviews(Pageable pageable) {
        log.debug("REST request to get a page of MatchPreviews");
        Page<MatchPreview> page = matchPreviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-previews/:id} : get the "id" matchPreview.
     *
     * @param id the id of the matchPreview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchPreview, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-previews/{id}")
    public ResponseEntity<MatchPreview> getMatchPreview(@PathVariable Long id) {
        log.debug("REST request to get MatchPreview : {}", id);
        Optional<MatchPreview> matchPreview = matchPreviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchPreview);
    }

    /**
     * {@code DELETE  /match-previews/:id} : delete the "id" matchPreview.
     *
     * @param id the id of the matchPreview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-previews/{id}")
    public ResponseEntity<Void> deleteMatchPreview(@PathVariable Long id) {
        log.debug("REST request to delete MatchPreview : {}", id);
        matchPreviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/match-previews?query=:query} : search for the matchPreview corresponding
     * to the query.
     *
     * @param query the query of the matchPreview search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/match-previews")
    public ResponseEntity<List<MatchPreview>> searchMatchPreviews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchPreviews for query {}", query);
        Page<MatchPreview> page = matchPreviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    
    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/match-previews/loadAPIMatchPreviewByTeamId/{teamId}")
    public ResponseEntity<MatchPreview> loadAPIMatchPreviewByTeamId(@PathVariable Integer teamId, Pageable pageable) {
        log.debug("REST request to get a page of MatchPreview");
        /*IMPLEMENTAR*/
        String url = environment.getProperty("sportscribe.url");
        String keyName = environment.getProperty("sportscribe.keyName");
        String keyValue = environment.getProperty("sportscribe.keyValue");       
        String language = "en";
        SportScribeAPI sportScribeAPI = new SportScribeAPI(url, keyName, keyValue, language, competitionService, sportService, countryService, teamService, matchPreviewService, paragraphsService, titleService, partsService, socialMediaService, teamSocialService);
        
        MatchPreview matchPreview = sportScribeAPI.getMatchPreviewByTeamId(teamId); 
       
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchPreview.getId().toString()))
                .body(matchPreview);
    }
    
    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/match-previews/loadAPIMatchPreviewByDate/{date}")
    public ResponseEntity<List<MatchPreview>> loadAPIMatchPreviewByDate(@PathVariable Date date, Pageable pageable) {
        log.debug("REST request to get a page of MatchPreview");
        
        String url = environment.getProperty("sportscribe.url");
        String keyName = environment.getProperty("sportscribe.keyName");
        String keyValue = environment.getProperty("sportscribe.keyValue");       
        String language = "en";
        SportScribeAPI sportScribeAPI = new SportScribeAPI(url, keyName, keyValue, language, competitionService, sportService, countryService, teamService, matchPreviewService, paragraphsService, titleService, partsService, socialMediaService, teamSocialService);
        List<MatchPreview> matchPreviewList = sportScribeAPI.getMatchPreviewsByDate(date);
        
        Page<MatchPreview> page = new PageImpl<MatchPreview>(matchPreviewList);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

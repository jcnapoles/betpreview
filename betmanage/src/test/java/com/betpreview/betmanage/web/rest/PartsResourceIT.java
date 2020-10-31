package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Parts;
import com.betpreview.betmanage.repository.PartsRepository;
import com.betpreview.betmanage.repository.search.PartsSearchRepository;
import com.betpreview.betmanage.service.PartsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartsResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartsResourceIT {

    private static final String DEFAULT_INTRO = "AAAAAAAAAA";
    private static final String UPDATED_INTRO = "BBBBBBBBBB";

    private static final String DEFAULT_WEATHER = "AAAAAAAAAA";
    private static final String UPDATED_WEATHER = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_LAST_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_HOME_LAST_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_VISITOR_LAST_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_VISITOR_LAST_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_SCORERS = "AAAAAAAAAA";
    private static final String UPDATED_HOME_SCORERS = "BBBBBBBBBB";

    private static final String DEFAULT_VISITOR_SCORERS = "AAAAAAAAAA";
    private static final String UPDATED_VISITOR_SCORERS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MEETING_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MEETING_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MEETING_SCORING = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MEETING_SCORING = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_SIDELINED = "AAAAAAAAAA";
    private static final String UPDATED_HOME_SIDELINED = "BBBBBBBBBB";

    private static final String DEFAULT_VISITOR_SIDELINED = "AAAAAAAAAA";
    private static final String UPDATED_VISITOR_SIDELINED = "BBBBBBBBBB";

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private PartsService partsService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.PartsSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartsSearchRepository mockPartsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartsMockMvc;

    private Parts parts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parts createEntity(EntityManager em) {
        Parts parts = new Parts()
            .intro(DEFAULT_INTRO)
            .weather(DEFAULT_WEATHER)
            .homeLastResult(DEFAULT_HOME_LAST_RESULT)
            .visitorLastResult(DEFAULT_VISITOR_LAST_RESULT)
            .homeScorers(DEFAULT_HOME_SCORERS)
            .visitorScorers(DEFAULT_VISITOR_SCORERS)
            .lastMeetingResult(DEFAULT_LAST_MEETING_RESULT)
            .lastMeetingScoring(DEFAULT_LAST_MEETING_SCORING)
            .homeSidelined(DEFAULT_HOME_SIDELINED)
            .visitorSidelined(DEFAULT_VISITOR_SIDELINED);
        return parts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parts createUpdatedEntity(EntityManager em) {
        Parts parts = new Parts()
            .intro(UPDATED_INTRO)
            .weather(UPDATED_WEATHER)
            .homeLastResult(UPDATED_HOME_LAST_RESULT)
            .visitorLastResult(UPDATED_VISITOR_LAST_RESULT)
            .homeScorers(UPDATED_HOME_SCORERS)
            .visitorScorers(UPDATED_VISITOR_SCORERS)
            .lastMeetingResult(UPDATED_LAST_MEETING_RESULT)
            .lastMeetingScoring(UPDATED_LAST_MEETING_SCORING)
            .homeSidelined(UPDATED_HOME_SIDELINED)
            .visitorSidelined(UPDATED_VISITOR_SIDELINED);
        return parts;
    }

    @BeforeEach
    public void initTest() {
        parts = createEntity(em);
    }

    @Test
    @Transactional
    public void createParts() throws Exception {
        int databaseSizeBeforeCreate = partsRepository.findAll().size();
        // Create the Parts
        restPartsMockMvc.perform(post("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parts)))
            .andExpect(status().isCreated());

        // Validate the Parts in the database
        List<Parts> partsList = partsRepository.findAll();
        assertThat(partsList).hasSize(databaseSizeBeforeCreate + 1);
        Parts testParts = partsList.get(partsList.size() - 1);
        assertThat(testParts.getIntro()).isEqualTo(DEFAULT_INTRO);
        assertThat(testParts.getWeather()).isEqualTo(DEFAULT_WEATHER);
        assertThat(testParts.getHomeLastResult()).isEqualTo(DEFAULT_HOME_LAST_RESULT);
        assertThat(testParts.getVisitorLastResult()).isEqualTo(DEFAULT_VISITOR_LAST_RESULT);
        assertThat(testParts.getHomeScorers()).isEqualTo(DEFAULT_HOME_SCORERS);
        assertThat(testParts.getVisitorScorers()).isEqualTo(DEFAULT_VISITOR_SCORERS);
        assertThat(testParts.getLastMeetingResult()).isEqualTo(DEFAULT_LAST_MEETING_RESULT);
        assertThat(testParts.getLastMeetingScoring()).isEqualTo(DEFAULT_LAST_MEETING_SCORING);
        assertThat(testParts.getHomeSidelined()).isEqualTo(DEFAULT_HOME_SIDELINED);
        assertThat(testParts.getVisitorSidelined()).isEqualTo(DEFAULT_VISITOR_SIDELINED);

        // Validate the Parts in Elasticsearch
        verify(mockPartsSearchRepository, times(1)).save(testParts);
    }

    @Test
    @Transactional
    public void createPartsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partsRepository.findAll().size();

        // Create the Parts with an existing ID
        parts.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartsMockMvc.perform(post("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parts)))
            .andExpect(status().isBadRequest());

        // Validate the Parts in the database
        List<Parts> partsList = partsRepository.findAll();
        assertThat(partsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Parts in Elasticsearch
        verify(mockPartsSearchRepository, times(0)).save(parts);
    }


    @Test
    @Transactional
    public void getAllParts() throws Exception {
        // Initialize the database
        partsRepository.saveAndFlush(parts);

        // Get all the partsList
        restPartsMockMvc.perform(get("/api/parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parts.getId().intValue())))
            .andExpect(jsonPath("$.[*].intro").value(hasItem(DEFAULT_INTRO)))
            .andExpect(jsonPath("$.[*].weather").value(hasItem(DEFAULT_WEATHER)))
            .andExpect(jsonPath("$.[*].homeLastResult").value(hasItem(DEFAULT_HOME_LAST_RESULT)))
            .andExpect(jsonPath("$.[*].visitorLastResult").value(hasItem(DEFAULT_VISITOR_LAST_RESULT)))
            .andExpect(jsonPath("$.[*].homeScorers").value(hasItem(DEFAULT_HOME_SCORERS)))
            .andExpect(jsonPath("$.[*].visitorScorers").value(hasItem(DEFAULT_VISITOR_SCORERS)))
            .andExpect(jsonPath("$.[*].lastMeetingResult").value(hasItem(DEFAULT_LAST_MEETING_RESULT)))
            .andExpect(jsonPath("$.[*].lastMeetingScoring").value(hasItem(DEFAULT_LAST_MEETING_SCORING)))
            .andExpect(jsonPath("$.[*].homeSidelined").value(hasItem(DEFAULT_HOME_SIDELINED)))
            .andExpect(jsonPath("$.[*].visitorSidelined").value(hasItem(DEFAULT_VISITOR_SIDELINED)));
    }
    
    @Test
    @Transactional
    public void getParts() throws Exception {
        // Initialize the database
        partsRepository.saveAndFlush(parts);

        // Get the parts
        restPartsMockMvc.perform(get("/api/parts/{id}", parts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parts.getId().intValue()))
            .andExpect(jsonPath("$.intro").value(DEFAULT_INTRO))
            .andExpect(jsonPath("$.weather").value(DEFAULT_WEATHER))
            .andExpect(jsonPath("$.homeLastResult").value(DEFAULT_HOME_LAST_RESULT))
            .andExpect(jsonPath("$.visitorLastResult").value(DEFAULT_VISITOR_LAST_RESULT))
            .andExpect(jsonPath("$.homeScorers").value(DEFAULT_HOME_SCORERS))
            .andExpect(jsonPath("$.visitorScorers").value(DEFAULT_VISITOR_SCORERS))
            .andExpect(jsonPath("$.lastMeetingResult").value(DEFAULT_LAST_MEETING_RESULT))
            .andExpect(jsonPath("$.lastMeetingScoring").value(DEFAULT_LAST_MEETING_SCORING))
            .andExpect(jsonPath("$.homeSidelined").value(DEFAULT_HOME_SIDELINED))
            .andExpect(jsonPath("$.visitorSidelined").value(DEFAULT_VISITOR_SIDELINED));
    }
    @Test
    @Transactional
    public void getNonExistingParts() throws Exception {
        // Get the parts
        restPartsMockMvc.perform(get("/api/parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParts() throws Exception {
        // Initialize the database
        partsService.save(parts);

        int databaseSizeBeforeUpdate = partsRepository.findAll().size();

        // Update the parts
        Parts updatedParts = partsRepository.findById(parts.getId()).get();
        // Disconnect from session so that the updates on updatedParts are not directly saved in db
        em.detach(updatedParts);
        updatedParts
            .intro(UPDATED_INTRO)
            .weather(UPDATED_WEATHER)
            .homeLastResult(UPDATED_HOME_LAST_RESULT)
            .visitorLastResult(UPDATED_VISITOR_LAST_RESULT)
            .homeScorers(UPDATED_HOME_SCORERS)
            .visitorScorers(UPDATED_VISITOR_SCORERS)
            .lastMeetingResult(UPDATED_LAST_MEETING_RESULT)
            .lastMeetingScoring(UPDATED_LAST_MEETING_SCORING)
            .homeSidelined(UPDATED_HOME_SIDELINED)
            .visitorSidelined(UPDATED_VISITOR_SIDELINED);

        restPartsMockMvc.perform(put("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParts)))
            .andExpect(status().isOk());

        // Validate the Parts in the database
        List<Parts> partsList = partsRepository.findAll();
        assertThat(partsList).hasSize(databaseSizeBeforeUpdate);
        Parts testParts = partsList.get(partsList.size() - 1);
        assertThat(testParts.getIntro()).isEqualTo(UPDATED_INTRO);
        assertThat(testParts.getWeather()).isEqualTo(UPDATED_WEATHER);
        assertThat(testParts.getHomeLastResult()).isEqualTo(UPDATED_HOME_LAST_RESULT);
        assertThat(testParts.getVisitorLastResult()).isEqualTo(UPDATED_VISITOR_LAST_RESULT);
        assertThat(testParts.getHomeScorers()).isEqualTo(UPDATED_HOME_SCORERS);
        assertThat(testParts.getVisitorScorers()).isEqualTo(UPDATED_VISITOR_SCORERS);
        assertThat(testParts.getLastMeetingResult()).isEqualTo(UPDATED_LAST_MEETING_RESULT);
        assertThat(testParts.getLastMeetingScoring()).isEqualTo(UPDATED_LAST_MEETING_SCORING);
        assertThat(testParts.getHomeSidelined()).isEqualTo(UPDATED_HOME_SIDELINED);
        assertThat(testParts.getVisitorSidelined()).isEqualTo(UPDATED_VISITOR_SIDELINED);

        // Validate the Parts in Elasticsearch
        verify(mockPartsSearchRepository, times(2)).save(testParts);
    }

    @Test
    @Transactional
    public void updateNonExistingParts() throws Exception {
        int databaseSizeBeforeUpdate = partsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartsMockMvc.perform(put("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parts)))
            .andExpect(status().isBadRequest());

        // Validate the Parts in the database
        List<Parts> partsList = partsRepository.findAll();
        assertThat(partsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Parts in Elasticsearch
        verify(mockPartsSearchRepository, times(0)).save(parts);
    }

    @Test
    @Transactional
    public void deleteParts() throws Exception {
        // Initialize the database
        partsService.save(parts);

        int databaseSizeBeforeDelete = partsRepository.findAll().size();

        // Delete the parts
        restPartsMockMvc.perform(delete("/api/parts/{id}", parts.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parts> partsList = partsRepository.findAll();
        assertThat(partsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Parts in Elasticsearch
        verify(mockPartsSearchRepository, times(1)).deleteById(parts.getId());
    }

    @Test
    @Transactional
    public void searchParts() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        partsService.save(parts);
        when(mockPartsSearchRepository.search(queryStringQuery("id:" + parts.getId())))
            .thenReturn(Collections.singletonList(parts));

        // Search the parts
        restPartsMockMvc.perform(get("/api/_search/parts?query=id:" + parts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parts.getId().intValue())))
            .andExpect(jsonPath("$.[*].intro").value(hasItem(DEFAULT_INTRO)))
            .andExpect(jsonPath("$.[*].weather").value(hasItem(DEFAULT_WEATHER)))
            .andExpect(jsonPath("$.[*].homeLastResult").value(hasItem(DEFAULT_HOME_LAST_RESULT)))
            .andExpect(jsonPath("$.[*].visitorLastResult").value(hasItem(DEFAULT_VISITOR_LAST_RESULT)))
            .andExpect(jsonPath("$.[*].homeScorers").value(hasItem(DEFAULT_HOME_SCORERS)))
            .andExpect(jsonPath("$.[*].visitorScorers").value(hasItem(DEFAULT_VISITOR_SCORERS)))
            .andExpect(jsonPath("$.[*].lastMeetingResult").value(hasItem(DEFAULT_LAST_MEETING_RESULT)))
            .andExpect(jsonPath("$.[*].lastMeetingScoring").value(hasItem(DEFAULT_LAST_MEETING_SCORING)))
            .andExpect(jsonPath("$.[*].homeSidelined").value(hasItem(DEFAULT_HOME_SIDELINED)))
            .andExpect(jsonPath("$.[*].visitorSidelined").value(hasItem(DEFAULT_VISITOR_SIDELINED)));
    }
}

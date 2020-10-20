package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.repository.SportRepository;
import com.betpreview.betmanage.repository.search.SportSearchRepository;
import com.betpreview.betmanage.service.SportService;

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
 * Integration tests for the {@link SportResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SportResourceIT {

    private static final String DEFAULT_SPORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SPORT_NAME = "BBBBBBBBBB";

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private SportService sportService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.SportSearchRepositoryMockConfiguration
     */
    @Autowired
    private SportSearchRepository mockSportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportMockMvc;

    private Sport sport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sport createEntity(EntityManager em) {
        Sport sport = new Sport()
            .sportName(DEFAULT_SPORT_NAME);
        return sport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sport createUpdatedEntity(EntityManager em) {
        Sport sport = new Sport()
            .sportName(UPDATED_SPORT_NAME);
        return sport;
    }

    @BeforeEach
    public void initTest() {
        sport = createEntity(em);
    }

    @Test
    @Transactional
    public void createSport() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();
        // Create the Sport
        restSportMockMvc.perform(post("/api/sports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sport)))
            .andExpect(status().isCreated());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate + 1);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getSportName()).isEqualTo(DEFAULT_SPORT_NAME);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(1)).save(testSport);
    }

    @Test
    @Transactional
    public void createSportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();

        // Create the Sport with an existing ID
        sport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportMockMvc.perform(post("/api/sports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sport)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(0)).save(sport);
    }


    @Test
    @Transactional
    public void checkSportNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportRepository.findAll().size();
        // set the field null
        sport.setSportName(null);

        // Create the Sport, which fails.


        restSportMockMvc.perform(post("/api/sports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sport)))
            .andExpect(status().isBadRequest());

        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSports() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        // Get all the sportList
        restSportMockMvc.perform(get("/api/sports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sport.getId().intValue())))
            .andExpect(jsonPath("$.[*].sportName").value(hasItem(DEFAULT_SPORT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        // Get the sport
        restSportMockMvc.perform(get("/api/sports/{id}", sport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sport.getId().intValue()))
            .andExpect(jsonPath("$.sportName").value(DEFAULT_SPORT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingSport() throws Exception {
        // Get the sport
        restSportMockMvc.perform(get("/api/sports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSport() throws Exception {
        // Initialize the database
        sportService.save(sport);

        int databaseSizeBeforeUpdate = sportRepository.findAll().size();

        // Update the sport
        Sport updatedSport = sportRepository.findById(sport.getId()).get();
        // Disconnect from session so that the updates on updatedSport are not directly saved in db
        em.detach(updatedSport);
        updatedSport
            .sportName(UPDATED_SPORT_NAME);

        restSportMockMvc.perform(put("/api/sports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSport)))
            .andExpect(status().isOk());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getSportName()).isEqualTo(UPDATED_SPORT_NAME);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(2)).save(testSport);
    }

    @Test
    @Transactional
    public void updateNonExistingSport() throws Exception {
        int databaseSizeBeforeUpdate = sportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportMockMvc.perform(put("/api/sports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sport)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(0)).save(sport);
    }

    @Test
    @Transactional
    public void deleteSport() throws Exception {
        // Initialize the database
        sportService.save(sport);

        int databaseSizeBeforeDelete = sportRepository.findAll().size();

        // Delete the sport
        restSportMockMvc.perform(delete("/api/sports/{id}", sport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(1)).deleteById(sport.getId());
    }

    @Test
    @Transactional
    public void searchSport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sportService.save(sport);
        when(mockSportSearchRepository.search(queryStringQuery("id:" + sport.getId())))
            .thenReturn(Collections.singletonList(sport));

        // Search the sport
        restSportMockMvc.perform(get("/api/_search/sports?query=id:" + sport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sport.getId().intValue())))
            .andExpect(jsonPath("$.[*].sportName").value(hasItem(DEFAULT_SPORT_NAME)));
    }
}

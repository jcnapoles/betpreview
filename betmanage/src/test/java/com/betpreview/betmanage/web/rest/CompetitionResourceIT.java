package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.repository.CompetitionRepository;
import com.betpreview.betmanage.repository.search.CompetitionSearchRepository;
import com.betpreview.betmanage.service.CompetitionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.betpreview.betmanage.domain.enumeration.TypeCompetition;
/**
 * Integration tests for the {@link CompetitionResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetitionResourceIT {

    private static final String DEFAULT_COMPETITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITION_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPETITION_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPETITION_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMPETITION_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPETITION_LOGO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_CUP = false;
    private static final Boolean UPDATED_IS_CUP = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final TypeCompetition DEFAULT_TYPE = TypeCompetition.DOMESTIC;
    private static final TypeCompetition UPDATED_TYPE = TypeCompetition.DOMESTIC_CUP;

    private static final Integer DEFAULT_SPORTSCRIBE_ID = 1;
    private static final Integer UPDATED_SPORTSCRIBE_ID = 2;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Mock
    private CompetitionRepository competitionRepositoryMock;

    @Mock
    private CompetitionService competitionServiceMock;

    @Autowired
    private CompetitionService competitionService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.CompetitionSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionSearchRepository mockCompetitionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitionMockMvc;

    private Competition competition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createEntity(EntityManager em) {
        Competition competition = new Competition()
            .competitionName(DEFAULT_COMPETITION_NAME)
            .competitionLogo(DEFAULT_COMPETITION_LOGO)
            .competitionLogoContentType(DEFAULT_COMPETITION_LOGO_CONTENT_TYPE)
            .isCup(DEFAULT_IS_CUP)
            .active(DEFAULT_ACTIVE)
            .type(DEFAULT_TYPE)
            .sportscribeId(DEFAULT_SPORTSCRIBE_ID);
        return competition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createUpdatedEntity(EntityManager em) {
        Competition competition = new Competition()
            .competitionName(UPDATED_COMPETITION_NAME)
            .competitionLogo(UPDATED_COMPETITION_LOGO)
            .competitionLogoContentType(UPDATED_COMPETITION_LOGO_CONTENT_TYPE)
            .isCup(UPDATED_IS_CUP)
            .active(UPDATED_ACTIVE)
            .type(UPDATED_TYPE)
            .sportscribeId(UPDATED_SPORTSCRIBE_ID);
        return competition;
    }

    @BeforeEach
    public void initTest() {
        competition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetition() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();
        // Create the Competition
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isCreated());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate + 1);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getCompetitionName()).isEqualTo(DEFAULT_COMPETITION_NAME);
        assertThat(testCompetition.getCompetitionLogo()).isEqualTo(DEFAULT_COMPETITION_LOGO);
        assertThat(testCompetition.getCompetitionLogoContentType()).isEqualTo(DEFAULT_COMPETITION_LOGO_CONTENT_TYPE);
        assertThat(testCompetition.isIsCup()).isEqualTo(DEFAULT_IS_CUP);
        assertThat(testCompetition.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCompetition.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCompetition.getSportscribeId()).isEqualTo(DEFAULT_SPORTSCRIBE_ID);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(1)).save(testCompetition);
    }

    @Test
    @Transactional
    public void createCompetitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();

        // Create the Competition with an existing ID
        competition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(0)).save(competition);
    }


    @Test
    @Transactional
    public void checkCompetitionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionRepository.findAll().size();
        // set the field null
        competition.setCompetitionName(null);

        // Create the Competition, which fails.


        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isBadRequest());

        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitions() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList
        restCompetitionMockMvc.perform(get("/api/competitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionName").value(hasItem(DEFAULT_COMPETITION_NAME)))
            .andExpect(jsonPath("$.[*].competitionLogoContentType").value(hasItem(DEFAULT_COMPETITION_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].competitionLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPETITION_LOGO))))
            .andExpect(jsonPath("$.[*].isCup").value(hasItem(DEFAULT_IS_CUP.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sportscribeId").value(hasItem(DEFAULT_SPORTSCRIBE_ID)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCompetitionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(competitionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetitionMockMvc.perform(get("/api/competitions?eagerload=true"))
            .andExpect(status().isOk());

        verify(competitionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCompetitionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(competitionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetitionMockMvc.perform(get("/api/competitions?eagerload=true"))
            .andExpect(status().isOk());

        verify(competitionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competition.getId().intValue()))
            .andExpect(jsonPath("$.competitionName").value(DEFAULT_COMPETITION_NAME))
            .andExpect(jsonPath("$.competitionLogoContentType").value(DEFAULT_COMPETITION_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.competitionLogo").value(Base64Utils.encodeToString(DEFAULT_COMPETITION_LOGO)))
            .andExpect(jsonPath("$.isCup").value(DEFAULT_IS_CUP.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sportscribeId").value(DEFAULT_SPORTSCRIBE_ID));
    }
    @Test
    @Transactional
    public void getNonExistingCompetition() throws Exception {
        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetition() throws Exception {
        // Initialize the database
        competitionService.save(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition
        Competition updatedCompetition = competitionRepository.findById(competition.getId()).get();
        // Disconnect from session so that the updates on updatedCompetition are not directly saved in db
        em.detach(updatedCompetition);
        updatedCompetition
            .competitionName(UPDATED_COMPETITION_NAME)
            .competitionLogo(UPDATED_COMPETITION_LOGO)
            .competitionLogoContentType(UPDATED_COMPETITION_LOGO_CONTENT_TYPE)
            .isCup(UPDATED_IS_CUP)
            .active(UPDATED_ACTIVE)
            .type(UPDATED_TYPE)
            .sportscribeId(UPDATED_SPORTSCRIBE_ID);

        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetition)))
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getCompetitionName()).isEqualTo(UPDATED_COMPETITION_NAME);
        assertThat(testCompetition.getCompetitionLogo()).isEqualTo(UPDATED_COMPETITION_LOGO);
        assertThat(testCompetition.getCompetitionLogoContentType()).isEqualTo(UPDATED_COMPETITION_LOGO_CONTENT_TYPE);
        assertThat(testCompetition.isIsCup()).isEqualTo(UPDATED_IS_CUP);
        assertThat(testCompetition.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCompetition.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompetition.getSportscribeId()).isEqualTo(UPDATED_SPORTSCRIBE_ID);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(2)).save(testCompetition);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competition)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(0)).save(competition);
    }

    @Test
    @Transactional
    public void deleteCompetition() throws Exception {
        // Initialize the database
        competitionService.save(competition);

        int databaseSizeBeforeDelete = competitionRepository.findAll().size();

        // Delete the competition
        restCompetitionMockMvc.perform(delete("/api/competitions/{id}", competition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(1)).deleteById(competition.getId());
    }

    @Test
    @Transactional
    public void searchCompetition() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        competitionService.save(competition);
        when(mockCompetitionSearchRepository.search(queryStringQuery("id:" + competition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competition), PageRequest.of(0, 1), 1));

        // Search the competition
        restCompetitionMockMvc.perform(get("/api/_search/competitions?query=id:" + competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionName").value(hasItem(DEFAULT_COMPETITION_NAME)))
            .andExpect(jsonPath("$.[*].competitionLogoContentType").value(hasItem(DEFAULT_COMPETITION_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].competitionLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPETITION_LOGO))))
            .andExpect(jsonPath("$.[*].isCup").value(hasItem(DEFAULT_IS_CUP.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sportscribeId").value(hasItem(DEFAULT_SPORTSCRIBE_ID)));
    }
}

package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.repository.TeamRepository;
import com.betpreview.betmanage.repository.search.TeamSearchRepository;
import com.betpreview.betmanage.service.TeamService;

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

/**
 * Integration tests for the {@link TeamResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeamResourceIT {

    private static final String DEFAULT_TEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_NATIONAL_TEAM = false;
    private static final Boolean UPDATED_IS_NATIONAL_TEAM = true;

    private static final byte[] DEFAULT_TEAM_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEAM_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TEAM_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEAM_LOGO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TEAM_ID = 1;
    private static final Integer UPDATED_TEAM_ID = 2;

    @Autowired
    private TeamRepository teamRepository;

    @Mock
    private TeamRepository teamRepositoryMock;

    @Mock
    private TeamService teamServiceMock;

    @Autowired
    private TeamService teamService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.TeamSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeamSearchRepository mockTeamSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMockMvc;

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .teamName(DEFAULT_TEAM_NAME)
            .shortCode(DEFAULT_SHORT_CODE)
            .isNationalTeam(DEFAULT_IS_NATIONAL_TEAM)
            .teamLogo(DEFAULT_TEAM_LOGO)
            .teamLogoContentType(DEFAULT_TEAM_LOGO_CONTENT_TYPE)
            .teamId(DEFAULT_TEAM_ID);
        return team;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity(EntityManager em) {
        Team team = new Team()
            .teamName(UPDATED_TEAM_NAME)
            .shortCode(UPDATED_SHORT_CODE)
            .isNationalTeam(UPDATED_IS_NATIONAL_TEAM)
            .teamLogo(UPDATED_TEAM_LOGO)
            .teamLogoContentType(UPDATED_TEAM_LOGO_CONTENT_TYPE)
            .teamId(UPDATED_TEAM_ID);
        return team;
    }

    @BeforeEach
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();
        // Create the Team
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamName()).isEqualTo(DEFAULT_TEAM_NAME);
        assertThat(testTeam.getShortCode()).isEqualTo(DEFAULT_SHORT_CODE);
        assertThat(testTeam.isIsNationalTeam()).isEqualTo(DEFAULT_IS_NATIONAL_TEAM);
        assertThat(testTeam.getTeamLogo()).isEqualTo(DEFAULT_TEAM_LOGO);
        assertThat(testTeam.getTeamLogoContentType()).isEqualTo(DEFAULT_TEAM_LOGO_CONTENT_TYPE);
        assertThat(testTeam.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);

        // Validate the Team in Elasticsearch
        verify(mockTeamSearchRepository, times(1)).save(testTeam);
    }

    @Test
    @Transactional
    public void createTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team with an existing ID
        team.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);

        // Validate the Team in Elasticsearch
        verify(mockTeamSearchRepository, times(0)).save(team);
    }


    @Test
    @Transactional
    public void checkTeamNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setTeamName(null);

        // Create the Team, which fails.


        restTeamMockMvc.perform(post("/api/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc.perform(get("/api/teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE)))
            .andExpect(jsonPath("$.[*].isNationalTeam").value(hasItem(DEFAULT_IS_NATIONAL_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].teamLogoContentType").value(hasItem(DEFAULT_TEAM_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].teamLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEAM_LOGO))))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTeamsWithEagerRelationshipsIsEnabled() throws Exception {
        when(teamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamMockMvc.perform(get("/api/teams?eagerload=true"))
            .andExpect(status().isOk());

        verify(teamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTeamsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(teamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamMockMvc.perform(get("/api/teams?eagerload=true"))
            .andExpect(status().isOk());

        verify(teamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.teamName").value(DEFAULT_TEAM_NAME))
            .andExpect(jsonPath("$.shortCode").value(DEFAULT_SHORT_CODE))
            .andExpect(jsonPath("$.isNationalTeam").value(DEFAULT_IS_NATIONAL_TEAM.booleanValue()))
            .andExpect(jsonPath("$.teamLogoContentType").value(DEFAULT_TEAM_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.teamLogo").value(Base64Utils.encodeToString(DEFAULT_TEAM_LOGO)))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID));
    }
    @Test
    @Transactional
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeam() throws Exception {
        // Initialize the database
        teamService.save(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam
            .teamName(UPDATED_TEAM_NAME)
            .shortCode(UPDATED_SHORT_CODE)
            .isNationalTeam(UPDATED_IS_NATIONAL_TEAM)
            .teamLogo(UPDATED_TEAM_LOGO)
            .teamLogoContentType(UPDATED_TEAM_LOGO_CONTENT_TYPE)
            .teamId(UPDATED_TEAM_ID);

        restTeamMockMvc.perform(put("/api/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeam)))
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamName()).isEqualTo(UPDATED_TEAM_NAME);
        assertThat(testTeam.getShortCode()).isEqualTo(UPDATED_SHORT_CODE);
        assertThat(testTeam.isIsNationalTeam()).isEqualTo(UPDATED_IS_NATIONAL_TEAM);
        assertThat(testTeam.getTeamLogo()).isEqualTo(UPDATED_TEAM_LOGO);
        assertThat(testTeam.getTeamLogoContentType()).isEqualTo(UPDATED_TEAM_LOGO_CONTENT_TYPE);
        assertThat(testTeam.getTeamId()).isEqualTo(UPDATED_TEAM_ID);

        // Validate the Team in Elasticsearch
        verify(mockTeamSearchRepository, times(2)).save(testTeam);
    }

    @Test
    @Transactional
    public void updateNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc.perform(put("/api/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Team in Elasticsearch
        verify(mockTeamSearchRepository, times(0)).save(team);
    }

    @Test
    @Transactional
    public void deleteTeam() throws Exception {
        // Initialize the database
        teamService.save(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", team.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Team in Elasticsearch
        verify(mockTeamSearchRepository, times(1)).deleteById(team.getId());
    }

    @Test
    @Transactional
    public void searchTeam() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        teamService.save(team);
        when(mockTeamSearchRepository.search(queryStringQuery("id:" + team.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(team), PageRequest.of(0, 1), 1));

        // Search the team
        restTeamMockMvc.perform(get("/api/_search/teams?query=id:" + team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE)))
            .andExpect(jsonPath("$.[*].isNationalTeam").value(hasItem(DEFAULT_IS_NATIONAL_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].teamLogoContentType").value(hasItem(DEFAULT_TEAM_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].teamLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEAM_LOGO))))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID)));
    }
}

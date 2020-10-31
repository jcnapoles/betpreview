package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.TeamSocial;
import com.betpreview.betmanage.repository.TeamSocialRepository;
import com.betpreview.betmanage.repository.search.TeamSocialSearchRepository;
import com.betpreview.betmanage.service.TeamSocialService;

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
 * Integration tests for the {@link TeamSocialResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeamSocialResourceIT {

    private static final Integer DEFAULT_HOME = 1;
    private static final Integer UPDATED_HOME = 2;

    private static final Integer DEFAULT_VISITOR = 1;
    private static final Integer UPDATED_VISITOR = 2;

    private static final String DEFAULT_MATCH = "AAAAAAAAAA";
    private static final String UPDATED_MATCH = "BBBBBBBBBB";

    @Autowired
    private TeamSocialRepository teamSocialRepository;

    @Autowired
    private TeamSocialService teamSocialService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.TeamSocialSearchRepositoryMockConfiguration
     */
    @Autowired
    private TeamSocialSearchRepository mockTeamSocialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamSocialMockMvc;

    private TeamSocial teamSocial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamSocial createEntity(EntityManager em) {
        TeamSocial teamSocial = new TeamSocial()
            .home(DEFAULT_HOME)
            .visitor(DEFAULT_VISITOR)
            .match(DEFAULT_MATCH);
        return teamSocial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamSocial createUpdatedEntity(EntityManager em) {
        TeamSocial teamSocial = new TeamSocial()
            .home(UPDATED_HOME)
            .visitor(UPDATED_VISITOR)
            .match(UPDATED_MATCH);
        return teamSocial;
    }

    @BeforeEach
    public void initTest() {
        teamSocial = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamSocial() throws Exception {
        int databaseSizeBeforeCreate = teamSocialRepository.findAll().size();
        // Create the TeamSocial
        restTeamSocialMockMvc.perform(post("/api/team-socials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamSocial)))
            .andExpect(status().isCreated());

        // Validate the TeamSocial in the database
        List<TeamSocial> teamSocialList = teamSocialRepository.findAll();
        assertThat(teamSocialList).hasSize(databaseSizeBeforeCreate + 1);
        TeamSocial testTeamSocial = teamSocialList.get(teamSocialList.size() - 1);
        assertThat(testTeamSocial.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testTeamSocial.getVisitor()).isEqualTo(DEFAULT_VISITOR);
        assertThat(testTeamSocial.getMatch()).isEqualTo(DEFAULT_MATCH);

        // Validate the TeamSocial in Elasticsearch
        verify(mockTeamSocialSearchRepository, times(1)).save(testTeamSocial);
    }

    @Test
    @Transactional
    public void createTeamSocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamSocialRepository.findAll().size();

        // Create the TeamSocial with an existing ID
        teamSocial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamSocialMockMvc.perform(post("/api/team-socials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamSocial)))
            .andExpect(status().isBadRequest());

        // Validate the TeamSocial in the database
        List<TeamSocial> teamSocialList = teamSocialRepository.findAll();
        assertThat(teamSocialList).hasSize(databaseSizeBeforeCreate);

        // Validate the TeamSocial in Elasticsearch
        verify(mockTeamSocialSearchRepository, times(0)).save(teamSocial);
    }


    @Test
    @Transactional
    public void getAllTeamSocials() throws Exception {
        // Initialize the database
        teamSocialRepository.saveAndFlush(teamSocial);

        // Get all the teamSocialList
        restTeamSocialMockMvc.perform(get("/api/team-socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].visitor").value(hasItem(DEFAULT_VISITOR)))
            .andExpect(jsonPath("$.[*].match").value(hasItem(DEFAULT_MATCH)));
    }
    
    @Test
    @Transactional
    public void getTeamSocial() throws Exception {
        // Initialize the database
        teamSocialRepository.saveAndFlush(teamSocial);

        // Get the teamSocial
        restTeamSocialMockMvc.perform(get("/api/team-socials/{id}", teamSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamSocial.getId().intValue()))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME))
            .andExpect(jsonPath("$.visitor").value(DEFAULT_VISITOR))
            .andExpect(jsonPath("$.match").value(DEFAULT_MATCH));
    }
    @Test
    @Transactional
    public void getNonExistingTeamSocial() throws Exception {
        // Get the teamSocial
        restTeamSocialMockMvc.perform(get("/api/team-socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamSocial() throws Exception {
        // Initialize the database
        teamSocialService.save(teamSocial);

        int databaseSizeBeforeUpdate = teamSocialRepository.findAll().size();

        // Update the teamSocial
        TeamSocial updatedTeamSocial = teamSocialRepository.findById(teamSocial.getId()).get();
        // Disconnect from session so that the updates on updatedTeamSocial are not directly saved in db
        em.detach(updatedTeamSocial);
        updatedTeamSocial
            .home(UPDATED_HOME)
            .visitor(UPDATED_VISITOR)
            .match(UPDATED_MATCH);

        restTeamSocialMockMvc.perform(put("/api/team-socials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamSocial)))
            .andExpect(status().isOk());

        // Validate the TeamSocial in the database
        List<TeamSocial> teamSocialList = teamSocialRepository.findAll();
        assertThat(teamSocialList).hasSize(databaseSizeBeforeUpdate);
        TeamSocial testTeamSocial = teamSocialList.get(teamSocialList.size() - 1);
        assertThat(testTeamSocial.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testTeamSocial.getVisitor()).isEqualTo(UPDATED_VISITOR);
        assertThat(testTeamSocial.getMatch()).isEqualTo(UPDATED_MATCH);

        // Validate the TeamSocial in Elasticsearch
        verify(mockTeamSocialSearchRepository, times(2)).save(testTeamSocial);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamSocial() throws Exception {
        int databaseSizeBeforeUpdate = teamSocialRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamSocialMockMvc.perform(put("/api/team-socials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamSocial)))
            .andExpect(status().isBadRequest());

        // Validate the TeamSocial in the database
        List<TeamSocial> teamSocialList = teamSocialRepository.findAll();
        assertThat(teamSocialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TeamSocial in Elasticsearch
        verify(mockTeamSocialSearchRepository, times(0)).save(teamSocial);
    }

    @Test
    @Transactional
    public void deleteTeamSocial() throws Exception {
        // Initialize the database
        teamSocialService.save(teamSocial);

        int databaseSizeBeforeDelete = teamSocialRepository.findAll().size();

        // Delete the teamSocial
        restTeamSocialMockMvc.perform(delete("/api/team-socials/{id}", teamSocial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamSocial> teamSocialList = teamSocialRepository.findAll();
        assertThat(teamSocialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TeamSocial in Elasticsearch
        verify(mockTeamSocialSearchRepository, times(1)).deleteById(teamSocial.getId());
    }

    @Test
    @Transactional
    public void searchTeamSocial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        teamSocialService.save(teamSocial);
        when(mockTeamSocialSearchRepository.search(queryStringQuery("id:" + teamSocial.getId())))
            .thenReturn(Collections.singletonList(teamSocial));

        // Search the teamSocial
        restTeamSocialMockMvc.perform(get("/api/_search/team-socials?query=id:" + teamSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].visitor").value(hasItem(DEFAULT_VISITOR)))
            .andExpect(jsonPath("$.[*].match").value(hasItem(DEFAULT_MATCH)));
    }
}

package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Title;
import com.betpreview.betmanage.repository.TitleRepository;
import com.betpreview.betmanage.repository.search.TitleSearchRepository;
import com.betpreview.betmanage.service.TitleService;

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
 * Integration tests for the {@link TitleResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TitleResourceIT {

    private static final String DEFAULT_TITLE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_TEXT = "BBBBBBBBBB";

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private TitleService titleService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.TitleSearchRepositoryMockConfiguration
     */
    @Autowired
    private TitleSearchRepository mockTitleSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTitleMockMvc;

    private Title title;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Title createEntity(EntityManager em) {
        Title title = new Title()
            .titleText(DEFAULT_TITLE_TEXT);
        return title;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Title createUpdatedEntity(EntityManager em) {
        Title title = new Title()
            .titleText(UPDATED_TITLE_TEXT);
        return title;
    }

    @BeforeEach
    public void initTest() {
        title = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitle() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();
        // Create the Title
        restTitleMockMvc.perform(post("/api/titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(title)))
            .andExpect(status().isCreated());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeCreate + 1);
        Title testTitle = titleList.get(titleList.size() - 1);
        assertThat(testTitle.getTitleText()).isEqualTo(DEFAULT_TITLE_TEXT);

        // Validate the Title in Elasticsearch
        verify(mockTitleSearchRepository, times(1)).save(testTitle);
    }

    @Test
    @Transactional
    public void createTitleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();

        // Create the Title with an existing ID
        title.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTitleMockMvc.perform(post("/api/titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(title)))
            .andExpect(status().isBadRequest());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Title in Elasticsearch
        verify(mockTitleSearchRepository, times(0)).save(title);
    }


    @Test
    @Transactional
    public void getAllTitles() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get all the titleList
        restTitleMockMvc.perform(get("/api/titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleText").value(hasItem(DEFAULT_TITLE_TEXT)));
    }
    
    @Test
    @Transactional
    public void getTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(title.getId().intValue()))
            .andExpect(jsonPath("$.titleText").value(DEFAULT_TITLE_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingTitle() throws Exception {
        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitle() throws Exception {
        // Initialize the database
        titleService.save(title);

        int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // Update the title
        Title updatedTitle = titleRepository.findById(title.getId()).get();
        // Disconnect from session so that the updates on updatedTitle are not directly saved in db
        em.detach(updatedTitle);
        updatedTitle
            .titleText(UPDATED_TITLE_TEXT);

        restTitleMockMvc.perform(put("/api/titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTitle)))
            .andExpect(status().isOk());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeUpdate);
        Title testTitle = titleList.get(titleList.size() - 1);
        assertThat(testTitle.getTitleText()).isEqualTo(UPDATED_TITLE_TEXT);

        // Validate the Title in Elasticsearch
        verify(mockTitleSearchRepository, times(2)).save(testTitle);
    }

    @Test
    @Transactional
    public void updateNonExistingTitle() throws Exception {
        int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTitleMockMvc.perform(put("/api/titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(title)))
            .andExpect(status().isBadRequest());

        // Validate the Title in the database
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Title in Elasticsearch
        verify(mockTitleSearchRepository, times(0)).save(title);
    }

    @Test
    @Transactional
    public void deleteTitle() throws Exception {
        // Initialize the database
        titleService.save(title);

        int databaseSizeBeforeDelete = titleRepository.findAll().size();

        // Delete the title
        restTitleMockMvc.perform(delete("/api/titles/{id}", title.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Title> titleList = titleRepository.findAll();
        assertThat(titleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Title in Elasticsearch
        verify(mockTitleSearchRepository, times(1)).deleteById(title.getId());
    }

    @Test
    @Transactional
    public void searchTitle() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        titleService.save(title);
        when(mockTitleSearchRepository.search(queryStringQuery("id:" + title.getId())))
            .thenReturn(Collections.singletonList(title));

        // Search the title
        restTitleMockMvc.perform(get("/api/_search/titles?query=id:" + title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleText").value(hasItem(DEFAULT_TITLE_TEXT)));
    }
}

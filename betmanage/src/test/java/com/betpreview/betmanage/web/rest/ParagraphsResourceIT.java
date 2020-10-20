package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.Paragraphs;
import com.betpreview.betmanage.repository.ParagraphsRepository;
import com.betpreview.betmanage.repository.search.ParagraphsSearchRepository;
import com.betpreview.betmanage.service.ParagraphsService;

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
 * Integration tests for the {@link ParagraphsResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParagraphsResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ParagraphsRepository paragraphsRepository;

    @Autowired
    private ParagraphsService paragraphsService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.ParagraphsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParagraphsSearchRepository mockParagraphsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParagraphsMockMvc;

    private Paragraphs paragraphs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paragraphs createEntity(EntityManager em) {
        Paragraphs paragraphs = new Paragraphs()
            .content(DEFAULT_CONTENT);
        return paragraphs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paragraphs createUpdatedEntity(EntityManager em) {
        Paragraphs paragraphs = new Paragraphs()
            .content(UPDATED_CONTENT);
        return paragraphs;
    }

    @BeforeEach
    public void initTest() {
        paragraphs = createEntity(em);
    }

    @Test
    @Transactional
    public void createParagraphs() throws Exception {
        int databaseSizeBeforeCreate = paragraphsRepository.findAll().size();
        // Create the Paragraphs
        restParagraphsMockMvc.perform(post("/api/paragraphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paragraphs)))
            .andExpect(status().isCreated());

        // Validate the Paragraphs in the database
        List<Paragraphs> paragraphsList = paragraphsRepository.findAll();
        assertThat(paragraphsList).hasSize(databaseSizeBeforeCreate + 1);
        Paragraphs testParagraphs = paragraphsList.get(paragraphsList.size() - 1);
        assertThat(testParagraphs.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the Paragraphs in Elasticsearch
        verify(mockParagraphsSearchRepository, times(1)).save(testParagraphs);
    }

    @Test
    @Transactional
    public void createParagraphsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paragraphsRepository.findAll().size();

        // Create the Paragraphs with an existing ID
        paragraphs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParagraphsMockMvc.perform(post("/api/paragraphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paragraphs)))
            .andExpect(status().isBadRequest());

        // Validate the Paragraphs in the database
        List<Paragraphs> paragraphsList = paragraphsRepository.findAll();
        assertThat(paragraphsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Paragraphs in Elasticsearch
        verify(mockParagraphsSearchRepository, times(0)).save(paragraphs);
    }


    @Test
    @Transactional
    public void getAllParagraphs() throws Exception {
        // Initialize the database
        paragraphsRepository.saveAndFlush(paragraphs);

        // Get all the paragraphsList
        restParagraphsMockMvc.perform(get("/api/paragraphs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paragraphs.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getParagraphs() throws Exception {
        // Initialize the database
        paragraphsRepository.saveAndFlush(paragraphs);

        // Get the paragraphs
        restParagraphsMockMvc.perform(get("/api/paragraphs/{id}", paragraphs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paragraphs.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }
    @Test
    @Transactional
    public void getNonExistingParagraphs() throws Exception {
        // Get the paragraphs
        restParagraphsMockMvc.perform(get("/api/paragraphs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParagraphs() throws Exception {
        // Initialize the database
        paragraphsService.save(paragraphs);

        int databaseSizeBeforeUpdate = paragraphsRepository.findAll().size();

        // Update the paragraphs
        Paragraphs updatedParagraphs = paragraphsRepository.findById(paragraphs.getId()).get();
        // Disconnect from session so that the updates on updatedParagraphs are not directly saved in db
        em.detach(updatedParagraphs);
        updatedParagraphs
            .content(UPDATED_CONTENT);

        restParagraphsMockMvc.perform(put("/api/paragraphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParagraphs)))
            .andExpect(status().isOk());

        // Validate the Paragraphs in the database
        List<Paragraphs> paragraphsList = paragraphsRepository.findAll();
        assertThat(paragraphsList).hasSize(databaseSizeBeforeUpdate);
        Paragraphs testParagraphs = paragraphsList.get(paragraphsList.size() - 1);
        assertThat(testParagraphs.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the Paragraphs in Elasticsearch
        verify(mockParagraphsSearchRepository, times(2)).save(testParagraphs);
    }

    @Test
    @Transactional
    public void updateNonExistingParagraphs() throws Exception {
        int databaseSizeBeforeUpdate = paragraphsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParagraphsMockMvc.perform(put("/api/paragraphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paragraphs)))
            .andExpect(status().isBadRequest());

        // Validate the Paragraphs in the database
        List<Paragraphs> paragraphsList = paragraphsRepository.findAll();
        assertThat(paragraphsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Paragraphs in Elasticsearch
        verify(mockParagraphsSearchRepository, times(0)).save(paragraphs);
    }

    @Test
    @Transactional
    public void deleteParagraphs() throws Exception {
        // Initialize the database
        paragraphsService.save(paragraphs);

        int databaseSizeBeforeDelete = paragraphsRepository.findAll().size();

        // Delete the paragraphs
        restParagraphsMockMvc.perform(delete("/api/paragraphs/{id}", paragraphs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paragraphs> paragraphsList = paragraphsRepository.findAll();
        assertThat(paragraphsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Paragraphs in Elasticsearch
        verify(mockParagraphsSearchRepository, times(1)).deleteById(paragraphs.getId());
    }

    @Test
    @Transactional
    public void searchParagraphs() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        paragraphsService.save(paragraphs);
        when(mockParagraphsSearchRepository.search(queryStringQuery("id:" + paragraphs.getId())))
            .thenReturn(Collections.singletonList(paragraphs));

        // Search the paragraphs
        restParagraphsMockMvc.perform(get("/api/_search/paragraphs?query=id:" + paragraphs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paragraphs.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}

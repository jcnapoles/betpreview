package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.SocialMedia;
import com.betpreview.betmanage.repository.SocialMediaRepository;
import com.betpreview.betmanage.repository.search.SocialMediaSearchRepository;
import com.betpreview.betmanage.service.SocialMediaService;

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

import com.betpreview.betmanage.domain.enumeration.PlatformEnum;
/**
 * Integration tests for the {@link SocialMediaResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SocialMediaResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final PlatformEnum DEFAULT_PLATFORM = PlatformEnum.TWITTER;
    private static final PlatformEnum UPDATED_PLATFORM = PlatformEnum.INSTAGRAM;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private SocialMediaService socialMediaService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.SocialMediaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SocialMediaSearchRepository mockSocialMediaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialMediaMockMvc;

    private SocialMedia socialMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMedia createEntity(EntityManager em) {
        SocialMedia socialMedia = new SocialMedia()
            .tag(DEFAULT_TAG)
            .platform(DEFAULT_PLATFORM);
        return socialMedia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMedia createUpdatedEntity(EntityManager em) {
        SocialMedia socialMedia = new SocialMedia()
            .tag(UPDATED_TAG)
            .platform(UPDATED_PLATFORM);
        return socialMedia;
    }

    @BeforeEach
    public void initTest() {
        socialMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialMedia() throws Exception {
        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();
        // Create the SocialMedia
        restSocialMediaMockMvc.perform(post("/api/social-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialMedia)))
            .andExpect(status().isCreated());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate + 1);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testSocialMedia.getPlatform()).isEqualTo(DEFAULT_PLATFORM);

        // Validate the SocialMedia in Elasticsearch
        verify(mockSocialMediaSearchRepository, times(1)).save(testSocialMedia);
    }

    @Test
    @Transactional
    public void createSocialMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();

        // Create the SocialMedia with an existing ID
        socialMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMediaMockMvc.perform(post("/api/social-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialMedia)))
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate);

        // Validate the SocialMedia in Elasticsearch
        verify(mockSocialMediaSearchRepository, times(0)).save(socialMedia);
    }


    @Test
    @Transactional
    public void getAllSocialMedias() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get all the socialMediaList
        restSocialMediaMockMvc.perform(get("/api/social-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }
    
    @Test
    @Transactional
    public void getSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get the socialMedia
        restSocialMediaMockMvc.perform(get("/api/social-medias/{id}", socialMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialMedia.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSocialMedia() throws Exception {
        // Get the socialMedia
        restSocialMediaMockMvc.perform(get("/api/social-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialMedia() throws Exception {
        // Initialize the database
        socialMediaService.save(socialMedia);

        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Update the socialMedia
        SocialMedia updatedSocialMedia = socialMediaRepository.findById(socialMedia.getId()).get();
        // Disconnect from session so that the updates on updatedSocialMedia are not directly saved in db
        em.detach(updatedSocialMedia);
        updatedSocialMedia
            .tag(UPDATED_TAG)
            .platform(UPDATED_PLATFORM);

        restSocialMediaMockMvc.perform(put("/api/social-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialMedia)))
            .andExpect(status().isOk());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testSocialMedia.getPlatform()).isEqualTo(UPDATED_PLATFORM);

        // Validate the SocialMedia in Elasticsearch
        verify(mockSocialMediaSearchRepository, times(2)).save(testSocialMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc.perform(put("/api/social-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialMedia)))
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SocialMedia in Elasticsearch
        verify(mockSocialMediaSearchRepository, times(0)).save(socialMedia);
    }

    @Test
    @Transactional
    public void deleteSocialMedia() throws Exception {
        // Initialize the database
        socialMediaService.save(socialMedia);

        int databaseSizeBeforeDelete = socialMediaRepository.findAll().size();

        // Delete the socialMedia
        restSocialMediaMockMvc.perform(delete("/api/social-medias/{id}", socialMedia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SocialMedia in Elasticsearch
        verify(mockSocialMediaSearchRepository, times(1)).deleteById(socialMedia.getId());
    }

    @Test
    @Transactional
    public void searchSocialMedia() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        socialMediaService.save(socialMedia);
        when(mockSocialMediaSearchRepository.search(queryStringQuery("id:" + socialMedia.getId())))
            .thenReturn(Collections.singletonList(socialMedia));

        // Search the socialMedia
        restSocialMediaMockMvc.perform(get("/api/_search/social-medias?query=id:" + socialMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())));
    }
}

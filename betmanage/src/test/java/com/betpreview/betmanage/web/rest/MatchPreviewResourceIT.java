package com.betpreview.betmanage.web.rest;

import com.betpreview.betmanage.BetmanageApp;
import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.repository.MatchPreviewRepository;
import com.betpreview.betmanage.repository.search.MatchPreviewSearchRepository;
import com.betpreview.betmanage.service.MatchPreviewService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.betpreview.betmanage.domain.enumeration.LanguageEnum;
/**
 * Integration tests for the {@link MatchPreviewResource} REST controller.
 */
@SpringBootTest(classes = BetmanageApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MatchPreviewResourceIT {

    private static final String DEFAULT_BLURB_FULL = "AAAAAAAAAA";
    private static final String UPDATED_BLURB_FULL = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIXTURE_ID = 1;
    private static final Integer UPDATED_FIXTURE_ID = 2;

    private static final Integer DEFAULT_HOMETEAM_ID = 1;
    private static final Integer UPDATED_HOMETEAM_ID = 2;

    private static final Integer DEFAULT_VISITORTEAM_ID = 1;
    private static final Integer UPDATED_VISITORTEAM_ID = 2;

    private static final String DEFAULT_HOMETEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOMETEAM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VISITORTEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VISITORTEAM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEAGUE_ID = 1;
    private static final Integer UPDATED_LEAGUE_ID = 2;

    private static final String DEFAULT_LEAGUE = "AAAAAAAAAA";
    private static final String UPDATED_LEAGUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FIXTURE_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIXTURE_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIXTURE_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIXTURE_IMG_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FORMATION_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FORMATION_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FORMATION_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FORMATION_IMG_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_START_UTC_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_UTC_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VENUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE_CITY = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_CITY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MATCH_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MATCH_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MATCH_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MATCH_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MATCH_IMG_TXT = "AAAAAAAAAA";
    private static final String UPDATED_MATCH_IMG_TXT = "BBBBBBBBBB";

    private static final String DEFAULT_HEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_HEADLINE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LanguageEnum DEFAULT_LANGUAGE = LanguageEnum.EN;
    private static final LanguageEnum UPDATED_LANGUAGE = LanguageEnum.ES;

    @Autowired
    private MatchPreviewRepository matchPreviewRepository;

    @Mock
    private MatchPreviewRepository matchPreviewRepositoryMock;

    @Mock
    private MatchPreviewService matchPreviewServiceMock;

    @Autowired
    private MatchPreviewService matchPreviewService;

    /**
     * This repository is mocked in the com.betpreview.betmanage.repository.search test package.
     *
     * @see com.betpreview.betmanage.repository.search.MatchPreviewSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchPreviewSearchRepository mockMatchPreviewSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchPreviewMockMvc;

    private MatchPreview matchPreview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchPreview createEntity(EntityManager em) {
        MatchPreview matchPreview = new MatchPreview()
            .blurbFull(DEFAULT_BLURB_FULL)
            .fixtureId(DEFAULT_FIXTURE_ID)
            .hometeamId(DEFAULT_HOMETEAM_ID)
            .visitorteamId(DEFAULT_VISITORTEAM_ID)
            .hometeamName(DEFAULT_HOMETEAM_NAME)
            .visitorteamName(DEFAULT_VISITORTEAM_NAME)
            .leagueId(DEFAULT_LEAGUE_ID)
            .league(DEFAULT_LEAGUE)
            .fixtureImg(DEFAULT_FIXTURE_IMG)
            .fixtureImgContentType(DEFAULT_FIXTURE_IMG_CONTENT_TYPE)
            .formationImg(DEFAULT_FORMATION_IMG)
            .formationImgContentType(DEFAULT_FORMATION_IMG_CONTENT_TYPE)
            .startUtcTimestamp(DEFAULT_START_UTC_TIMESTAMP)
            .venueName(DEFAULT_VENUE_NAME)
            .venueCity(DEFAULT_VENUE_CITY)
            .matchImg(DEFAULT_MATCH_IMG)
            .matchImgContentType(DEFAULT_MATCH_IMG_CONTENT_TYPE)
            .matchImgTxt(DEFAULT_MATCH_IMG_TXT)
            .headline(DEFAULT_HEADLINE)
            .date(DEFAULT_DATE)
            .language(DEFAULT_LANGUAGE);
        return matchPreview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchPreview createUpdatedEntity(EntityManager em) {
        MatchPreview matchPreview = new MatchPreview()
            .blurbFull(UPDATED_BLURB_FULL)
            .fixtureId(UPDATED_FIXTURE_ID)
            .hometeamId(UPDATED_HOMETEAM_ID)
            .visitorteamId(UPDATED_VISITORTEAM_ID)
            .hometeamName(UPDATED_HOMETEAM_NAME)
            .visitorteamName(UPDATED_VISITORTEAM_NAME)
            .leagueId(UPDATED_LEAGUE_ID)
            .league(UPDATED_LEAGUE)
            .fixtureImg(UPDATED_FIXTURE_IMG)
            .fixtureImgContentType(UPDATED_FIXTURE_IMG_CONTENT_TYPE)
            .formationImg(UPDATED_FORMATION_IMG)
            .formationImgContentType(UPDATED_FORMATION_IMG_CONTENT_TYPE)
            .startUtcTimestamp(UPDATED_START_UTC_TIMESTAMP)
            .venueName(UPDATED_VENUE_NAME)
            .venueCity(UPDATED_VENUE_CITY)
            .matchImg(UPDATED_MATCH_IMG)
            .matchImgContentType(UPDATED_MATCH_IMG_CONTENT_TYPE)
            .matchImgTxt(UPDATED_MATCH_IMG_TXT)
            .headline(UPDATED_HEADLINE)
            .date(UPDATED_DATE)
            .language(UPDATED_LANGUAGE);
        return matchPreview;
    }

    @BeforeEach
    public void initTest() {
        matchPreview = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchPreview() throws Exception {
        int databaseSizeBeforeCreate = matchPreviewRepository.findAll().size();
        // Create the MatchPreview
        restMatchPreviewMockMvc.perform(post("/api/match-previews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchPreview)))
            .andExpect(status().isCreated());

        // Validate the MatchPreview in the database
        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeCreate + 1);
        MatchPreview testMatchPreview = matchPreviewList.get(matchPreviewList.size() - 1);
        assertThat(testMatchPreview.getBlurbFull()).isEqualTo(DEFAULT_BLURB_FULL);
        assertThat(testMatchPreview.getFixtureId()).isEqualTo(DEFAULT_FIXTURE_ID);
        assertThat(testMatchPreview.getHometeamId()).isEqualTo(DEFAULT_HOMETEAM_ID);
        assertThat(testMatchPreview.getVisitorteamId()).isEqualTo(DEFAULT_VISITORTEAM_ID);
        assertThat(testMatchPreview.getHometeamName()).isEqualTo(DEFAULT_HOMETEAM_NAME);
        assertThat(testMatchPreview.getVisitorteamName()).isEqualTo(DEFAULT_VISITORTEAM_NAME);
        assertThat(testMatchPreview.getLeagueId()).isEqualTo(DEFAULT_LEAGUE_ID);
        assertThat(testMatchPreview.getLeague()).isEqualTo(DEFAULT_LEAGUE);
        assertThat(testMatchPreview.getFixtureImg()).isEqualTo(DEFAULT_FIXTURE_IMG);
        assertThat(testMatchPreview.getFixtureImgContentType()).isEqualTo(DEFAULT_FIXTURE_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getFormationImg()).isEqualTo(DEFAULT_FORMATION_IMG);
        assertThat(testMatchPreview.getFormationImgContentType()).isEqualTo(DEFAULT_FORMATION_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getStartUtcTimestamp()).isEqualTo(DEFAULT_START_UTC_TIMESTAMP);
        assertThat(testMatchPreview.getVenueName()).isEqualTo(DEFAULT_VENUE_NAME);
        assertThat(testMatchPreview.getVenueCity()).isEqualTo(DEFAULT_VENUE_CITY);
        assertThat(testMatchPreview.getMatchImg()).isEqualTo(DEFAULT_MATCH_IMG);
        assertThat(testMatchPreview.getMatchImgContentType()).isEqualTo(DEFAULT_MATCH_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getMatchImgTxt()).isEqualTo(DEFAULT_MATCH_IMG_TXT);
        assertThat(testMatchPreview.getHeadline()).isEqualTo(DEFAULT_HEADLINE);
        assertThat(testMatchPreview.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMatchPreview.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the MatchPreview in Elasticsearch
        verify(mockMatchPreviewSearchRepository, times(1)).save(testMatchPreview);
    }

    @Test
    @Transactional
    public void createMatchPreviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchPreviewRepository.findAll().size();

        // Create the MatchPreview with an existing ID
        matchPreview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchPreviewMockMvc.perform(post("/api/match-previews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchPreview)))
            .andExpect(status().isBadRequest());

        // Validate the MatchPreview in the database
        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchPreview in Elasticsearch
        verify(mockMatchPreviewSearchRepository, times(0)).save(matchPreview);
    }


    @Test
    @Transactional
    public void checkFixtureIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchPreviewRepository.findAll().size();
        // set the field null
        matchPreview.setFixtureId(null);

        // Create the MatchPreview, which fails.


        restMatchPreviewMockMvc.perform(post("/api/match-previews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchPreview)))
            .andExpect(status().isBadRequest());

        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatchPreviews() throws Exception {
        // Initialize the database
        matchPreviewRepository.saveAndFlush(matchPreview);

        // Get all the matchPreviewList
        restMatchPreviewMockMvc.perform(get("/api/match-previews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPreview.getId().intValue())))
            .andExpect(jsonPath("$.[*].blurbFull").value(hasItem(DEFAULT_BLURB_FULL.toString())))
            .andExpect(jsonPath("$.[*].fixtureId").value(hasItem(DEFAULT_FIXTURE_ID)))
            .andExpect(jsonPath("$.[*].hometeamId").value(hasItem(DEFAULT_HOMETEAM_ID)))
            .andExpect(jsonPath("$.[*].visitorteamId").value(hasItem(DEFAULT_VISITORTEAM_ID)))
            .andExpect(jsonPath("$.[*].hometeamName").value(hasItem(DEFAULT_HOMETEAM_NAME)))
            .andExpect(jsonPath("$.[*].visitorteamName").value(hasItem(DEFAULT_VISITORTEAM_NAME)))
            .andExpect(jsonPath("$.[*].leagueId").value(hasItem(DEFAULT_LEAGUE_ID)))
            .andExpect(jsonPath("$.[*].league").value(hasItem(DEFAULT_LEAGUE)))
            .andExpect(jsonPath("$.[*].fixtureImgContentType").value(hasItem(DEFAULT_FIXTURE_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fixtureImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIXTURE_IMG))))
            .andExpect(jsonPath("$.[*].formationImgContentType").value(hasItem(DEFAULT_FORMATION_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].formationImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_FORMATION_IMG))))
            .andExpect(jsonPath("$.[*].startUtcTimestamp").value(hasItem(DEFAULT_START_UTC_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].venueName").value(hasItem(DEFAULT_VENUE_NAME)))
            .andExpect(jsonPath("$.[*].venueCity").value(hasItem(DEFAULT_VENUE_CITY)))
            .andExpect(jsonPath("$.[*].matchImgContentType").value(hasItem(DEFAULT_MATCH_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].matchImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_MATCH_IMG))))
            .andExpect(jsonPath("$.[*].matchImaTxt").value(hasItem(DEFAULT_MATCH_IMG_TXT)))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMatchPreviewsWithEagerRelationshipsIsEnabled() throws Exception {
        when(matchPreviewServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatchPreviewMockMvc.perform(get("/api/match-previews?eagerload=true"))
            .andExpect(status().isOk());

        verify(matchPreviewServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMatchPreviewsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(matchPreviewServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatchPreviewMockMvc.perform(get("/api/match-previews?eagerload=true"))
            .andExpect(status().isOk());

        verify(matchPreviewServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMatchPreview() throws Exception {
        // Initialize the database
        matchPreviewRepository.saveAndFlush(matchPreview);

        // Get the matchPreview
        restMatchPreviewMockMvc.perform(get("/api/match-previews/{id}", matchPreview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchPreview.getId().intValue()))
            .andExpect(jsonPath("$.blurbFull").value(DEFAULT_BLURB_FULL.toString()))
            .andExpect(jsonPath("$.fixtureId").value(DEFAULT_FIXTURE_ID))
            .andExpect(jsonPath("$.hometeamId").value(DEFAULT_HOMETEAM_ID))
            .andExpect(jsonPath("$.visitorteamId").value(DEFAULT_VISITORTEAM_ID))
            .andExpect(jsonPath("$.hometeamName").value(DEFAULT_HOMETEAM_NAME))
            .andExpect(jsonPath("$.visitorteamName").value(DEFAULT_VISITORTEAM_NAME))
            .andExpect(jsonPath("$.leagueId").value(DEFAULT_LEAGUE_ID))
            .andExpect(jsonPath("$.league").value(DEFAULT_LEAGUE))
            .andExpect(jsonPath("$.fixtureImgContentType").value(DEFAULT_FIXTURE_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.fixtureImg").value(Base64Utils.encodeToString(DEFAULT_FIXTURE_IMG)))
            .andExpect(jsonPath("$.formationImgContentType").value(DEFAULT_FORMATION_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.formationImg").value(Base64Utils.encodeToString(DEFAULT_FORMATION_IMG)))
            .andExpect(jsonPath("$.startUtcTimestamp").value(DEFAULT_START_UTC_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.venueName").value(DEFAULT_VENUE_NAME))
            .andExpect(jsonPath("$.venueCity").value(DEFAULT_VENUE_CITY))
            .andExpect(jsonPath("$.matchImgContentType").value(DEFAULT_MATCH_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.matchImg").value(Base64Utils.encodeToString(DEFAULT_MATCH_IMG)))
            .andExpect(jsonPath("$.matchImgTxt").value(DEFAULT_MATCH_IMG_TXT))
            .andExpect(jsonPath("$.headline").value(DEFAULT_HEADLINE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMatchPreview() throws Exception {
        // Get the matchPreview
        restMatchPreviewMockMvc.perform(get("/api/match-previews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchPreview() throws Exception {
        // Initialize the database
        matchPreviewService.save(matchPreview);

        int databaseSizeBeforeUpdate = matchPreviewRepository.findAll().size();

        // Update the matchPreview
        MatchPreview updatedMatchPreview = matchPreviewRepository.findById(matchPreview.getId()).get();
        // Disconnect from session so that the updates on updatedMatchPreview are not directly saved in db
        em.detach(updatedMatchPreview);
        updatedMatchPreview
            .blurbFull(UPDATED_BLURB_FULL)
            .fixtureId(UPDATED_FIXTURE_ID)
            .hometeamId(UPDATED_HOMETEAM_ID)
            .visitorteamId(UPDATED_VISITORTEAM_ID)
            .hometeamName(UPDATED_HOMETEAM_NAME)
            .visitorteamName(UPDATED_VISITORTEAM_NAME)
            .leagueId(UPDATED_LEAGUE_ID)
            .league(UPDATED_LEAGUE)
            .fixtureImg(UPDATED_FIXTURE_IMG)
            .fixtureImgContentType(UPDATED_FIXTURE_IMG_CONTENT_TYPE)
            .formationImg(UPDATED_FORMATION_IMG)
            .formationImgContentType(UPDATED_FORMATION_IMG_CONTENT_TYPE)
            .startUtcTimestamp(UPDATED_START_UTC_TIMESTAMP)
            .venueName(UPDATED_VENUE_NAME)
            .venueCity(UPDATED_VENUE_CITY)
            .matchImg(UPDATED_MATCH_IMG)
            .matchImgContentType(UPDATED_MATCH_IMG_CONTENT_TYPE)
            .matchImgTxt(UPDATED_MATCH_IMG_TXT)
            .headline(UPDATED_HEADLINE)
            .date(UPDATED_DATE)
            .language(UPDATED_LANGUAGE);

        restMatchPreviewMockMvc.perform(put("/api/match-previews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchPreview)))
            .andExpect(status().isOk());

        // Validate the MatchPreview in the database
        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeUpdate);
        MatchPreview testMatchPreview = matchPreviewList.get(matchPreviewList.size() - 1);
        assertThat(testMatchPreview.getBlurbFull()).isEqualTo(UPDATED_BLURB_FULL);
        assertThat(testMatchPreview.getFixtureId()).isEqualTo(UPDATED_FIXTURE_ID);
        assertThat(testMatchPreview.getHometeamId()).isEqualTo(UPDATED_HOMETEAM_ID);
        assertThat(testMatchPreview.getVisitorteamId()).isEqualTo(UPDATED_VISITORTEAM_ID);
        assertThat(testMatchPreview.getHometeamName()).isEqualTo(UPDATED_HOMETEAM_NAME);
        assertThat(testMatchPreview.getVisitorteamName()).isEqualTo(UPDATED_VISITORTEAM_NAME);
        assertThat(testMatchPreview.getLeagueId()).isEqualTo(UPDATED_LEAGUE_ID);
        assertThat(testMatchPreview.getLeague()).isEqualTo(UPDATED_LEAGUE);
        assertThat(testMatchPreview.getFixtureImg()).isEqualTo(UPDATED_FIXTURE_IMG);
        assertThat(testMatchPreview.getFixtureImgContentType()).isEqualTo(UPDATED_FIXTURE_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getFormationImg()).isEqualTo(UPDATED_FORMATION_IMG);
        assertThat(testMatchPreview.getFormationImgContentType()).isEqualTo(UPDATED_FORMATION_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getStartUtcTimestamp()).isEqualTo(UPDATED_START_UTC_TIMESTAMP);
        assertThat(testMatchPreview.getVenueName()).isEqualTo(UPDATED_VENUE_NAME);
        assertThat(testMatchPreview.getVenueCity()).isEqualTo(UPDATED_VENUE_CITY);
        assertThat(testMatchPreview.getMatchImg()).isEqualTo(UPDATED_MATCH_IMG);
        assertThat(testMatchPreview.getMatchImgContentType()).isEqualTo(UPDATED_MATCH_IMG_CONTENT_TYPE);
        assertThat(testMatchPreview.getMatchImgTxt()).isEqualTo(UPDATED_MATCH_IMG_TXT);
        assertThat(testMatchPreview.getHeadline()).isEqualTo(UPDATED_HEADLINE);
        assertThat(testMatchPreview.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMatchPreview.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the MatchPreview in Elasticsearch
        verify(mockMatchPreviewSearchRepository, times(2)).save(testMatchPreview);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchPreview() throws Exception {
        int databaseSizeBeforeUpdate = matchPreviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchPreviewMockMvc.perform(put("/api/match-previews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchPreview)))
            .andExpect(status().isBadRequest());

        // Validate the MatchPreview in the database
        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchPreview in Elasticsearch
        verify(mockMatchPreviewSearchRepository, times(0)).save(matchPreview);
    }

    @Test
    @Transactional
    public void deleteMatchPreview() throws Exception {
        // Initialize the database
        matchPreviewService.save(matchPreview);

        int databaseSizeBeforeDelete = matchPreviewRepository.findAll().size();

        // Delete the matchPreview
        restMatchPreviewMockMvc.perform(delete("/api/match-previews/{id}", matchPreview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchPreview> matchPreviewList = matchPreviewRepository.findAll();
        assertThat(matchPreviewList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchPreview in Elasticsearch
        verify(mockMatchPreviewSearchRepository, times(1)).deleteById(matchPreview.getId());
    }

    @Test
    @Transactional
    public void searchMatchPreview() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        matchPreviewService.save(matchPreview);
        when(mockMatchPreviewSearchRepository.search(queryStringQuery("id:" + matchPreview.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchPreview), PageRequest.of(0, 1), 1));

        // Search the matchPreview
        restMatchPreviewMockMvc.perform(get("/api/_search/match-previews?query=id:" + matchPreview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPreview.getId().intValue())))
            .andExpect(jsonPath("$.[*].blurbFull").value(hasItem(DEFAULT_BLURB_FULL.toString())))
            .andExpect(jsonPath("$.[*].fixtureId").value(hasItem(DEFAULT_FIXTURE_ID)))
            .andExpect(jsonPath("$.[*].hometeamId").value(hasItem(DEFAULT_HOMETEAM_ID)))
            .andExpect(jsonPath("$.[*].visitorteamId").value(hasItem(DEFAULT_VISITORTEAM_ID)))
            .andExpect(jsonPath("$.[*].hometeamName").value(hasItem(DEFAULT_HOMETEAM_NAME)))
            .andExpect(jsonPath("$.[*].visitorteamName").value(hasItem(DEFAULT_VISITORTEAM_NAME)))
            .andExpect(jsonPath("$.[*].leagueId").value(hasItem(DEFAULT_LEAGUE_ID)))
            .andExpect(jsonPath("$.[*].league").value(hasItem(DEFAULT_LEAGUE)))
            .andExpect(jsonPath("$.[*].fixtureImgContentType").value(hasItem(DEFAULT_FIXTURE_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fixtureImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIXTURE_IMG))))
            .andExpect(jsonPath("$.[*].formationImgContentType").value(hasItem(DEFAULT_FORMATION_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].formationImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_FORMATION_IMG))))
            .andExpect(jsonPath("$.[*].startUtcTimestamp").value(hasItem(DEFAULT_START_UTC_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].venueName").value(hasItem(DEFAULT_VENUE_NAME)))
            .andExpect(jsonPath("$.[*].venueCity").value(hasItem(DEFAULT_VENUE_CITY)))
            .andExpect(jsonPath("$.[*].matchImgContentType").value(hasItem(DEFAULT_MATCH_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].matchImg").value(hasItem(Base64Utils.encodeToString(DEFAULT_MATCH_IMG))))
            .andExpect(jsonPath("$.[*].matchImgTxt").value(hasItem(DEFAULT_MATCH_IMG_TXT)))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
}

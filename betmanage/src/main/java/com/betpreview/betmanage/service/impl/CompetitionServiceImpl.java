package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.repository.CompetitionRepository;
import com.betpreview.betmanage.repository.search.CompetitionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Competition}.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionSearchRepository competitionSearchRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, CompetitionSearchRepository competitionSearchRepository) {
        this.competitionRepository = competitionRepository;
        this.competitionSearchRepository = competitionSearchRepository;
    }

    @Override
    public Competition save(Competition competition) {
        log.debug("Request to save Competition : {}", competition);
        Competition result = competitionRepository.save(competition);
        competitionSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Competition> findAll(Pageable pageable) {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Competition> findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
        competitionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Competition> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Competitions for query {}", query);
        return competitionSearchRepository.search(queryStringQuery(query), pageable);    }
}

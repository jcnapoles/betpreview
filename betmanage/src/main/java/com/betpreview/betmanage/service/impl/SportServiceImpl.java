package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.repository.SportRepository;
import com.betpreview.betmanage.repository.search.SportSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sport}.
 */
@Service
@Transactional
public class SportServiceImpl implements SportService {

    private final Logger log = LoggerFactory.getLogger(SportServiceImpl.class);

    private final SportRepository sportRepository;

    private final SportSearchRepository sportSearchRepository;

    public SportServiceImpl(SportRepository sportRepository, SportSearchRepository sportSearchRepository) {
        this.sportRepository = sportRepository;
        this.sportSearchRepository = sportSearchRepository;
    }

    @Override
    public Sport save(Sport sport) {
        log.debug("Request to save Sport : {}", sport);
        Sport result = sportRepository.save(sport);
        sportSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sport> findAll() {
        log.debug("Request to get all Sports");
        return sportRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Sport> findOne(Long id) {
        log.debug("Request to get Sport : {}", id);
        return sportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sport : {}", id);
        sportRepository.deleteById(id);
        sportSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sport> search(String query) {
        log.debug("Request to search Sports for query {}", query);
        return StreamSupport
            .stream(sportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}

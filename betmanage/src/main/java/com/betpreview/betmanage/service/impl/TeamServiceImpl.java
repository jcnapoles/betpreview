package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.TeamService;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.repository.TeamRepository;
import com.betpreview.betmanage.repository.search.TeamSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;

    private final TeamSearchRepository teamSearchRepository;

    public TeamServiceImpl(TeamRepository teamRepository, TeamSearchRepository teamSearchRepository) {
        this.teamRepository = teamRepository;
        this.teamSearchRepository = teamSearchRepository;
    }

    @Override
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        Team result = teamRepository.save(team);
        teamSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Team> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findAll(pageable);
    }


    public Page<Team> findAllWithEagerRelationships(Pageable pageable) {
        return teamRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
        teamSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Team> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Teams for query {}", query);
        return teamSearchRepository.search(queryStringQuery(query), pageable);    }
}

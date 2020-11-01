package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.TeamSocialService;
import com.betpreview.betmanage.domain.TeamSocial;
import com.betpreview.betmanage.repository.TeamSocialRepository;
import com.betpreview.betmanage.repository.search.TeamSocialSearchRepository;
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
 * Service Implementation for managing {@link TeamSocial}.
 */
@Service
@Transactional
public class TeamSocialServiceImpl implements TeamSocialService {

    private final Logger log = LoggerFactory.getLogger(TeamSocialServiceImpl.class);

    private final TeamSocialRepository teamSocialRepository;

    private final TeamSocialSearchRepository teamSocialSearchRepository;

    public TeamSocialServiceImpl(TeamSocialRepository teamSocialRepository, TeamSocialSearchRepository teamSocialSearchRepository) {
        this.teamSocialRepository = teamSocialRepository;
        this.teamSocialSearchRepository = teamSocialSearchRepository;
    }

    @Override
    public TeamSocial save(TeamSocial teamSocial) {
        log.debug("Request to save TeamSocial : {}", teamSocial);
        TeamSocial result = teamSocialRepository.save(teamSocial);
        teamSocialSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamSocial> findAll() {
        log.debug("Request to get all TeamSocials");
        return teamSocialRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TeamSocial> findOne(Long id) {
        log.debug("Request to get TeamSocial : {}", id);
        return teamSocialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamSocial : {}", id);
        teamSocialRepository.deleteById(id);
        teamSocialSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamSocial> search(String query) {
        log.debug("Request to search TeamSocials for query {}", query);
        return StreamSupport
            .stream(teamSocialSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }

	/*
	 * @Override public Optional<TeamSocial> findOneByTag(String tag) {
	 * log.debug("Request to get TeamSocial By Tag: {}", tag); return
	 * teamSocialRepository.findOneByTag(tag); }
	 */
}

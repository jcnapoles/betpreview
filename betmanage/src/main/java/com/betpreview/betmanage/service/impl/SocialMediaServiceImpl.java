package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.SocialMediaService;
import com.betpreview.betmanage.domain.SocialMedia;
import com.betpreview.betmanage.repository.SocialMediaRepository;
import com.betpreview.betmanage.repository.search.SocialMediaSearchRepository;
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
 * Service Implementation for managing {@link SocialMedia}.
 */
@Service
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    private final Logger log = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;

    private final SocialMediaSearchRepository socialMediaSearchRepository;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, SocialMediaSearchRepository socialMediaSearchRepository) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaSearchRepository = socialMediaSearchRepository;
    }

    @Override
    public SocialMedia save(SocialMedia socialMedia) {
        log.debug("Request to save SocialMedia : {}", socialMedia);
        SocialMedia result = socialMediaRepository.save(socialMedia);
        socialMediaSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialMedia> findAll() {
        log.debug("Request to get all SocialMedias");
        return socialMediaRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SocialMedia> findOne(Long id) {
        log.debug("Request to get SocialMedia : {}", id);
        return socialMediaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialMedia : {}", id);
        socialMediaRepository.deleteById(id);
        socialMediaSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialMedia> search(String query) {
        log.debug("Request to search SocialMedias for query {}", query);
        return StreamSupport
            .stream(socialMediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}

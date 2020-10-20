package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.MatchPreviewService;
import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.repository.MatchPreviewRepository;
import com.betpreview.betmanage.repository.search.MatchPreviewSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MatchPreview}.
 */
@Service
@Transactional
public class MatchPreviewServiceImpl implements MatchPreviewService {

    private final Logger log = LoggerFactory.getLogger(MatchPreviewServiceImpl.class);

    private final MatchPreviewRepository matchPreviewRepository;

    private final MatchPreviewSearchRepository matchPreviewSearchRepository;

    public MatchPreviewServiceImpl(MatchPreviewRepository matchPreviewRepository, MatchPreviewSearchRepository matchPreviewSearchRepository) {
        this.matchPreviewRepository = matchPreviewRepository;
        this.matchPreviewSearchRepository = matchPreviewSearchRepository;
    }

    @Override
    public MatchPreview save(MatchPreview matchPreview) {
        log.debug("Request to save MatchPreview : {}", matchPreview);
        MatchPreview result = matchPreviewRepository.save(matchPreview);
        matchPreviewSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchPreview> findAll(Pageable pageable) {
        log.debug("Request to get all MatchPreviews");
        return matchPreviewRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MatchPreview> findOne(Long id) {
        log.debug("Request to get MatchPreview : {}", id);
        return matchPreviewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatchPreview : {}", id);
        matchPreviewRepository.deleteById(id);
        matchPreviewSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatchPreview> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MatchPreviews for query {}", query);
        return matchPreviewSearchRepository.search(queryStringQuery(query), pageable);    }
}

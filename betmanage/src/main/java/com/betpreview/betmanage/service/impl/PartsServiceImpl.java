package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.PartsService;
import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Parts;
import com.betpreview.betmanage.repository.PartsRepository;
import com.betpreview.betmanage.repository.search.PartsSearchRepository;
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
 * Service Implementation for managing {@link Parts}.
 */
@Service
@Transactional
public class PartsServiceImpl implements PartsService {

    private final Logger log = LoggerFactory.getLogger(PartsServiceImpl.class);

    private final PartsRepository partsRepository;

    private final PartsSearchRepository partsSearchRepository;

    public PartsServiceImpl(PartsRepository partsRepository, PartsSearchRepository partsSearchRepository) {
        this.partsRepository = partsRepository;
        this.partsSearchRepository = partsSearchRepository;
    }

    @Override
    public Parts save(Parts parts) {
        log.debug("Request to save Parts : {}", parts);
        Parts result = partsRepository.save(parts);
        partsSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parts> findAll() {
        log.debug("Request to get all Parts");
        return partsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Parts> findOne(Long id) {
        log.debug("Request to get Parts : {}", id);
        return partsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parts : {}", id);
        partsRepository.deleteById(id);
        partsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parts> search(String query) {
        log.debug("Request to search Parts for query {}", query);
        return StreamSupport
            .stream(partsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }

	@Override
	@Transactional(readOnly = true)
	public Optional<Parts> findOneByMatchPreview(MatchPreview matchPreview) {
		log.debug("Request to get Parts by MatchPreview: {}", matchPreview.getId());
		return partsRepository.findOneByMatchPreview(matchPreview);
	}
}

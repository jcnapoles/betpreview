package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.TitleService;
import com.betpreview.betmanage.domain.Title;
import com.betpreview.betmanage.repository.TitleRepository;
import com.betpreview.betmanage.repository.search.TitleSearchRepository;
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
 * Service Implementation for managing {@link Title}.
 */
@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    private final Logger log = LoggerFactory.getLogger(TitleServiceImpl.class);

    private final TitleRepository titleRepository;

    private final TitleSearchRepository titleSearchRepository;

    public TitleServiceImpl(TitleRepository titleRepository, TitleSearchRepository titleSearchRepository) {
        this.titleRepository = titleRepository;
        this.titleSearchRepository = titleSearchRepository;
    }

    @Override
    public Title save(Title title) {
        log.debug("Request to save Title : {}", title);
        Title result = titleRepository.save(title);
        titleSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Title> findAll() {
        log.debug("Request to get all Titles");
        return titleRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Title> findOne(Long id) {
        log.debug("Request to get Title : {}", id);
        return titleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Title : {}", id);
        titleRepository.deleteById(id);
        titleSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Title> search(String query) {
        log.debug("Request to search Titles for query {}", query);
        return StreamSupport
            .stream(titleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}

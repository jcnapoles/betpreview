package com.betpreview.betmanage.service.impl;

import com.betpreview.betmanage.service.ParagraphsService;
import com.betpreview.betmanage.domain.Paragraphs;
import com.betpreview.betmanage.repository.ParagraphsRepository;
import com.betpreview.betmanage.repository.search.ParagraphsSearchRepository;
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
 * Service Implementation for managing {@link Paragraphs}.
 */
@Service
@Transactional
public class ParagraphsServiceImpl implements ParagraphsService {

    private final Logger log = LoggerFactory.getLogger(ParagraphsServiceImpl.class);

    private final ParagraphsRepository paragraphsRepository;

    private final ParagraphsSearchRepository paragraphsSearchRepository;

    public ParagraphsServiceImpl(ParagraphsRepository paragraphsRepository, ParagraphsSearchRepository paragraphsSearchRepository) {
        this.paragraphsRepository = paragraphsRepository;
        this.paragraphsSearchRepository = paragraphsSearchRepository;
    }

    @Override
    public Paragraphs save(Paragraphs paragraphs) {
        log.debug("Request to save Paragraphs : {}", paragraphs);
        Paragraphs result = paragraphsRepository.save(paragraphs);
        paragraphsSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paragraphs> findAll() {
        log.debug("Request to get all Paragraphs");
        return paragraphsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Paragraphs> findOne(Long id) {
        log.debug("Request to get Paragraphs : {}", id);
        return paragraphsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paragraphs : {}", id);
        paragraphsRepository.deleteById(id);
        paragraphsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paragraphs> search(String query) {
        log.debug("Request to search Paragraphs for query {}", query);
        return StreamSupport
            .stream(paragraphsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}

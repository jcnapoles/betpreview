package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.Paragraphs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Paragraphs} entity.
 */
public interface ParagraphsSearchRepository extends ElasticsearchRepository<Paragraphs, Long> {
}

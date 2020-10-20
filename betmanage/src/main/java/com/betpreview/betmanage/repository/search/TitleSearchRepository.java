package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.Title;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Title} entity.
 */
public interface TitleSearchRepository extends ElasticsearchRepository<Title, Long> {
}

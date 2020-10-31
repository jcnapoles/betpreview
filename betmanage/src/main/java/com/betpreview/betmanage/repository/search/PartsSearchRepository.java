package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.Parts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Parts} entity.
 */
public interface PartsSearchRepository extends ElasticsearchRepository<Parts, Long> {
}

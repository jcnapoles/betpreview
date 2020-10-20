package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.Sport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Sport} entity.
 */
public interface SportSearchRepository extends ElasticsearchRepository<Sport, Long> {
}

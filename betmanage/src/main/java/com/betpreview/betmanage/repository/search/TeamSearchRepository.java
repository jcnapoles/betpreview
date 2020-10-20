package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.Team;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Team} entity.
 */
public interface TeamSearchRepository extends ElasticsearchRepository<Team, Long> {
}

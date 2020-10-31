package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.TeamSocial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TeamSocial} entity.
 */
public interface TeamSocialSearchRepository extends ElasticsearchRepository<TeamSocial, Long> {
}

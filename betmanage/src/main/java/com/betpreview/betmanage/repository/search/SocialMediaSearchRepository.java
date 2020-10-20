package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.SocialMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SocialMedia} entity.
 */
public interface SocialMediaSearchRepository extends ElasticsearchRepository<SocialMedia, Long> {
}

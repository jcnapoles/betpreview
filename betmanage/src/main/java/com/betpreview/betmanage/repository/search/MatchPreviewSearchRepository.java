package com.betpreview.betmanage.repository.search;

import com.betpreview.betmanage.domain.MatchPreview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MatchPreview} entity.
 */
public interface MatchPreviewSearchRepository extends ElasticsearchRepository<MatchPreview, Long> {
}

package com.betpreview.betmanage.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MatchPreviewSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MatchPreviewSearchRepositoryMockConfiguration {

    @MockBean
    private MatchPreviewSearchRepository mockMatchPreviewSearchRepository;

}

package com.betpreview.betmanage.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TitleSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TitleSearchRepositoryMockConfiguration {

    @MockBean
    private TitleSearchRepository mockTitleSearchRepository;

}

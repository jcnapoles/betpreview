package com.betpreview.betmanage.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PartsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartsSearchRepositoryMockConfiguration {

    @MockBean
    private PartsSearchRepository mockPartsSearchRepository;

}

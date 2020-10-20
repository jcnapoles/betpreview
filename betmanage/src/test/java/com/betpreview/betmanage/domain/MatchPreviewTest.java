package com.betpreview.betmanage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betpreview.betmanage.web.rest.TestUtil;

public class MatchPreviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchPreview.class);
        MatchPreview matchPreview1 = new MatchPreview();
        matchPreview1.setId(1L);
        MatchPreview matchPreview2 = new MatchPreview();
        matchPreview2.setId(matchPreview1.getId());
        assertThat(matchPreview1).isEqualTo(matchPreview2);
        matchPreview2.setId(2L);
        assertThat(matchPreview1).isNotEqualTo(matchPreview2);
        matchPreview1.setId(null);
        assertThat(matchPreview1).isNotEqualTo(matchPreview2);
    }
}

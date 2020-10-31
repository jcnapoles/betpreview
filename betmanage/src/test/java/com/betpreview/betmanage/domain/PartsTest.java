package com.betpreview.betmanage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betpreview.betmanage.web.rest.TestUtil;

public class PartsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parts.class);
        Parts parts1 = new Parts();
        parts1.setId(1L);
        Parts parts2 = new Parts();
        parts2.setId(parts1.getId());
        assertThat(parts1).isEqualTo(parts2);
        parts2.setId(2L);
        assertThat(parts1).isNotEqualTo(parts2);
        parts1.setId(null);
        assertThat(parts1).isNotEqualTo(parts2);
    }
}

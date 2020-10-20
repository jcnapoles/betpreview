package com.betpreview.betmanage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betpreview.betmanage.web.rest.TestUtil;

public class ParagraphsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paragraphs.class);
        Paragraphs paragraphs1 = new Paragraphs();
        paragraphs1.setId(1L);
        Paragraphs paragraphs2 = new Paragraphs();
        paragraphs2.setId(paragraphs1.getId());
        assertThat(paragraphs1).isEqualTo(paragraphs2);
        paragraphs2.setId(2L);
        assertThat(paragraphs1).isNotEqualTo(paragraphs2);
        paragraphs1.setId(null);
        assertThat(paragraphs1).isNotEqualTo(paragraphs2);
    }
}

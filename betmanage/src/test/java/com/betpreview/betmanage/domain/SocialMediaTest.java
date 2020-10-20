package com.betpreview.betmanage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betpreview.betmanage.web.rest.TestUtil;

public class SocialMediaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialMedia.class);
        SocialMedia socialMedia1 = new SocialMedia();
        socialMedia1.setId(1L);
        SocialMedia socialMedia2 = new SocialMedia();
        socialMedia2.setId(socialMedia1.getId());
        assertThat(socialMedia1).isEqualTo(socialMedia2);
        socialMedia2.setId(2L);
        assertThat(socialMedia1).isNotEqualTo(socialMedia2);
        socialMedia1.setId(null);
        assertThat(socialMedia1).isNotEqualTo(socialMedia2);
    }
}

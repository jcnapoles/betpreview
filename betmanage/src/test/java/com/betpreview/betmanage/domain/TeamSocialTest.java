package com.betpreview.betmanage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.betpreview.betmanage.web.rest.TestUtil;

public class TeamSocialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamSocial.class);
        TeamSocial teamSocial1 = new TeamSocial();
        teamSocial1.setId(1L);
        TeamSocial teamSocial2 = new TeamSocial();
        teamSocial2.setId(teamSocial1.getId());
        assertThat(teamSocial1).isEqualTo(teamSocial2);
        teamSocial2.setId(2L);
        assertThat(teamSocial1).isNotEqualTo(teamSocial2);
        teamSocial1.setId(null);
        assertThat(teamSocial1).isNotEqualTo(teamSocial2);
    }
}

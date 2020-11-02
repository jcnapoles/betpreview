package com.betpreview.betmanage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TeamSocial.
 */
@Entity
@Table(name = "team_social")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "teamsocial")
public class TeamSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_team_id")
    private Integer homeTeamId;

    @Column(name = "visitor_team_id")
    private Integer visitorTeamId;

    @Column(name = "jhi_match")
    private String match;

    @OneToMany(mappedBy = "teamSocial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SocialMedia> socialMediaMatches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public TeamSocial homeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
        return this;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getVisitorTeamId() {
        return visitorTeamId;
    }

    public TeamSocial visitorTeamId(Integer visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
        return this;
    }

    public void setVisitorTeamId(Integer visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }

    public String getMatch() {
        return match;
    }

    public TeamSocial match(String match) {
        this.match = match;
        return this;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Set<SocialMedia> getSocialMediaMatches() {
        return socialMediaMatches;
    }

    public TeamSocial socialMediaMatches(Set<SocialMedia> socialMedias) {
        this.socialMediaMatches = socialMedias;
        return this;
    }

    public TeamSocial addSocialMediaMatch(SocialMedia socialMedia) {
        this.socialMediaMatches.add(socialMedia);
        socialMedia.setTeamSocial(this);
        return this;
    }

    public TeamSocial removeSocialMediaMatch(SocialMedia socialMedia) {
        this.socialMediaMatches.remove(socialMedia);
        socialMedia.setTeamSocial(null);
        return this;
    }

    public void setSocialMediaMatches(Set<SocialMedia> socialMedias) {
        this.socialMediaMatches = socialMedias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamSocial)) {
            return false;
        }
        return id != null && id.equals(((TeamSocial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamSocial{" +
            "id=" + getId() +
            ", homeTeamId=" + getHomeTeamId() +
            ", visitorTeamId=" + getVisitorTeamId() +
            ", match='" + getMatch() + "'" +
            "}";
    }
}

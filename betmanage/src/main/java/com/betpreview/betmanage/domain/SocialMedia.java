package com.betpreview.betmanage.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.betpreview.betmanage.domain.enumeration.PlatformEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "social_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "socialmedia")
public class SocialMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private PlatformEnum platform;

    @ManyToOne
    @JsonIgnoreProperties(value = "socialMedias", allowSetters = true)
    private Team team;

    @ManyToOne
    @JsonIgnoreProperties(value = "socialMedias", allowSetters = true)
    private TeamSocial teamSocial;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public SocialMedia tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }

    public SocialMedia platform(PlatformEnum platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(PlatformEnum platform) {
        this.platform = platform;
    }

    public Team getTeam() {
        return team;
    }

    public SocialMedia team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamSocial getTeamSocial() {
        return teamSocial;
    }

    public SocialMedia teamSocial(TeamSocial teamSocial) {
        this.teamSocial = teamSocial;
        return this;
    }

    public void setTeamSocial(TeamSocial teamSocial) {
        this.teamSocial = teamSocial;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialMedia)) {
            return false;
        }
        return id != null && id.equals(((SocialMedia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialMedia{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", platform='" + getPlatform() + "'" +
            "}";
    }
}

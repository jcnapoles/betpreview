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

    @Column(name = "home")
    private Integer home;

    @Column(name = "visitor")
    private Integer visitor;

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

    public Integer getHome() {
        return home;
    }

    public TeamSocial home(Integer home) {
        this.home = home;
        return this;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getVisitor() {
        return visitor;
    }

    public TeamSocial visitor(Integer visitor) {
        this.visitor = visitor;
        return this;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
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
            ", home=" + getHome() +
            ", visitor=" + getVisitor() +
            ", match='" + getMatch() + "'" +
            "}";
    }
}

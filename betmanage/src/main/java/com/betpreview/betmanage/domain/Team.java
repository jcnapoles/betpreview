package com.betpreview.betmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "is_national_team")
    private Boolean isNationalTeam;

    @Lob
    @Column(name = "team_logo")
    private byte[] teamLogo;

    @Column(name = "team_logo_content_type")
    private String teamLogoContentType;

    @Column(name = "team_id")
    private Integer teamId;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SocialMedia> socialMedias = new HashSet<>();

    @OneToMany(mappedBy = "homeTeam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MatchPreview> matchPreviewsHomes = new HashSet<>();

    @OneToMany(mappedBy = "visitorTeam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MatchPreview> matchPreviewsVisitors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "teams", allowSetters = true)
    private Country country;

    @ManyToMany(mappedBy = "teams")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Competition> competitions = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<MatchPreview> matchPreviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public Team shortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Boolean isIsNationalTeam() {
        return isNationalTeam;
    }

    public Team isNationalTeam(Boolean isNationalTeam) {
        this.isNationalTeam = isNationalTeam;
        return this;
    }

    public void setIsNationalTeam(Boolean isNationalTeam) {
        this.isNationalTeam = isNationalTeam;
    }

    public byte[] getTeamLogo() {
        return teamLogo;
    }

    public Team teamLogo(byte[] teamLogo) {
        this.teamLogo = teamLogo;
        return this;
    }

    public void setTeamLogo(byte[] teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getTeamLogoContentType() {
        return teamLogoContentType;
    }

    public Team teamLogoContentType(String teamLogoContentType) {
        this.teamLogoContentType = teamLogoContentType;
        return this;
    }

    public void setTeamLogoContentType(String teamLogoContentType) {
        this.teamLogoContentType = teamLogoContentType;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Team teamId(Integer teamId) {
        this.teamId = teamId;
        return this;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Set<SocialMedia> getSocialMedias() {
        return socialMedias;
    }

    public Team socialMedias(Set<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
        return this;
    }

    public Team addSocialMedia(SocialMedia socialMedia) {
        this.socialMedias.add(socialMedia);
        socialMedia.setTeam(this);
        return this;
    }

    public Team removeSocialMedia(SocialMedia socialMedia) {
        this.socialMedias.remove(socialMedia);
        socialMedia.setTeam(null);
        return this;
    }

    public void setSocialMedias(Set<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
    }

    public Set<MatchPreview> getMatchPreviewsHomes() {
        return matchPreviewsHomes;
    }

    public Team matchPreviewsHomes(Set<MatchPreview> matchPreviews) {
        this.matchPreviewsHomes = matchPreviews;
        return this;
    }

    public Team addMatchPreviewsHome(MatchPreview matchPreview) {
        this.matchPreviewsHomes.add(matchPreview);
        matchPreview.setHomeTeam(this);
        return this;
    }

    public Team removeMatchPreviewsHome(MatchPreview matchPreview) {
        this.matchPreviewsHomes.remove(matchPreview);
        matchPreview.setHomeTeam(null);
        return this;
    }

    public void setMatchPreviewsHomes(Set<MatchPreview> matchPreviews) {
        this.matchPreviewsHomes = matchPreviews;
    }

    public Set<MatchPreview> getMatchPreviewsVisitors() {
        return matchPreviewsVisitors;
    }

    public Team matchPreviewsVisitors(Set<MatchPreview> matchPreviews) {
        this.matchPreviewsVisitors = matchPreviews;
        return this;
    }

    public Team addMatchPreviewsVisitor(MatchPreview matchPreview) {
        this.matchPreviewsVisitors.add(matchPreview);
        matchPreview.setVisitorTeam(this);
        return this;
    }

    public Team removeMatchPreviewsVisitor(MatchPreview matchPreview) {
        this.matchPreviewsVisitors.remove(matchPreview);
        matchPreview.setVisitorTeam(null);
        return this;
    }

    public void setMatchPreviewsVisitors(Set<MatchPreview> matchPreviews) {
        this.matchPreviewsVisitors = matchPreviews;
    }

    public Country getCountry() {
        return country;
    }

    public Team country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public Team competitions(Set<Competition> competitions) {
        this.competitions = competitions;
        return this;
    }

    public Team addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.getTeams().add(this);
        return this;
    }

    public Team removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.getTeams().remove(this);
        return this;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

    public Set<MatchPreview> getMatchPreviews() {
        return matchPreviews;
    }

    public Team matchPreviews(Set<MatchPreview> matchPreviews) {
        this.matchPreviews = matchPreviews;
        return this;
    }

    public Team addMatchPreview(MatchPreview matchPreview) {
        this.matchPreviews.add(matchPreview);
        matchPreview.getTeams().add(this);
        return this;
    }

    public Team removeMatchPreview(MatchPreview matchPreview) {
        this.matchPreviews.remove(matchPreview);
        matchPreview.getTeams().remove(this);
        return this;
    }

    public void setMatchPreviews(Set<MatchPreview> matchPreviews) {
        this.matchPreviews = matchPreviews;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", teamName='" + getTeamName() + "'" +
            ", shortCode='" + getShortCode() + "'" +
            ", isNationalTeam='" + isIsNationalTeam() + "'" +
            ", teamLogo='" + getTeamLogo() + "'" +
            ", teamLogoContentType='" + getTeamLogoContentType() + "'" +
            ", teamId=" + getTeamId() +
            "}";
    }
}

package com.betpreview.betmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MatchPreview.
 */
@Entity
@Table(name = "match_preview")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchpreview")
public class MatchPreview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fixture_id", nullable = false)
    private Integer fixtureId;

    @Column(name = "blurb_full")
    private String blurbFull;

    @Column(name = "hometeam_id")
    private Integer hometeamId;

    @Column(name = "visitorteam_id")
    private Integer visitorteamId;

    @Column(name = "hometeam_name")
    private String hometeamName;

    @Column(name = "visitorteam_name")
    private String visitorteamName;

    @Column(name = "league_id")
    private Integer leagueId;

    @Column(name = "league")
    private String league;

    @Column(name = "formation_img")
    private String formationImg;

    @Column(name = "fixture_img")
    private String fixtureImg;

    @OneToOne
    @JoinColumn(unique = true)
    private Country country;

    @OneToMany(mappedBy = "quickItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Title> titles = new HashSet<>();

    @OneToMany(mappedBy = "blurbSplit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Paragraphs> paragraphs = new HashSet<>();

    @ManyToMany(mappedBy = "matchPreviews")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFixtureId() {
        return fixtureId;
    }

    public MatchPreview fixtureId(Integer fixtureId) {
        this.fixtureId = fixtureId;
        return this;
    }

    public void setFixtureId(Integer fixtureId) {
        this.fixtureId = fixtureId;
    }

    public String getBlurbFull() {
        return blurbFull;
    }

    public MatchPreview blurbFull(String blurbFull) {
        this.blurbFull = blurbFull;
        return this;
    }

    public void setBlurbFull(String blurbFull) {
        this.blurbFull = blurbFull;
    }

    public Integer getHometeamId() {
        return hometeamId;
    }

    public MatchPreview hometeamId(Integer hometeamId) {
        this.hometeamId = hometeamId;
        return this;
    }

    public void setHometeamId(Integer hometeamId) {
        this.hometeamId = hometeamId;
    }

    public Integer getVisitorteamId() {
        return visitorteamId;
    }

    public MatchPreview visitorteamId(Integer visitorteamId) {
        this.visitorteamId = visitorteamId;
        return this;
    }

    public void setVisitorteamId(Integer visitorteamId) {
        this.visitorteamId = visitorteamId;
    }

    public String getHometeamName() {
        return hometeamName;
    }

    public MatchPreview hometeamName(String hometeamName) {
        this.hometeamName = hometeamName;
        return this;
    }

    public void setHometeamName(String hometeamName) {
        this.hometeamName = hometeamName;
    }

    public String getVisitorteamName() {
        return visitorteamName;
    }

    public MatchPreview visitorteamName(String visitorteamName) {
        this.visitorteamName = visitorteamName;
        return this;
    }

    public void setVisitorteamName(String visitorteamName) {
        this.visitorteamName = visitorteamName;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public MatchPreview leagueId(Integer leagueId) {
        this.leagueId = leagueId;
        return this;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeague() {
        return league;
    }

    public MatchPreview league(String league) {
        this.league = league;
        return this;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getFormationImg() {
        return formationImg;
    }

    public MatchPreview formationImg(String formationImg) {
        this.formationImg = formationImg;
        return this;
    }

    public void setFormationImg(String formationImg) {
        this.formationImg = formationImg;
    }

    public String getFixtureImg() {
        return fixtureImg;
    }

    public MatchPreview fixtureImg(String fixtureImg) {
        this.fixtureImg = fixtureImg;
        return this;
    }

    public void setFixtureImg(String fixtureImg) {
        this.fixtureImg = fixtureImg;
    }

    public Country getCountry() {
        return country;
    }

    public MatchPreview country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Title> getTitles() {
        return titles;
    }

    public MatchPreview titles(Set<Title> titles) {
        this.titles = titles;
        return this;
    }

    public MatchPreview addTitle(Title title) {
        this.titles.add(title);
        title.setQuickItems(this);
        return this;
    }

    public MatchPreview removeTitle(Title title) {
        this.titles.remove(title);
        title.setQuickItems(null);
        return this;
    }

    public void setTitles(Set<Title> titles) {
        this.titles = titles;
    }

    public Set<Paragraphs> getParagraphs() {
        return paragraphs;
    }

    public MatchPreview paragraphs(Set<Paragraphs> paragraphs) {
        this.paragraphs = paragraphs;
        return this;
    }

    public MatchPreview addParagraphs(Paragraphs paragraphs) {
        this.paragraphs.add(paragraphs);
        paragraphs.setBlurbSplit(this);
        return this;
    }

    public MatchPreview removeParagraphs(Paragraphs paragraphs) {
        this.paragraphs.remove(paragraphs);
        paragraphs.setBlurbSplit(null);
        return this;
    }

    public void setParagraphs(Set<Paragraphs> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public MatchPreview teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public MatchPreview addTeam(Team team) {
        this.teams.add(team);
        team.getMatchPreviews().add(this);
        return this;
    }

    public MatchPreview removeTeam(Team team) {
        this.teams.remove(team);
        team.getMatchPreviews().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchPreview)) {
            return false;
        }
        return id != null && id.equals(((MatchPreview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPreview{" +
            "id=" + getId() +
            ", fixtureId=" + getFixtureId() +
            ", blurbFull='" + getBlurbFull() + "'" +
            ", hometeamId=" + getHometeamId() +
            ", visitorteamId=" + getVisitorteamId() +
            ", hometeamName='" + getHometeamName() + "'" +
            ", visitorteamName='" + getVisitorteamName() + "'" +
            ", leagueId=" + getLeagueId() +
            ", league='" + getLeague() + "'" +
            ", formationImg='" + getFormationImg() + "'" +
            ", fixtureImg='" + getFixtureImg() + "'" +
            "}";
    }
}

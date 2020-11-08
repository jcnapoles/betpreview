package com.betpreview.betmanage.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.betpreview.betmanage.domain.enumeration.LanguageEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Lob
    @Column(name = "blurb_full")
    private String blurbFull;

    @NotNull
    @Column(name = "fixture_id", nullable = false)
    private Integer fixtureId;

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

    @Lob
    @Column(name = "fixture_img")
    private byte[] fixtureImg;

    @Column(name = "fixture_img_content_type")
    private String fixtureImgContentType;

    @Lob
    @Column(name = "formation_img")
    private byte[] formationImg;

    @Column(name = "formation_img_content_type")
    private String formationImgContentType;

    @Column(name = "start_utc_timestamp")
    private Instant startUtcTimestamp;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "venue_city")
    private String venueCity;

    @Lob
    @Column(name = "match_img")
    private byte[] matchImg;

    @Column(name = "match_img_content_type")
    private String matchImgContentType;

    @Column(name = "match_ima_txt")
    private String matchImaTxt;

    @Column(name = "headline")
    private String headline;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private LanguageEnum language;

    @OneToMany(mappedBy = "matchPreview")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Title> titles = new HashSet<>();

    @OneToMany(mappedBy = "matchPreview")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Paragraphs> paragraphs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "match_preview_team",
               joinColumns = @JoinColumn(name = "match_preview_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "matchPreviews", allowSetters = true)
    private Competition competition;

    @ManyToOne
    @JsonIgnoreProperties(value = "matchPreviewsHomes", allowSetters = true)
    private Team homeTeam;

    @ManyToOne
    @JsonIgnoreProperties(value = "matchPreviewsVisitors", allowSetters = true)
    private Team visitorTeam;

    @ManyToOne
    @JsonIgnoreProperties(value = "matchPreviews", allowSetters = true)
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getFixtureImg() {
        return fixtureImg;
    }

    public MatchPreview fixtureImg(byte[] fixtureImg) {
        this.fixtureImg = fixtureImg;
        return this;
    }

    public void setFixtureImg(byte[] fixtureImg) {
        this.fixtureImg = fixtureImg;
    }

    public String getFixtureImgContentType() {
        return fixtureImgContentType;
    }

    public MatchPreview fixtureImgContentType(String fixtureImgContentType) {
        this.fixtureImgContentType = fixtureImgContentType;
        return this;
    }

    public void setFixtureImgContentType(String fixtureImgContentType) {
        this.fixtureImgContentType = fixtureImgContentType;
    }

    public byte[] getFormationImg() {
        return formationImg;
    }

    public MatchPreview formationImg(byte[] formationImg) {
        this.formationImg = formationImg;
        return this;
    }

    public void setFormationImg(byte[] formationImg) {
        this.formationImg = formationImg;
    }

    public String getFormationImgContentType() {
        return formationImgContentType;
    }

    public MatchPreview formationImgContentType(String formationImgContentType) {
        this.formationImgContentType = formationImgContentType;
        return this;
    }

    public void setFormationImgContentType(String formationImgContentType) {
        this.formationImgContentType = formationImgContentType;
    }

    public Instant getStartUtcTimestamp() {
        return startUtcTimestamp;
    }

    public MatchPreview startUtcTimestamp(Instant startUtcTimestamp) {
        this.startUtcTimestamp = startUtcTimestamp;
        return this;
    }

    public void setStartUtcTimestamp(Instant startUtcTimestamp) {
        this.startUtcTimestamp = startUtcTimestamp;
    }

    public String getVenueName() {
        return venueName;
    }

    public MatchPreview venueName(String venueName) {
        this.venueName = venueName;
        return this;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public MatchPreview venueCity(String venueCity) {
        this.venueCity = venueCity;
        return this;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public byte[] getMatchImg() {
        return matchImg;
    }

    public MatchPreview matchImg(byte[] matchImg) {
        this.matchImg = matchImg;
        return this;
    }

    public void setMatchImg(byte[] matchImg) {
        this.matchImg = matchImg;
    }

    public String getMatchImgContentType() {
        return matchImgContentType;
    }

    public MatchPreview matchImgContentType(String matchImgContentType) {
        this.matchImgContentType = matchImgContentType;
        return this;
    }

    public void setMatchImgContentType(String matchImgContentType) {
        this.matchImgContentType = matchImgContentType;
    }

    public String getMatchImaTxt() {
        return matchImaTxt;
    }

    public MatchPreview matchImaTxt(String matchImaTxt) {
        this.matchImaTxt = matchImaTxt;
        return this;
    }

    public void setMatchImaTxt(String matchImaTxt) {
        this.matchImaTxt = matchImaTxt;
    }

    public String getHeadline() {
        return headline;
    }

    public MatchPreview headline(String headline) {
        this.headline = headline;
        return this;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public LocalDate getDate() {
        return date;
    }

    public MatchPreview date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public MatchPreview language(LanguageEnum language) {
        this.language = language;
        return this;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
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
        title.setMatchPreview(this);
        return this;
    }

    public MatchPreview removeTitle(Title title) {
        this.titles.remove(title);
        title.setMatchPreview(null);
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
        paragraphs.setMatchPreview(this);
        return this;
    }

    public MatchPreview removeParagraphs(Paragraphs paragraphs) {
        this.paragraphs.remove(paragraphs);
        paragraphs.setMatchPreview(null);
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

    public Competition getCompetition() {
        return competition;
    }

    public MatchPreview competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public MatchPreview homeTeam(Team team) {
        this.homeTeam = team;
        return this;
    }

    public void setHomeTeam(Team team) {
        this.homeTeam = team;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public MatchPreview visitorTeam(Team team) {
        this.visitorTeam = team;
        return this;
    }

    public void setVisitorTeam(Team team) {
        this.visitorTeam = team;
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
            ", blurbFull='" + getBlurbFull() + "'" +
            ", fixtureId=" + getFixtureId() +
            ", hometeamId=" + getHometeamId() +
            ", visitorteamId=" + getVisitorteamId() +
            ", hometeamName='" + getHometeamName() + "'" +
            ", visitorteamName='" + getVisitorteamName() + "'" +
            ", leagueId=" + getLeagueId() +
            ", league='" + getLeague() + "'" +
            ", fixtureImg='" + getFixtureImg() + "'" +
            ", fixtureImgContentType='" + getFixtureImgContentType() + "'" +
            ", formationImg='" + getFormationImg() + "'" +
            ", formationImgContentType='" + getFormationImgContentType() + "'" +
            ", startUtcTimestamp='" + getStartUtcTimestamp() + "'" +
            ", venueName='" + getVenueName() + "'" +
            ", venueCity='" + getVenueCity() + "'" +
            ", matchImg='" + getMatchImg() + "'" +
            ", matchImgContentType='" + getMatchImgContentType() + "'" +
            ", matchImaTxt='" + getMatchImaTxt() + "'" +
            ", headline='" + getHeadline() + "'" +
            ", date='" + getDate() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}

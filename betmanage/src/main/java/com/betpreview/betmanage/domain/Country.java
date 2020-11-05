package com.betpreview.betmanage.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Competition> competitions = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MatchPreview> matchPreviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public Country competitions(Set<Competition> competitions) {
        this.competitions = competitions;
        return this;
    }

    public Country addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setCountry(this);
        return this;
    }

    public Country removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setCountry(null);
        return this;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Country teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Country addTeam(Team team) {
        this.teams.add(team);
        team.setCountry(this);
        return this;
    }

    public Country removeTeam(Team team) {
        this.teams.remove(team);
        team.setCountry(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<MatchPreview> getMatchPreviews() {
        return matchPreviews;
    }

    public Country matchPreviews(Set<MatchPreview> matchPreviews) {
        this.matchPreviews = matchPreviews;
        return this;
    }

    public Country addMatchPreview(MatchPreview matchPreview) {
        this.matchPreviews.add(matchPreview);
        matchPreview.setCountry(this);
        return this;
    }

    public Country removeMatchPreview(MatchPreview matchPreview) {
        this.matchPreviews.remove(matchPreview);
        matchPreview.setCountry(null);
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
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}

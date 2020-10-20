package com.betpreview.betmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.betpreview.betmanage.domain.enumeration.TypeCompetition;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "competition_name", nullable = false)
    private String competitionName;

    @Lob
    @Column(name = "competition_logo")
    private byte[] competitionLogo;

    @Column(name = "competition_logo_content_type")
    private String competitionLogoContentType;

    @Column(name = "is_cup")
    private Boolean isCup;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeCompetition type;

    @Column(name = "sportscribe_id")
    private Integer sportscribeId;

    @OneToOne
    @JoinColumn(unique = true)
    private Country country;

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "competitions", allowSetters = true)
    private Sport sport;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public Competition competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public byte[] getCompetitionLogo() {
        return competitionLogo;
    }

    public Competition competitionLogo(byte[] competitionLogo) {
        this.competitionLogo = competitionLogo;
        return this;
    }

    public void setCompetitionLogo(byte[] competitionLogo) {
        this.competitionLogo = competitionLogo;
    }

    public String getCompetitionLogoContentType() {
        return competitionLogoContentType;
    }

    public Competition competitionLogoContentType(String competitionLogoContentType) {
        this.competitionLogoContentType = competitionLogoContentType;
        return this;
    }

    public void setCompetitionLogoContentType(String competitionLogoContentType) {
        this.competitionLogoContentType = competitionLogoContentType;
    }

    public Boolean isIsCup() {
        return isCup;
    }

    public Competition isCup(Boolean isCup) {
        this.isCup = isCup;
        return this;
    }

    public void setIsCup(Boolean isCup) {
        this.isCup = isCup;
    }

    public Boolean isActive() {
        return active;
    }

    public Competition active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public TypeCompetition getType() {
        return type;
    }

    public Competition type(TypeCompetition type) {
        this.type = type;
        return this;
    }

    public void setType(TypeCompetition type) {
        this.type = type;
    }

    public Integer getSportscribeId() {
        return sportscribeId;
    }

    public Competition sportscribeId(Integer sportscribeId) {
        this.sportscribeId = sportscribeId;
        return this;
    }

    public void setSportscribeId(Integer sportscribeId) {
        this.sportscribeId = sportscribeId;
    }

    public Country getCountry() {
        return country;
    }

    public Competition country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Competition teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Competition addTeam(Team team) {
        this.teams.add(team);
        team.setCompetition(this);
        return this;
    }

    public Competition removeTeam(Team team) {
        this.teams.remove(team);
        team.setCompetition(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Sport getSport() {
        return sport;
    }

    public Competition sport(Sport sport) {
        this.sport = sport;
        return this;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", competitionName='" + getCompetitionName() + "'" +
            ", competitionLogo='" + getCompetitionLogo() + "'" +
            ", competitionLogoContentType='" + getCompetitionLogoContentType() + "'" +
            ", isCup='" + isIsCup() + "'" +
            ", active='" + isActive() + "'" +
            ", type='" + getType() + "'" +
            ", sportscribeId=" + getSportscribeId() +
            "}";
    }
}

package com.betpreview.betmanage.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Sport entity.
 */
@ApiModel(description = "The Sport entity.")
@Entity
@Table(name = "sport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sport")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sport_name", nullable = false)
    private String sportName;

    @OneToMany(mappedBy = "sport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Competition> competitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public Sport sportName(String sportName) {
        this.sportName = sportName;
        return this;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public Sport competitions(Set<Competition> competitions) {
        this.competitions = competitions;
        return this;
    }

    public Sport addCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.setSport(this);
        return this;
    }

    public Sport removeCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.setSport(null);
        return this;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sport)) {
            return false;
        }
        return id != null && id.equals(((Sport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sport{" +
            "id=" + getId() +
            ", sportName='" + getSportName() + "'" +
            "}";
    }
}

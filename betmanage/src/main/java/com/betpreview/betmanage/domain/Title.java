package com.betpreview.betmanage.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Title.
 */
@Entity
@Table(name = "title")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "title")
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_text")
    private String titleText;

    @ManyToOne
    @JsonIgnoreProperties(value = "titles", allowSetters = true)
    private MatchPreview matchPreview;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleText() {
        return titleText;
    }

    public Title titleText(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public MatchPreview getMatchPreview() {
        return matchPreview;
    }

    public Title matchPreview(MatchPreview matchPreview) {
        this.matchPreview = matchPreview;
        return this;
    }

    public void setMatchPreview(MatchPreview matchPreview) {
        this.matchPreview = matchPreview;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Title)) {
            return false;
        }
        return id != null && id.equals(((Title) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Title{" +
            "id=" + getId() +
            ", titleText='" + getTitleText() + "'" +
            "}";
    }
}

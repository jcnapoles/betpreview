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
 * A Paragraphs.
 */
@Entity
@Table(name = "paragraphs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paragraphs")
public class Paragraphs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = "paragraphs", allowSetters = true)
    private MatchPreview matchPreview;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Paragraphs content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MatchPreview getMatchPreview() {
        return matchPreview;
    }

    public Paragraphs matchPreview(MatchPreview matchPreview) {
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
        if (!(o instanceof Paragraphs)) {
            return false;
        }
        return id != null && id.equals(((Paragraphs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paragraphs{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}

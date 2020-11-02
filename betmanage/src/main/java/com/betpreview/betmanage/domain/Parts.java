package com.betpreview.betmanage.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parts.
 */
@Entity
@Table(name = "parts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "parts")
public class Parts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intro")
    private String intro;

    @Column(name = "weather")
    private String weather;

    @Column(name = "home_last_result")
    private String homeLastResult;

    @Column(name = "visitor_last_result")
    private String visitorLastResult;

    @Column(name = "home_scorers")
    private String homeScorers;

    @Column(name = "visitor_scorers")
    private String visitorScorers;

    @Column(name = "last_meeting_result")
    private String lastMeetingResult;

    @Column(name = "last_meeting_scoring")
    private String lastMeetingScoring;

    @Column(name = "home_sidelined")
    private String homeSidelined;

    @Column(name = "visitor_sidelined")
    private String visitorSidelined;

    @OneToOne
    @JoinColumn(unique = true)
    private MatchPreview matchPreview;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public Parts intro(String intro) {
        this.intro = intro;
        return this;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWeather() {
        return weather;
    }

    public Parts weather(String weather) {
        this.weather = weather;
        return this;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHomeLastResult() {
        return homeLastResult;
    }

    public Parts homeLastResult(String homeLastResult) {
        this.homeLastResult = homeLastResult;
        return this;
    }

    public void setHomeLastResult(String homeLastResult) {
        this.homeLastResult = homeLastResult;
    }

    public String getVisitorLastResult() {
        return visitorLastResult;
    }

    public Parts visitorLastResult(String visitorLastResult) {
        this.visitorLastResult = visitorLastResult;
        return this;
    }

    public void setVisitorLastResult(String visitorLastResult) {
        this.visitorLastResult = visitorLastResult;
    }

    public String getHomeScorers() {
        return homeScorers;
    }

    public Parts homeScorers(String homeScorers) {
        this.homeScorers = homeScorers;
        return this;
    }

    public void setHomeScorers(String homeScorers) {
        this.homeScorers = homeScorers;
    }

    public String getVisitorScorers() {
        return visitorScorers;
    }

    public Parts visitorScorers(String visitorScorers) {
        this.visitorScorers = visitorScorers;
        return this;
    }

    public void setVisitorScorers(String visitorScorers) {
        this.visitorScorers = visitorScorers;
    }

    public String getLastMeetingResult() {
        return lastMeetingResult;
    }

    public Parts lastMeetingResult(String lastMeetingResult) {
        this.lastMeetingResult = lastMeetingResult;
        return this;
    }

    public void setLastMeetingResult(String lastMeetingResult) {
        this.lastMeetingResult = lastMeetingResult;
    }

    public String getLastMeetingScoring() {
        return lastMeetingScoring;
    }

    public Parts lastMeetingScoring(String lastMeetingScoring) {
        this.lastMeetingScoring = lastMeetingScoring;
        return this;
    }

    public void setLastMeetingScoring(String lastMeetingScoring) {
        this.lastMeetingScoring = lastMeetingScoring;
    }

    public String getHomeSidelined() {
        return homeSidelined;
    }

    public Parts homeSidelined(String homeSidelined) {
        this.homeSidelined = homeSidelined;
        return this;
    }

    public void setHomeSidelined(String homeSidelined) {
        this.homeSidelined = homeSidelined;
    }

    public String getVisitorSidelined() {
        return visitorSidelined;
    }

    public Parts visitorSidelined(String visitorSidelined) {
        this.visitorSidelined = visitorSidelined;
        return this;
    }

    public void setVisitorSidelined(String visitorSidelined) {
        this.visitorSidelined = visitorSidelined;
    }

    public MatchPreview getMatchPreview() {
        return matchPreview;
    }

    public Parts matchPreview(MatchPreview matchPreview) {
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
        if (!(o instanceof Parts)) {
            return false;
        }
        return id != null && id.equals(((Parts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parts{" +
            "id=" + getId() +
            ", intro='" + getIntro() + "'" +
            ", weather='" + getWeather() + "'" +
            ", homeLastResult='" + getHomeLastResult() + "'" +
            ", visitorLastResult='" + getVisitorLastResult() + "'" +
            ", homeScorers='" + getHomeScorers() + "'" +
            ", visitorScorers='" + getVisitorScorers() + "'" +
            ", lastMeetingResult='" + getLastMeetingResult() + "'" +
            ", lastMeetingScoring='" + getLastMeetingScoring() + "'" +
            ", homeSidelined='" + getHomeSidelined() + "'" +
            ", visitorSidelined='" + getVisitorSidelined() + "'" +
            "}";
    }
}

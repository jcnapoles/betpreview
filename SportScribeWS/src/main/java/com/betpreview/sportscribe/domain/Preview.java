package com.betpreview.sportscribe.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Preview {
	
	private String blurb_full;
	
	private String[]  blurb_split; 
	
	private Integer fixture_id;
	
	private Integer hometeam_id;
	
	private String hometeam_name;
	
	private Integer visitorteam_id;
	
	private String visitorteam_name;
	
	private Integer league_id;
	
	private String league;
	
	private String country;
	
	private String formation_img;
	
	private String fixture_img;
	
	private String[] quick_items;
	
	private String start_utc_timestamp;
	
	private String venue_name;
	
	private String venue_city;
	
	private String match_img;
	
	private String match_img_txt;
	
	private String headline;
	
	private String date;
	
	private Object social;
	
	private String language;
	
	private PartPreview parts; 

	public String getBlurb_full() {
		return blurb_full;
	}

	public void setBlurb_full(String blurb_full) {
		this.blurb_full = blurb_full;
	}

	
	public Integer getFixture_id() {
		return fixture_id;
	}

	public void setFixture_id(Integer fixture_id) {
		this.fixture_id = fixture_id;
	}

	public Integer getHometeam_id() {
		return hometeam_id;
	}

	public void setHometeam_id(Integer hometeam_id) {
		this.hometeam_id = hometeam_id;
	}

	public String getHometeam_name() {
		return hometeam_name;
	}

	public void setHometeam_name(String hometeam_name) {
		this.hometeam_name = hometeam_name;
	}

	public Integer getVisitorteam_id() {
		return visitorteam_id;
	}

	public void setVisitorteam_id(Integer visitorteam_id) {
		this.visitorteam_id = visitorteam_id;
	}

	public String getVisitorteam_name() {
		return visitorteam_name;
	}

	public void setVisitorteam_name(String visitorteam_name) {
		this.visitorteam_name = visitorteam_name;
	}

	public Integer getLeague_id() {
		return league_id;
	}

	public void setLeague_id(Integer league_id) {
		this.league_id = league_id;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFormation_img() {
		return formation_img;
	}

	public void setFormation_img(String formation_img) {
		this.formation_img = formation_img;
	}

	public String getFixture_img() {
		return fixture_img;
	}

	public void setFixture_img(String fixture_img) {
		this.fixture_img = fixture_img;
	}
	
	
	public String[] getBlurb_split() {
		return blurb_split;
	}

	public void setBlurb_split(String[] blurb_split) {
		this.blurb_split = blurb_split;
	}

	public String[] getQuick_items() {
		return quick_items;
	}

	public void setQuick_items(String[] quick_items) {
		this.quick_items = quick_items;
	}

	public String getStart_utc_timestamp() {
		return start_utc_timestamp;
	}

	public void setStart_utc_timestamp(String start_utc_timestamp) {
		this.start_utc_timestamp = start_utc_timestamp;
	}

	public String getVenue_name() {
		return venue_name;
	}

	public void setVenue_name(String venue_name) {
		this.venue_name = venue_name;
	}

	public String getVenue_city() {
		return venue_city;
	}

	public void setVenue_city(String venue_city) {
		this.venue_city = venue_city;
	}

	public String getMatch_img() {
		return match_img;
	}

	public void setMatch_img(String match_img) {
		this.match_img = match_img;
	}

	public String getMatch_img_txt() {
		return match_img_txt;
	}

	public void setMatch_img_txt(String match_img_txt) {
		this.match_img_txt = match_img_txt;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Object getSocial() {
		return social;
	}

	public void setSocial(Object social) {
		this.social = social;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public PartPreview getParts() {
		return parts;
	}

	public void setParts(PartPreview parts) {
		this.parts = parts;
	}

	@Override
	public String toString() {
		return "Preview [blurb_full=" + blurb_full + ", blurb_split=" + Arrays.toString(blurb_split) + ", fixture_id="
				+ fixture_id + ", hometeam_id=" + hometeam_id + ", hometeam_name=" + hometeam_name + ", visitorteam_id="
				+ visitorteam_id + ", visitorteam_name=" + visitorteam_name + ", league_id=" + league_id + ", league="
				+ league + ", country=" + country + ", formation_img=" + formation_img + ", fixture_img=" + fixture_img
				+ ", quick_items=" + Arrays.toString(quick_items) + ", start_utc_timestamp=" + start_utc_timestamp
				+ ", venue_name=" + venue_name + ", venue_city=" + venue_city + ", match_img=" + match_img
				+ ", match_img_txt=" + match_img_txt + ", headline=" + headline + ", date=" + date + ", social="
				+ social + ", language=" + language + ", parts=" + parts + "]";
	}

	
	
}

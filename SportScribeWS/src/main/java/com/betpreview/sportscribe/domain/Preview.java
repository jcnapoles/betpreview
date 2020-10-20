package com.betpreview.sportscribe.domain;

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

	@Override
	public String toString() {
		return "Preview [blurb_full=" + blurb_full + ", blurb_split=" + blurb_split + ", fixture_id=" + fixture_id
				+ ", hometeam_id=" + hometeam_id + ", hometeam_name=" + hometeam_name + ", visitorteam_id="
				+ visitorteam_id + ", visitorteam_name=" + visitorteam_name + ", league_id=" + league_id + ", league="
				+ league + ", country=" + country + ", formation_img=" + formation_img + ", fixture_img=" + fixture_img
				+ ", quick_items=" + quick_items + "]";
	}
	
	
}

package com.betpreview.betmanage.service.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.betpreview.betmanage.domain.enumeration.LanguageEnum;

public class MatchPreviewDTO {
	
	private Long fixture_id;  
    
    private Long hometeam_id;
    
    private String hometeam_name;
    
    private Long visitorteam_id;
    
    private String visitorteam_name;
    
    private Integer league_id;
    
    private String league;
     
    private String fixture_img;
       
    private String formation_img;   
   
    private String start_utc_timestamp;
    
    private String venue_name;
    
    private String venue_city;
        
    private String match_img;
    
    private String match_img_txt;
    
    private String headline;    
    
    private String date;

    @Enumerated(EnumType.STRING)    
    private LanguageEnum language;   
    
    private String country;
    
    private String parts_intro;
    
    private String parts_weather;
    
    private String parts_home_lastResult;
    
    private String parts_visitor_lastResult;
    
    private String parts_home_scorers;
    
    private String parts_visitor_scorers;
    
    private String parts_last_meeting_result;
    
    private String parts_last_meeting_scoring;
    
    private String parts_home_sidelined;
    
    private String parts_visitor_sidelined;
    
    private String[] quick_items;
    
    private String hometeam_logo;
    
    private String visitorteam_logo;
    
    private String competition_logo;
    
    private String blurb_full;
    
    private String[] blurb_split;

	public Long getFixture_id() {
		return fixture_id;
	}

	public void setFixture_id(Long fixture_id) {
		this.fixture_id = fixture_id;
	}

	public Long getHometeam_id() {
		return hometeam_id;
	}

	public void setHometeam_id(Long hometeam_id) {
		this.hometeam_id = hometeam_id;
	}

	public String getHometeam_name() {
		return hometeam_name;
	}

	public void setHometeam_name(String hometeam_name) {
		this.hometeam_name = hometeam_name;
	}

	public Long getVisitorteam_id() {
		return visitorteam_id;
	}

	public void setVisitorteam_id(Long visitorteam_id) {
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

	public String getHeadline() {
		return headline;
	}	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}	

	public LanguageEnum getLanguage() {
		return language;
	}

	public void setLanguage(LanguageEnum language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getParts_intro() {
		return parts_intro;
	}

	public void setParts_intro(String parts_intro) {
		this.parts_intro = parts_intro;
	}

	public String getParts_weather() {
		return parts_weather;
	}

	public void setParts_weather(String parts_weather) {
		this.parts_weather = parts_weather;
	}

	public String getParts_home_lastResult() {
		return parts_home_lastResult;
	}

	public void setParts_home_lastResult(String parts_home_lastResult) {
		this.parts_home_lastResult = parts_home_lastResult;
	}

	public String getParts_visitor_lastResult() {
		return parts_visitor_lastResult;
	}

	public void setParts_visitor_lastResult(String parts_visitor_lastResult) {
		this.parts_visitor_lastResult = parts_visitor_lastResult;
	}

	public String getParts_home_scorers() {
		return parts_home_scorers;
	}

	public void setParts_home_scorers(String parts_home_scorers) {
		this.parts_home_scorers = parts_home_scorers;
	}

	public String getParts_visitor_scorers() {
		return parts_visitor_scorers;
	}

	public void setParts_visitor_scorers(String parts_visitor_scorers) {
		this.parts_visitor_scorers = parts_visitor_scorers;
	}

	public String getParts_last_meeting_result() {
		return parts_last_meeting_result;
	}

	public void setParts_last_meeting_result(String parts_last_meeting_result) {
		this.parts_last_meeting_result = parts_last_meeting_result;
	}

	public String getParts_last_meeting_scoring() {
		return parts_last_meeting_scoring;
	}

	public void setParts_last_meeting_scoring(String parts_last_meeting_scoring) {
		this.parts_last_meeting_scoring = parts_last_meeting_scoring;
	}	

	public String getMatch_img_txt() {
		return match_img_txt;
	}

	public void setMatch_img_txt(String match_img_txt) {
		this.match_img_txt = match_img_txt;
	}

	public String getParts_home_sidelined() {
		return parts_home_sidelined;
	}

	public void setParts_home_sidelined(String parts_home_sidelined) {
		this.parts_home_sidelined = parts_home_sidelined;
	}

	public String getParts_visitor_sidelined() {
		return parts_visitor_sidelined;
	}

	public void setParts_visitor_sidelined(String parts_visitor_sidelined) {
		this.parts_visitor_sidelined = parts_visitor_sidelined;
	}

	public String[] getQuick_items() {
		return quick_items;
	}

	public void setQuick_items(String[] quick_items) {
		this.quick_items = quick_items;
	}	

	public String getFixture_img() {
		return fixture_img;
	}

	public void setFixture_img(String fixture_img) {
		this.fixture_img = fixture_img;
	}

	public String getFormation_img() {
		return formation_img;
	}

	public void setFormation_img(String formation_img) {
		this.formation_img = formation_img;
	}

	public String getMatch_img() {
		return match_img;
	}

	public void setMatch_img(String match_img) {
		this.match_img = match_img;
	}

	public String getHometeam_logo() {
		return hometeam_logo;
	}

	public void setHometeam_logo(String hometeam_logo) {
		this.hometeam_logo = hometeam_logo;
	}

	public String getVisitorteam_logo() {
		return visitorteam_logo;
	}

	public void setVisitorteam_logo(String visitorteam_logo) {
		this.visitorteam_logo = visitorteam_logo;
	}

	public String getCompetition_logo() {
		return competition_logo;
	}

	public void setCompetition_logo(String competition_logo) {
		this.competition_logo = competition_logo;
	}

	public String getBlurb_full() {
		return blurb_full;
	}

	public void setBlurb_full(String blurb_full) {
		this.blurb_full = blurb_full;
	}

	public String[] getBlurb_split() {
		return blurb_split;
	}

	public void setBlurb_split(String[] blurb_split) {
		this.blurb_split = blurb_split;
	}
	

}

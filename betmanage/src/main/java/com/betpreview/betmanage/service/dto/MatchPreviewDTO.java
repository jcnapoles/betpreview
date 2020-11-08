package com.betpreview.betmanage.service.dto;

import java.io.File;

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
     
    private File fixture_img;
       
    private File formation_img;   
   
    private String start_utc_timestamp;
    
    private String venue_name;
    
    private String venue_city;
        
    private File match_img;
    
    private String match_ima_txt;
    
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
    
    private String parts_home_sidelinded;
    
    private String parts_vivistor_sidelined;
    
    private String[] quick_items;
    
    private File hometeam_logo;
    
    private File visitorteam_logo;
    
    private File competition_logo;
    
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

	public String getMatch_ima_txt() {
		return match_ima_txt;
	}

	public void setMatch_ima_txt(String match_ima_txt) {
		this.match_ima_txt = match_ima_txt;
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

	public String getParts_home_sidelinded() {
		return parts_home_sidelinded;
	}

	public void setParts_home_sidelinded(String parts_home_sidelinded) {
		this.parts_home_sidelinded = parts_home_sidelinded;
	}

	public String getParts_vivistor_sidelined() {
		return parts_vivistor_sidelined;
	}

	public void setParts_vivistor_sidelined(String parts_vivistor_sidelined) {
		this.parts_vivistor_sidelined = parts_vivistor_sidelined;
	}

	public String[] getQuick_items() {
		return quick_items;
	}

	public void setQuick_items(String[] quick_items) {
		this.quick_items = quick_items;
	}

	public File getFixture_img() {
		return fixture_img;
	}

	public void setFixture_img(File fixture_img) {
		this.fixture_img = fixture_img;
	}

	public File getFormation_img() {
		return formation_img;
	}

	public void setFormation_img(File formation_img) {
		this.formation_img = formation_img;
	}

	public File getMatch_img() {
		return match_img;
	}

	public void setMatch_img(File match_img) {
		this.match_img = match_img;
	}

	public File getHometeam_logo() {
		return hometeam_logo;
	}

	public void setHometeam_logo(File hometeam_logo) {
		this.hometeam_logo = hometeam_logo;
	}

	public File getVisitorteam_logo() {
		return visitorteam_logo;
	}

	public void setVisitorteam_logo(File visitorteam_logo) {
		this.visitorteam_logo = visitorteam_logo;
	}

	public File getCompetition_logo() {
		return competition_logo;
	}

	public void setCompetition_logo(File competition_logo) {
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

package com.betpreview.betmanage.service.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Parts;

@Mapper
public interface MatchPreviewMapper {
	
	MatchPreviewMapper INSTANCE = Mappers.getMapper( MatchPreviewMapper.class );
	
	@Mapping( source = "matchPreview.id", target = "fixture_id")
	@Mapping( source = "matchPreview.homeTeam.id", target = "hometeam_id")
	@Mapping( source = "matchPreview.homeTeam.teamName", target = "hometeam_name")
	@Mapping( source = "matchPreview.visitorTeam.id", target = "visitorteam_id")
	@Mapping( source = "matchPreview.visitorTeam.teamName", target = "visitorteam_name")
	@Mapping( source = "matchPreview.competition.id", target = "league_id")
	@Mapping( source = "matchPreview.competition.competitionName", target = "league")	
	@Mapping( source = "matchPreview.venueName", target = "venue_name")
	@Mapping( source = "matchPreview.venueCity", target = "venue_city")	
	@Mapping( source = "matchPreview.matchImgTxt", target = "match_img_txt")
	//@Mapping( source = "matchPreview.id", target = "headline")
	@Mapping( source = "date", target = "date")
	//@Mapping( source = "matchPreview.id", target = "language")
	@Mapping( source = "matchPreview.country.countryName", target = "country")
	@Mapping( source = "parts.intro", target = "parts_intro")
	@Mapping( source = "parts.weather", target = "parts_weather")
	@Mapping( source = "parts.homeLastResult", target = "parts_home_lastResult")
	@Mapping( source = "parts.visitorLastResult", target = "parts_visitor_lastResult")
	@Mapping( source = "parts.homeScorers", target = "parts_home_scorers")
	@Mapping( source = "parts.visitorScorers", target = "parts_visitor_scorers")
	@Mapping( source = "parts.lastMeetingResult", target = "parts_last_meeting_result")
	@Mapping( source = "parts.lastMeetingScoring", target = "parts_last_meeting_scoring")
	@Mapping( source = "parts.homeSidelined", target = "parts_home_sidelined")
	@Mapping( source = "parts.visitorSidelined", target = "parts_visitor_sidelined")
	@Mapping( source = "titles", target = "quick_items")
	//@Mapping( source = "title", target = "title")	
	@Mapping( source = "matchPreview.blurbFull", target = "blurb_full")
	
	MatchPreviewDTO createMatchPreviewDTO(MatchPreview matchPreview, Parts  parts, String[] titles, String[] blurb_split, String date, String start_utc_timestamp, String match_img, String fixture_img, String formation_img, String hometeam_logo, String visitorteam_logo, String competition_logo);
    
    
}
package com.betpreview.betmanage.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.domain.Country;
import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.enumeration.TypeCompetition;
import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.service.CountryService;
import com.betpreview.betmanage.service.MatchPreviewService;
import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.service.TeamService;
import com.betpreview.sportcribe.SportScribeWsApplication;
import com.betpreview.sportscribe.domain.League;
import com.betpreview.sportscribe.domain.Preview;

public class SportScribeAPI {
	
	private  String url;
	
	private String keyName;
	
	private  String keyValue;
	
	private String language;	
	
	private CompetitionService competitionService;
	
	private SportService sportService;
	
	private CountryService countryService;
	
	private TeamService teamService;
	
	private MatchPreviewService matchPreviewService;
	
	
	public SportScribeAPI(String url, String keyName, String keyValue, String language, CompetitionService competitionService, SportService sportService, CountryService countryService, TeamService teamService, MatchPreviewService matchPreviewService) {
		super();
		this.url = url;
		this.keyName = keyName;
		this.keyValue = keyValue;		
		this.language = language;
		this.competitionService = competitionService;
		this.sportService = sportService;
		this.countryService = countryService;
		this.teamService = teamService;
		this.matchPreviewService = matchPreviewService;
	}



	public List<Competition> getAllCompetition(){		
		List<Competition> competitionList = new ArrayList<Competition>();
		String method = "leagues";
		String parameter = null;		
		try {
			SportScribeWsApplication.execute(url, keyName, keyValue, method, parameter, language);
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}	
		List<League> leagues = SportScribeWsApplication.getLeagues();
		String sportName = "Football";
		for (League league : leagues) {
			Integer sportscribeId = league.getId();
			Optional<Competition> competitionOptional = competitionService.findOneBySportscribeId(sportscribeId);
			Competition competition = new Competition();
			if (!competitionOptional.isEmpty()) {				
				competition = competitionOptional.get();
			}
			
			competition.setActive(league.getActive());
			byte[] competitionLogo = null;
			if (league.getLogo() != null) {
				competitionLogo = urlStringToByte(league.getLogo());
			}			
			competition.setCompetitionLogo(competitionLogo);
			//competition.setCompetitionLogoContentType(league.);
			competition.setCompetitionName(league.getName());			
			competition.setIsCup(league.getIs_cup());
			competition.setSportscribeId(sportscribeId);
			competition.setType(TypeCompetition.valueOf(league.getType().toUpperCase()));
			
			/*Create or Load Sport*/
			Optional<Sport> sportOptional = sportService.findOneBySportName(sportName);
			Sport sport = new Sport();
			if (!sportOptional.isEmpty()) {
				sport = sportOptional.get();				
			} 
			sport.setSportName(sportName);
			sportService.save(sport);
			competition.setSport(sport);
			
			/*Create or Load Country*/
			Optional<Country> countryOptional = countryService.findOneByCountryName(league.getCountry());
			Country country = new Country();
			if (!countryOptional.isEmpty()) {
				country = countryOptional.get();				
			} 
			country.setCountryName(league.getCountry());
			countryService.save(country);
			competition.setCountry(country);
			
			Set<Team> teams = new HashSet<>(getTeamsByLeagueId(competition.getSportscribeId()));
			competition.setTeams(teams);
			
			competitionService.save(competition);
			competitionList.add(competition);
		}
		
		
		return competitionList;
	}
	
	public List<Team> getTeamsByLeagueId(Integer leagueId){
		List<Team> teamList = new ArrayList<Team>();
		String method = "teams";
		String parameter = leagueId.toString();
		try {
			SportScribeWsApplication.execute(url, keyName, keyValue, method, parameter, language);
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
		List<com.betpreview.sportscribe.domain.Team> teamListSportscribe = SportScribeWsApplication.getTeams();
		for (com.betpreview.sportscribe.domain.Team teamSportscribe : teamListSportscribe) {
			Integer sportscribeId = teamSportscribe.getId();
			Optional<Team> teamOptional = teamService.findOneBySportscribeId(sportscribeId);
			Team team = new Team();
			if (!teamOptional.isEmpty()) {
				team = teamOptional.get();
			}			
			team.setTeamId(sportscribeId);
			team.setTeamName(teamSportscribe.getName());
			team.setShortCode(teamSportscribe.getShort_code());
			team.setIsNationalTeam(teamSportscribe.getIs_national_team());
			byte[] teamLogo = null;
			if (teamSportscribe.getLogo() != null) {
				teamLogo = urlStringToByte(teamSportscribe.getLogo());
			}			
			team.setTeamLogo(teamLogo);
			
			/*Create or Load Country*/
			Optional<Country> countryOptional = countryService.findOneByCountryName(teamSportscribe.getCountry());
			Country country = new Country();
			if (!countryOptional.isEmpty()) {
				country = countryOptional.get();				
			} 
			country.setCountryName(teamSportscribe.getCountry());
			countryService.save(country);
			team.setCountry(country);	
			
			teamService.save(team);
			teamList.add(team);
		}
		return teamList;
	}
	
	public MatchPreview getMatchPreviewByTeamId(Integer teamId) {
		MatchPreview matchPreview = new MatchPreview();
		String method = "matchPreview";
		String parameter = teamId.toString();
		try {
			SportScribeWsApplication.execute(url, keyName, keyValue, method, parameter, language);
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
		Preview preview = SportScribeWsApplication.getPreview();
		Integer fixture_id = preview.getFixture_id();
		Optional<MatchPreview> matchPreviewOptional = matchPreviewService.findOneByFixtureId(fixture_id);
		if (!matchPreviewOptional.isEmpty()) {
			matchPreview = matchPreviewOptional.get();
		}
		matchPreview.setBlurbFull(preview.getBlurb_full());
		matchPreview.setFixtureId(fixture_id);
		matchPreview.setHometeamId(preview.getHometeam_id());
		matchPreview.setHometeamName(preview.getHometeam_name());
		matchPreview.setVisitorteamId(preview.getVisitorteam_id());
		matchPreview.setVisitorteamName(preview.getVisitorteam_name());
		matchPreview.setLeague(preview.getLeague());
		matchPreview.setLeagueId(preview.getLeague_id());
		
		/*Create or Load Country*/
		Optional<Country> countryOptional = countryService.findOneByCountryName(preview.getCountry());
		Country country = new Country();
		if (!countryOptional.isEmpty()) {
			country = countryOptional.get();				
		} 
		country.setCountryName(preview.getCountry());
		countryService.save(country);
		matchPreview.setCountry(country);
		
		byte[] matchPreviewLogo = null;
		if (preview.getFixture_img() != null) {
			matchPreviewLogo = urlStringToByte(preview.getFixture_img());
		}	
		matchPreview.setFixtureImg(matchPreviewLogo);
		//matchPreview.setFixtureImgContentType(preview.);
		byte[] formationImg = null;
		if (preview.getFormation_img() != null) {
			formationImg = urlStringToByte(preview.getFormation_img());
		}
		matchPreview.setFormationImg(formationImg);
		//matchPreview.setFormationImgContentType(formationImgContentType);	
		
		if (preview.getStart_utc_timestamp() != null) {
			try {
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    Date parsedDate = dateFormat.parse(preview.getStart_utc_timestamp());
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			    matchPreview.setStartUtcTimestamp(timestamp.toInstant());
			} catch(Exception e) { //this generic but you can control another types of exception
				 e.printStackTrace ();
			}
			
		}
		matchPreview.setVenueName(preview.getVenue_name());
		//matchPrevie
		
		
		
		
		return matchPreview;
	}

	public byte[] urlStringToByte(String urlString) {
		URL urlLogo = null;		
		InputStream is = null;
		byte[] imageBytes = null;
		try {
			 urlLogo = new URL(urlString);
		     is = urlLogo.openStream ();
		     imageBytes = IOUtils.toByteArray(is);
		}
		catch (IOException e) {		  
		     e.printStackTrace ();
		   
		}
		finally {
		     if (is != null) { try {
				is.close();
			} catch (IOException e) {				
				e.printStackTrace();
			} }
		}
		return imageBytes;
	}
}

package com.betpreview.betmanage.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.domain.Country;
import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.enumeration.TypeCompetition;
import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.service.CountryService;
import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.service.TeamService;
import com.betpreview.sportcribe.SportScribeWsApplication;
import com.betpreview.sportscribe.domain.League;

public class SportScribeAPI {
	
	private  String url;
	
	private String keyName;
	
	private  String keyValue;
	
	private String language;	
	
	private CompetitionService competitionService;
	
	private SportService sportService;
	
	private CountryService countryService;
	
	private TeamService teamService;
	
	
	public SportScribeAPI(String url, String keyName, String keyValue, String language, CompetitionService competitionService, SportService sportService, CountryService countryService, TeamService teamService) {
		super();
		this.url = url;
		this.keyName = keyName;
		this.keyValue = keyValue;		
		this.language = language;
		this.competitionService = competitionService;
		this.sportService = sportService;
		this.countryService = countryService;
		this.teamService = teamService;
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
			byte[] competitionLogo = urlStringToByte(league.getLogo());
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
			Team team = new Team();
			team.setTeamId(teamSportscribe.getId());
			team.setTeamName(teamSportscribe.getName());
			team.setShortCode(teamSportscribe.getShort_code());
			team.setIsNationalTeam(teamSportscribe.getIs_national_team());
			byte[] teamLogo = urlStringToByte(teamSportscribe.getLogo());
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
package com.betpreview.betmanage.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.betpreview.betmanage.domain.Competition;
import com.betpreview.betmanage.domain.Country;
import com.betpreview.betmanage.domain.MatchPreview;
import com.betpreview.betmanage.domain.Paragraphs;
import com.betpreview.betmanage.domain.Parts;
import com.betpreview.betmanage.domain.SocialMedia;
import com.betpreview.betmanage.domain.Sport;
import com.betpreview.betmanage.domain.Team;
import com.betpreview.betmanage.domain.TeamSocial;
import com.betpreview.betmanage.domain.Title;
import com.betpreview.betmanage.domain.enumeration.LanguageEnum;
import com.betpreview.betmanage.domain.enumeration.PlatformEnum;
import com.betpreview.betmanage.domain.enumeration.TypeCompetition;
import com.betpreview.betmanage.service.CompetitionService;
import com.betpreview.betmanage.service.CountryService;
import com.betpreview.betmanage.service.MatchPreviewService;
import com.betpreview.betmanage.service.ParagraphsService;
import com.betpreview.betmanage.service.PartsService;
import com.betpreview.betmanage.service.SocialMediaService;
import com.betpreview.betmanage.service.SportService;
import com.betpreview.betmanage.service.TeamService;
import com.betpreview.betmanage.service.TeamSocialService;
import com.betpreview.betmanage.service.TitleService;
import com.betpreview.sportcribe.SportScribeWsApplication;
import com.betpreview.sportscribe.domain.League;
import com.betpreview.sportscribe.domain.PartPreview;
import com.betpreview.sportscribe.domain.Preview;
import com.betpreview.sportscribe.domain.Social;

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
	
	private ParagraphsService paragraphsService;
	
	private TitleService titleService;
	
	private PartsService partsService;
	
	private SocialMediaService socialMediaService;
	
	private TeamSocialService teamSocialService;
	
	
	public SportScribeAPI(String url, String keyName, String keyValue, String language, CompetitionService competitionService, SportService sportService, CountryService countryService, TeamService teamService, MatchPreviewService matchPreviewService, ParagraphsService paragraphsService, TitleService titleService, PartsService partsService, SocialMediaService socialMediaService, TeamSocialService teamSocialService) {
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
		this.paragraphsService = paragraphsService;
		this.titleService = titleService;
		this.partsService = partsService;
		this.socialMediaService = socialMediaService;
		this.teamSocialService = teamSocialService;
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
			
			/*
			 * Optional<Competition> competitionOptional =
			 * competitionService.findOneBySportscribeId(leagueId); Competition competition
			 * = new Competition(); if (!competitionOptional.isEmpty()) { competition =
			 * competitionOptional.get(); } competition.setSportscribeId(leagueId);
			 * competitionService.save(competition); team.setCompetition(competition);
			 */
			
			
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
		
		String method = "matchPreview";
		String parameter = teamId.toString();
		try {
			SportScribeWsApplication.execute(url, keyName, keyValue, method, parameter, language);
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
		Preview preview = SportScribeWsApplication.getPreview();
		MatchPreview matchPreview = getMatchPreviewByPreview(preview);
		
		return matchPreview;
	}
	
	public List<MatchPreview> getMatchPreviewsByDate(Date date){
		List<MatchPreview> matchPreviewList = new ArrayList<MatchPreview>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String method = "date";
		String parameter = sdf.format(date);
		try {
			SportScribeWsApplication.execute(url, keyName, keyValue, method, parameter, language);
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}		
		List<Preview> previewList = SportScribeWsApplication.getPreviews();
		
		for (Preview preview : previewList) {
			MatchPreview matchPreview = new MatchPreview();
			matchPreview = getMatchPreviewByPreview(preview);			
			matchPreviewList.add(matchPreview);
		}
		
		return matchPreviewList;
	}
	
	private MatchPreview getMatchPreviewByPreview(Preview preview) {
		MatchPreview matchPreview = new MatchPreview();
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
		matchPreview.setVenueCity(preview.getVenue_city());
		
		byte[] matchLogo = null;
		if (preview.getMatch_img() != null) {
			matchLogo = urlStringToByte(preview.getMatch_img());
		}	
		matchPreview.setMatchImg(matchLogo);
		//matchPreview.setMatchImgContentType(matchImgContentType);
		matchPreview.setMatchImgTxt(preview.getMatch_img_txt());
		matchPreview.setHeadline(preview.getHeadline());
		
		if (preview.getDate() != null) {			
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parseDate = dateFormat.parse(preview.getDate());
				ZoneId defaultZoneId = ZoneId.systemDefault();
				Instant instant = parseDate.toInstant();
				LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
				matchPreview.setDate(localDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			
		}
	
		if (preview.getLanguage() != null) {
			matchPreview.setLanguage(LanguageEnum.valueOf(preview.getLanguage().toUpperCase()));
		}
		
		/*Create or Load Country*/
		Optional<Country> countryOptional = countryService.findOneByCountryName(preview.getCountry());
		Country country = new Country();
		if (!countryOptional.isEmpty()) {
			country = countryOptional.get();				
		} 
		country.setCountryName(preview.getCountry());
		countryService.save(country);
		matchPreview.setCountry(country);	
		
		/*Create or Load competition*/
		Competition competition = new Competition();
		Optional<Competition> competitionOptional = competitionService.findOneBySportscribeId(preview.getLeague_id());
		if (!competitionOptional.isEmpty()) {
			competition = competitionOptional.get();
		}
		competition.setCompetitionName(preview.getLeague());
		competitionService.save(competition);
		matchPreview.setCompetition(competition);	
			
		
		/**Create Titles*/
		if(preview.getQuick_items() != null) {
			Set<Title> titlesSet = new HashSet<Title>();
			List<String> titlesArray = Arrays.asList(preview.getQuick_items());
			for (String tit : titlesArray) {
				Title title = new Title();
				title.setTitleText(tit);
				titleService.save(title);
				titlesSet.add(title);
			}
			matchPreview.setTitles(titlesSet);
		}		
		
		
		Set<Team> teams = new HashSet<Team>();
		/**Create or Load homeTeam*/
		Team homeTeam = new Team();
		Optional<Team> homeTeamOptional = teamService.findOneBySportscribeId(preview.getHometeam_id());
		if (!homeTeamOptional.isEmpty()) {
			homeTeam = homeTeamOptional.get();
		}
		homeTeam.setTeamName(preview.getHometeam_name());		
		teamService.save(homeTeam);
		
		
		/**Create or Load visitorTeam*/
		Team visitorTeam = new Team();
		Optional<Team> visitorTeamOptional = teamService.findOneBySportscribeId(preview.getVisitorteam_id());
		if (!visitorTeamOptional.isEmpty()) {
			visitorTeam = visitorTeamOptional.get();
		}
		visitorTeam.setTeamName(preview.getVisitorteam_name());		
		teamService.save(visitorTeam);
		
		/*Create or Load Social*/
		TeamSocial teamSocial = new TeamSocial();		
		Optional<TeamSocial> teamSocialOptional = teamSocialService.findOneByMatch(preview.getFixture_id().toString());
		if (!teamSocialOptional.isEmpty()) {
			teamSocial = teamSocialOptional.get();
		}
		if (preview.getSocial() != null) {
			LinkedHashMap<String, Social[]> socialObject = preview.getSocial();	
			Boolean noVacio = false;
			List<SocialMedia> socialMediaList = new ArrayList<SocialMedia>();
			if (socialObject != null) {
				for (Map.Entry<String,Social[]> entry : socialObject.entrySet()) {
					String key = entry.getKey();
					Social[] social = entry.getValue();
					List<Social> socialList = Arrays.asList(social);
					
					for (Social socialElement : socialList) {
						noVacio = true;
						SocialMedia socialMedia = new SocialMedia();
						socialMedia.setTag(socialElement.getTag());
						socialMedia.setPlatform(PlatformEnum.valueOf(socialElement.getPlatform().name()));
						Integer tId = 0; 
						if (!key.equalsIgnoreCase("match")) {							
							tId = Integer.parseInt(key);
							Integer homeTeamId = homeTeam.getTeamId();
							if(tId.equals(homeTeamId)) {
								
								socialMedia.setTeam(homeTeam);								
							}else {
								
								socialMedia.setTeam(visitorTeam);
								
							}
							
						} else {
							socialMediaList.add(socialMedia);
							
							
							
						}						
						socialMediaService.save(socialMedia);
						
					}
					
					
				}
				
				if (noVacio) {
					teamSocial.setMatch(fixture_id.toString());	
					teamSocial.setHomeTeamId(homeTeam.getTeamId());
					teamSocial.setVisitorTeamId(visitorTeam.getTeamId());
					teamSocialService.save(teamSocial);
					for (SocialMedia socialMedia : socialMediaList) {
						socialMedia.setTeamSocial(teamSocial);
						socialMediaService.save(socialMedia);
					}
					
				}
			}
		}
		
		
		
		matchPreview.setHomeTeam(homeTeam);
		teams.add(homeTeam);		
		
		
		matchPreview.setVisitorTeam(visitorTeam);
		teams.add(visitorTeam);		
		
		
		matchPreview.setTeams(teams);		
		
		matchPreviewService.save(matchPreview);
		
		/**Create Paragraphs*/
		if(preview.getBlurb_split() != null) {			
			List<String> paragraphsArray = Arrays.asList(preview.getBlurb_split());
			for (String paragr : paragraphsArray) {
				Paragraphs paragraphs = new Paragraphs();
				paragraphs.setContent(paragr);
				paragraphs.setMatchPreview(matchPreview);
				paragraphsService.save(paragraphs);				
				
			}
			
		}
		if (teamSocial != null) {
			teamSocialOptional = teamSocialService.findOneByMatch(preview.getFixture_id().toString());
			if (!teamSocialOptional.isEmpty()) {
				teamSocial = teamSocialOptional.get();
			}
			teamSocial.setMatchPreview(matchPreview);
			teamSocial.setMatch(fixture_id.toString());	
			teamSocial.setHomeTeamId(homeTeam.getTeamId());
			teamSocial.setVisitorTeamId(visitorTeam.getTeamId());
			teamSocialService.save(teamSocial);
		}
			
		/*Create Part*/		
		PartPreview partPreview = preview.getParts();
		Parts parts = new Parts();
		Optional<Parts> partsOptional = partsService.findOneByMatchPreview(matchPreview);
		if (!partsOptional.isEmpty()) {
			parts = partsOptional.get();
		}
		
		parts.setHomeLastResult(partPreview.getHome_lastResult());
		parts.setHomeScorers(partPreview.getHome_scorers());
		parts.setHomeSidelined(partPreview.getHome_sidelined());
		parts.setIntro(partPreview.getIntro());
		parts.setLastMeetingResult(partPreview.getLast_meeting_result());
		parts.setLastMeetingScoring(partPreview.getLast_meeting_scoring());
		parts.setVisitorLastResult(partPreview.getVisitor_lastResult());
		parts.setVisitorScorers(partPreview.getVisitor_scorers());
		parts.setVisitorSidelined(partPreview.getVisitor_sidelined());
		parts.setWeather(partPreview.getWeather());		
		parts.setMatchPreview(matchPreview);
		partsService.save(parts);
		
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

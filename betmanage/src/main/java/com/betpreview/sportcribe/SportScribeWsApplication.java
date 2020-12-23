package com.betpreview.sportcribe;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.betpreview.sportscribe.domain.League;
import com.betpreview.sportscribe.domain.LeagueWrapper;
import com.betpreview.sportscribe.domain.Preview;
import com.betpreview.sportscribe.domain.Team;
import com.betpreview.sportscribe.domain.TeamWrapper;


public class SportScribeWsApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SportScribeWsApplication.class);	
	
	private  static List<League> leagues = new ArrayList<League>();
	
	private  static List<Team> teams = new ArrayList<Team>();
	
	private  static Preview preview = new Preview();
	
	private  static List<Preview> previews = new ArrayList<Preview>();

	public void main(String[] args) {		
		String SPORTSCRIBE_URL = args[0];
		String keyName = args[1];
		String key = args[2];
		String method = args[3];
		String param = args[4];
		String language = args[5];
		 
		try {
			execute(SPORTSCRIBE_URL, keyName, key, method, param, language);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void execute(String SPORTSCRIBE_URL, String keyName, String key, String method, String param, String language) throws URISyntaxException {	
		log.info("Getting data from API SpotScribe");
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.set(keyName, key);
	   
	    
	    switch (method) {
		case "leagues":
			
			RequestEntity<League> requestEntityLeagues = new RequestEntity<League>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + method));	
			ResponseEntity<LeagueWrapper> leaguesArray = restTemplate.exchange(requestEntityLeagues, LeagueWrapper.class);		    	
			leagues = Arrays.asList(leaguesArray.getBody().getLeagues());   
		   
		    break;
		case "teams":
			RequestEntity<Team> requestEntityTeam = new RequestEntity<Team>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + method + "/" + param));	
			ResponseEntity<TeamWrapper> teamsArray = restTemplate.exchange(requestEntityTeam, TeamWrapper.class);		    	
			teams = Arrays.asList(teamsArray.getBody().getTeams());
			
			break;
		case "matchPreview":
			if(!language.equalsIgnoreCase("null")) {
				RequestEntity<Preview> requestEntityPreview = new RequestEntity<Preview>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + method + "/" + param + "?lang=" + language));	
				ResponseEntity<Preview> previewData = restTemplate.exchange(requestEntityPreview, Preview.class);
				preview = previewData.getBody();
			}else {
				RequestEntity<Preview> requestEntityPreview = new RequestEntity<Preview>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + method + "/" + param));	
				ResponseEntity<Preview> previewData = restTemplate.exchange(requestEntityPreview, Preview.class);
				preview = previewData.getBody();
			}
					    	
			
			
			break;
		case "date":
			if(!language.equalsIgnoreCase("null")) {
				RequestEntity<Preview> requestEntityPreviews = new RequestEntity<Preview>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + "matchPreview" + "/" + method + "/" + param + "?lang=" + language));	
				ResponseEntity<Preview[]> previewArray = restTemplate.exchange(requestEntityPreviews, Preview[].class);		    	
				previews = Arrays.asList(previewArray.getBody());
			}else {
				RequestEntity<Preview> requestEntityPreviews = new RequestEntity<Preview>(headers, HttpMethod.GET, new URI(SPORTSCRIBE_URL + "matchPreview" + "/" + method + "/" + param));	
				ResponseEntity<Preview[]> previewArray = restTemplate.exchange(requestEntityPreviews, Preview[].class);		    	
				previews = Arrays.asList(previewArray.getBody());
			}
			
			
			break;
		case "videos":
			//implementar videos
			break;
		default:
			break;
		}
	 
	    log.info("SpotScribe API data has been got");
	}



	public static List<League> getLeagues() {
		return leagues;
	}



	public static void setLeagues(List<League> leagues) {
		SportScribeWsApplication.leagues = leagues;
	}



	public static List<Team> getTeams() {
		return teams;
	}



	public static void setTeams(List<Team> teams) {
		SportScribeWsApplication.teams = teams;
	}



	public static Preview getPreview() {
		return preview;
	}



	public static void setPreview(Preview preview) {
		SportScribeWsApplication.preview = preview;
	}



	public static List<Preview> getPreviews() {
		return previews;
	}



	public static void setPreviews(List<Preview> previews) {
		SportScribeWsApplication.previews = previews;
	}

	

}

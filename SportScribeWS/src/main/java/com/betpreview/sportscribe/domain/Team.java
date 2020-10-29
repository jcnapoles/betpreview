package com.betpreview.sportscribe.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
	
	private Integer id;
	
	private String name;
	
	private String short_code;
	
	private List<SocialPlatform> social_media;
	
	private String logo;
	
	private Boolean is_national_team;
	
	private String country;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShort_code() {
		return short_code;
	}

	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}

	public List<SocialPlatform> getSocial_media() {
		return social_media;
	}

	public void setSocial_media(List<SocialPlatform> social_media) {
		this.social_media = social_media;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Boolean getIs_national_team() {
		return is_national_team;
	}

	public void setIs_national_team(Boolean is_national_team) {
		this.is_national_team = is_national_team;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", short_code=" + short_code + ", social_media=" + social_media
				+ ", logo=" + logo + ", is_national_team=" + is_national_team + ", country=" + country + "]";
	}

	
	
	
	
}

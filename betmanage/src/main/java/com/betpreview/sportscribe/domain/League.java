package com.betpreview.sportscribe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
	
	private Integer id;
	
	private String name;
	
	private String country;
	
	private String logo;
	
	private String type;
	
	private Boolean is_cup;
	
	private Boolean active;

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIs_cup() {
		return is_cup;
	}

	public void setIs_cup(Boolean is_cup) {
		this.is_cup = is_cup;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", country=" + country + ", logo=" + logo + ", type=" + type
				+ ", is_cup=" + is_cup + ", active=" + active + "]";
	}

	
	
	
}

package com.betpreview.sportscribe.domain;

public class PartPreview {
	
	private String intro;
	
	private String weather;
	
	private String home_lastResult;
	
	private String visitor_lastResult;
	
	private String home_scorers;
	
	private String visitor_scorers;
	
	private String last_meeting_result;
	
	private String last_meeting_scoring;
	
	private String home_sidelined;
	
	private String visitor_sidelined;

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getHome_lastResult() {
		return home_lastResult;
	}

	public void setHome_lastResult(String home_lastResult) {
		this.home_lastResult = home_lastResult;
	}

	public String getVisitor_lastResult() {
		return visitor_lastResult;
	}

	public void setVisitor_lastResult(String visitor_lastResult) {
		this.visitor_lastResult = visitor_lastResult;
	}

	public String getHome_scorers() {
		return home_scorers;
	}

	public void setHome_scorers(String home_scorers) {
		this.home_scorers = home_scorers;
	}

	public String getVisitor_scorers() {
		return visitor_scorers;
	}

	public void setVisitor_scorers(String visitor_scorers) {
		this.visitor_scorers = visitor_scorers;
	}

	public String getLast_meeting_result() {
		return last_meeting_result;
	}

	public void setLast_meeting_result(String last_meeting_result) {
		this.last_meeting_result = last_meeting_result;
	}

	public String getLast_meeting_scoring() {
		return last_meeting_scoring;
	}

	public void setLast_meeting_scoring(String last_meeting_scoring) {
		this.last_meeting_scoring = last_meeting_scoring;
	}

	public String getHome_sidelined() {
		return home_sidelined;
	}

	public void setHome_sidelined(String home_sidelined) {
		this.home_sidelined = home_sidelined;
	}

	public String getVisitor_sidelined() {
		return visitor_sidelined;
	}

	public void setVisitor_sidelined(String visitor_sidelined) {
		this.visitor_sidelined = visitor_sidelined;
	}

	@Override
	public String toString() {
		return "PartPreview [intro=" + intro + ", weather=" + weather + ", home_lastResult=" + home_lastResult
				+ ", visitor_lastResult=" + visitor_lastResult + ", home_scorers=" + home_scorers + ", visitor_scorers="
				+ visitor_scorers + ", last_meeting_result=" + last_meeting_result + ", last_meeting_scoring="
				+ last_meeting_scoring + ", home_sidelined=" + home_sidelined + ", visitor_sidelined="
				+ visitor_sidelined + "]";
	}
	
	

}

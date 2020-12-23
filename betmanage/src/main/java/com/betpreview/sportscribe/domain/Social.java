package com.betpreview.sportscribe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Social {
	
	private SocialPlatform platform;
	
	private String tag;

	public SocialPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(SocialPlatform platform) {
		this.platform = platform;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Social [platform=" + platform + ", tag=" + tag + "]";
	}
	
	
}

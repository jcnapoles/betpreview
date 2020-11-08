package com.betpreview.betmanage.service.dto;

public class BetPreviewPost {
	
	private String slug;
    
    private String title;
    
    private Integer categories;
    
    private String status;
    
    private MatchPreviewDTO fields;

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	public Integer getCategories() {
		return categories;
	}

	public void setCategories(Integer categories) {
		this.categories = categories;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MatchPreviewDTO getFields() {
		return fields;
	}

	public void setFields(MatchPreviewDTO fields) {
		this.fields = fields;
	}
    
    
    

}

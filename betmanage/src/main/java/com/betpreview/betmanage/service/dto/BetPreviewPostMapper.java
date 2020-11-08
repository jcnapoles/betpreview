package com.betpreview.betmanage.service.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BetPreviewPostMapper {
	
	BetPreviewPostMapper INSTANCE = Mappers.getMapper(BetPreviewPostMapper.class);	
	
	BetPreviewPost createBetPreviewPost(MatchPreviewDTO fields, String slug, String title, Integer categories, String status);

}

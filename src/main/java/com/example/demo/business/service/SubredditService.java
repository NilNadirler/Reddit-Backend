package com.example.demo.business.service;

import java.util.List;

import com.example.demo.model.dto.SubredditDto;

public interface SubredditService {

	public SubredditDto save(SubredditDto subredditDto);
	
	public List<SubredditDto> getAll();
	
	public SubredditDto getSubreddit(Long id);

}

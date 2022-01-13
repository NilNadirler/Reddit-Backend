package com.example.demo.business.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.service.SubredditService;
import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Subreddit;
import com.example.demo.model.dto.SubredditDto;
import com.example.demo.repository.SubredditRepository;


@Service
public class SubredditManager implements SubredditService {

	@Autowired
	SubredditRepository subredditRepository;
	
	@Override
	public SubredditDto save(SubredditDto subredditDto) {
	
		Subreddit subreddit = SecurityConfig.modelMapper().map(subredditDto, Subreddit.class);
		this.subredditRepository.save(subreddit);
		return subredditDto;
	}

	@Override
	public List<SubredditDto> getAll() {
		
		return subredditRepository.findAll().stream().map(subreddit->
	     SecurityConfig.modelMapper().map(subreddit,SubredditDto.class ))
				.collect(Collectors.toList());
	}

	@Override
	public SubredditDto getSubreddit(Long id) {
		
			return SecurityConfig.modelMapper().map(subredditRepository.getById(id),SubredditDto.class);
				
	}

}

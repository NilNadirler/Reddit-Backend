package com.example.demo.business.service;

import com.example.demo.model.Post;
import com.example.demo.model.Vote;
import com.example.demo.model.dto.VoteDto;

public interface VoteService {

	public void vote(VoteDto voteDto);
	
	public Vote mapToVote(VoteDto voteDto, Post post);
}

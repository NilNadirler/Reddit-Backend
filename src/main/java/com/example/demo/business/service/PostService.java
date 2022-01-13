package com.example.demo.business.service;

import java.util.List;

import com.example.demo.model.dto.PostRequest;
import com.example.demo.model.dto.PostResponse;

public interface PostService {

	public void save(PostRequest postRequest);
	
	public PostResponse getPost(Long id);
	
	public List<PostResponse> getAllPosts();
	
	public List<PostResponse> getPostsBySubreddit(Long subbredditId);	
	
	public List<PostResponse> getPostsByUserName(String userName);
	
	
	
	
		
	
}

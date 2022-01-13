package com.example.demo.business.manager;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.service.AuthService;
import com.example.demo.business.service.PostService;
import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Post;
import com.example.demo.model.Subreddit;
import com.example.demo.model.User;
import com.example.demo.model.dto.PostRequest;
import com.example.demo.model.dto.PostResponse;

import com.example.demo.repository.PostRepository;
import com.example.demo.repository.SubredditRepository;
import com.example.demo.repository.UserRepository;



@Service
public class PostManager implements PostService{
	
	
	PostRepository postRepository;
	
	private SubredditRepository subredditRepository;
	private UserRepository userRepository;
	private AuthService authService;

	@Autowired
	public PostManager(PostRepository postRepository, AuthService authService,
			SubredditRepository subredditRepository,UserRepository userRepository) {
		super();
		this.postRepository = postRepository;
        this.authService= authService;
		this.subredditRepository = subredditRepository;
		this.userRepository= userRepository;
	}

	@Override
	public void save(PostRequest postRequest) { //postId, subredditName, postName, url, decription {
	
		Post post = SecurityConfig.modelMapper().map(postRequest, Post.class);
	
		post.setSubreddit(this.subredditRepository.getByName(postRequest.getSubredditName()));
		post.setUser(this.authService.getCurrentUser());
		
	     this.postRepository.save(post);
	}

	@Override  //postname,url,description,userName,subredditName,voteCount,commentCount,duration,upVote,downVote
	public PostResponse getPost(Long id) {  
	
		return SecurityConfig.modelMapper().map(this.postRepository.getById(id), PostResponse.class);
		
	}

	@Override
	public List<PostResponse> getAllPosts() {
		
		return this.postRepository.findAll().stream().map(post->
			SecurityConfig.modelMapper().map(post, PostResponse.class))
				.collect(Collectors.toList());
		
	}
	//return SecurityConfig.modelMapper().map(subredditRepository.getById(id),SubredditDto.class);
	@Override
	public List<PostResponse> getPostsBySubreddit(Long subbredditId) {
		
		Subreddit subreddit = subredditRepository.getById(subbredditId);
		
		return this.postRepository.getAllBySubreddit(subreddit).stream().map(post->
			SecurityConfig.modelMapper().map(post, PostResponse.class))
				.collect(Collectors.toList());
				
	}

	@Override
	public List<PostResponse> getPostsByUserName(String userName) {
		User user= userRepository.getByUsername(userName);
		
		return this.postRepository.getByUser(user).stream().map(post->
		SecurityConfig.modelMapper().map(post, PostResponse.class))
				.collect(Collectors.toList());
	}

}

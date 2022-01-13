package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.business.service.PostService;
import com.example.demo.model.dto.PostRequest;
import com.example.demo.model.dto.PostResponse;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(maxAge = 3600)
public class PostsController {

	@Autowired
	PostService postService;
	
	@PostMapping("add")
	public ResponseEntity<?> add(@RequestBody PostRequest postRequest){
		this.postService.save(postRequest);
		return ResponseEntity.ok("İşlem başarılı");
	}
	
	@GetMapping("/getPost/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
		
		return ResponseEntity.ok(postService.getPost(id));
	
	};
	
	@GetMapping("/getAll")
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		return ResponseEntity.ok(this.postService.getAllPosts());
	};
	
	@GetMapping("/getPostbySubreddit/{id}")
    public ResponseEntity<?> getPostsBySubreddit(@PathVariable Long id){
		
		return ResponseEntity.ok(postService.getPostsBySubreddit(id));
	
	};
	
	@GetMapping("/getPostbySubreddit/{userName}")
    public ResponseEntity<?> getPostsByUserName(@PathVariable String userName){
		
		return ResponseEntity.ok(postService.getPostsByUserName(userName));
	
	};
	

	
}

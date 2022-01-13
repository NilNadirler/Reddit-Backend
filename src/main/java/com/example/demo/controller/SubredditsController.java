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

import com.example.demo.business.service.SubredditService;

import com.example.demo.model.dto.SubredditDto;

@RestController
@RequestMapping("/api/subreddits")
@CrossOrigin(maxAge = 3600)
public class SubredditsController {

	@Autowired
	SubredditService subredditService;
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody SubredditDto subredditDto){
		this.subredditService.save(subredditDto);
		return ResponseEntity.ok("İşlem başarılı");
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
		
		return ResponseEntity.ok(this.subredditService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
		return ResponseEntity.ok(this.subredditService.getSubreddit(id));
	}
	
	
}

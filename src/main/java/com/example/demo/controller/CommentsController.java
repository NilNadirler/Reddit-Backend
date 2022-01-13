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

import com.example.demo.business.service.CommentService;
import com.example.demo.model.dto.CommentDto;



@RestController
@RequestMapping("/api/comments/")

@CrossOrigin(maxAge = 3600)
public class CommentsController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("add")
	public ResponseEntity<?> add(@RequestBody CommentDto commentDto){
		this.commentService.save(commentDto);
		return ResponseEntity.ok("İşlem başarılı");
	}
	@GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok((commentService.getAllCommentForPost(postId)));
               
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.ok(commentService.getAllCommentForUser(userName));
               
    }
}

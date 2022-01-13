package com.example.demo.business.service;

import java.util.List;

import com.example.demo.model.User;
import com.example.demo.model.dto.CommentDto;

public interface CommentService {

	public void save(CommentDto commentDto);// post//mailContainerbuilder
	
	public void sendCommentNotification(String message, User user);//NotificationEmail
	
	public List<CommentDto> getAllCommentForPost(Long postId);//post
	
	public List<CommentDto> getAllCommentForUser(String userName);//user
	
	
	
	
	
}

package com.example.demo.business.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.service.AuthService;
import com.example.demo.business.service.CommentService;

import com.example.demo.business.service.MailService;
import com.example.demo.config.SecurityConfig;
import com.example.demo.model.Comment;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;

import com.example.demo.repository.UserRepository;




@Service
public class CommentManager implements CommentService {

	
	@Autowired
	
	public CommentManager(PostRepository postRepository, CommentRepository commentRepository,
			 MailService mailService, AuthService authService,UserRepository userRepository) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		
		this.mailService = mailService;
		this.authService = authService;
		this.userRepository= userRepository;
	}
	private PostRepository postRepository;
	private CommentRepository commentRepository;

	private MailService mailService;
	private AuthService authService;
	private UserRepository userRepository;


	
	@Override
	public void save(CommentDto commentDto) {
		
		Comment comment= SecurityConfig.modelMapper().map(commentDto, Comment.class);
		comment.setPost(this.postRepository.getById(commentDto.getPostId()));
		comment.setUser(this.authService.getCurrentUser());
		
		this.commentRepository.save(comment);
		
		
		//String message= MailContentBuilder.build(authService.getCurrentUser()+""+"")
	}

	@Override
	public void sendCommentNotification(String message, User user) {
	
		mailService.sendMail(new NotificationEmail(user.getUsername(),user.getEmail(),""));
		
		
	}

	@Override
	public List<CommentDto> getAllCommentForPost(Long postId) {
		
		Post post = postRepository.getById(postId);
		
		return this.commentRepository.findByPost(post).stream().map(comment->
		SecurityConfig.modelMapper().map(comment, CommentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> getAllCommentForUser(String userName) {
		
		User user= userRepository.getByUsername(userName);
		
		return this.commentRepository.findAllByUser(user).stream().map(comment->
			SecurityConfig.modelMapper().map(comment, CommentDto.class))
				.collect(Collectors.toList());
		}
		
		
	
	}



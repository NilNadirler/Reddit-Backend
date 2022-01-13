package com.example.demo.business.manager;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.service.AuthService;
import com.example.demo.business.service.VoteService;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.exceptions.SpringRedditException;
import com.example.demo.model.Post;
import com.example.demo.model.Vote;
import com.example.demo.model.VoteType;
import com.example.demo.model.dto.VoteDto;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.VoteRepository;



@Service
public class VoteManager implements VoteService{
	
	
	private VoteRepository voteRepository;
	private PostRepository postRepository;
	private AuthService authService;
	

	@Autowired
	public VoteManager(VoteRepository voteRepository, PostRepository postRepository, AuthService authService) {
		super();
		this.voteRepository = voteRepository;
		this.postRepository = postRepository;
		this.authService = authService;
	}

	@Transactional
	@Override
	public void vote(VoteDto voteDto) {
		
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(()-> new PostNotFoundException("Post not found with ID - " +voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if(voteByPostAndUser.isPresent() &&
				voteByPostAndUser.get().getVoteType()
				.equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already" + voteDto.getVoteType() + "'d for this post");
			
			if(UPVOTE.equals(voteDto.getVoteType())) {
				post.setVoteCount(post.getVoteCount() +1);
				
			}else {
				post.setVoteCount(post.getVoteCount() -1);
			}
			
			voteRepository.save(mapToVote(voteDto,post));
			postRepository.save(post);
		}

	}

	@Override
	public Vote mapToVote(VoteDto voteDto, Post post) {
		
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
	 boolean isPostUpVoted(Post post) {
	        return checkVoteType(post, UPVOTE);
	    }

	    boolean isPostDownVoted(Post post) {
	        return checkVoteType(post, DOWNVOTE);
	    }

	    private boolean checkVoteType(Post post, VoteType voteType) {
	        if (authService.isLoggedIn()) {
	            Optional<Vote> voteForPostByUser =
	                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
	                            authService.getCurrentUser());
	            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
	                    .isPresent();
	        }
	        return false;
	    }

}

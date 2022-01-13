package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Post;
import com.example.demo.model.Subreddit;
import com.example.demo.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> getAllBySubreddit(Subreddit subreddit);
	
	List<Post> getByUser(User user);
}

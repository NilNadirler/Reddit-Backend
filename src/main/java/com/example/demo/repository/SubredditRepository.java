package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Subreddit;


@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
       
	Subreddit getByName(String subredditName);
}


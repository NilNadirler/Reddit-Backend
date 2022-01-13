package com.example.demo.model;

import java.util.Arrays;

import com.example.demo.exceptions.SpringRedditException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum VoteType {
	
    UPVOTE(1), DOWNVOTE(-1),
    
    ;

   private int direction;

   

   public static VoteType lookup(Integer direction) {
	   return Arrays.stream(VoteType.values())
			   .filter(value-> value.getDirection().equals(direction))
			   .findAny()
			   .orElseThrow(()-> new SpringRedditException("Vote not found"));

   }
   
   public Integer getDirection() {
	   return direction;
   }
}

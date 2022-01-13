package com.example.demo.model.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;
}

package com.example.demo.service;

import java.util.List;
import java.util.Optional;


import com.example.demo.model.entity.MovieComment;

public interface CommentService {

	List<MovieComment> getAllComments();
	Optional<MovieComment> getCommentById(Long id);
	MovieComment updateCommentById(MovieComment movieComment);
	
}

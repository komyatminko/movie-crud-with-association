package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieCommentDao;
import com.example.demo.model.entity.MovieComment;
import com.example.demo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	MovieCommentDao commentDao;
	
	@Override
	public List<MovieComment> getAllComments() {
		List<MovieComment> comments = this.commentDao.findAll();
		return comments;
	}

	@Override
	public Optional<MovieComment> getCommentById(Long id) {
		Optional<MovieComment> rs = this.commentDao.findById(id);
		MovieComment cmt = null;
		if(rs.isPresent()) {
			cmt = rs.get();
		}
		return Optional.of(cmt);
	}

	@Override
	public MovieComment updateCommentById(MovieComment movieComment) {
		Optional<MovieComment> rs = this.commentDao.findById(movieComment.getId());
		MovieComment oldComment = null;
		if(rs.isPresent()) {
			oldComment = rs.get();
		}
		
		oldComment.setComment(movieComment.getComment());
		MovieComment updatedComment = this.commentDao.save(oldComment);
		System.out.println(updatedComment);
		return updatedComment;
	}
	
	

}

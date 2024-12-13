package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.MovieComment;

import jakarta.transaction.Transactional;


@Repository
public interface MovieCommentDao extends JpaRepository<MovieComment, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM movie_comment WHERE id = ?1", nativeQuery = true)
	public int deleteCommentById(Long id);
}

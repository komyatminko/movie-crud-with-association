package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.MovieComment;

@Repository
public interface MovieCommentDao extends JpaRepository<MovieComment, Long>{

}

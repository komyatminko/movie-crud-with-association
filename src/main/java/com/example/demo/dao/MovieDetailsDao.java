package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.MovieDetails;

@Repository
public interface MovieDetailsDao extends JpaRepository<MovieDetails, Long>{

}

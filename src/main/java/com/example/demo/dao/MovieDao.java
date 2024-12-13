package com.example.demo.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.dto.GenreCount;
import com.example.demo.model.entity.Movie;

import jakarta.transaction.Transactional;


public interface MovieDao extends JpaRepository<Movie, Long>{

	List<Movie> findByGenre(String genre);
	List<Movie> findByYearBetween(Integer startYear, Integer endYear);
	List<Movie> findByYearIn(Collection<Integer> years);
	Page<Movie> findAll(Pageable page);
	List<Movie> findByYearLessThan(Integer year, Sort sort);
	
	@Query("SELECT distinct(m.genre) FROM Movie m")
	List<String> findGenres();
	
	@Query("SELECT count(m.genre) FROM Movie m WHERE m.genre = ?1")
	List<Integer> findGenreCount(String genre);
	
	@Query("SELECT distinct(m.genre) AS genre, count(m.genre) AS count FROM Movie m GROUP BY m.genre")
	List<GenreCount> findGenreAndCount();
	
	
}

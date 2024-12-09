package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.MovieDto;

public interface MovieService {

	List<MovieDto> getAllMovies() throws Exception;
	Optional<MovieDto> getMovieById(Long id);
	MovieDto saveMovie(MovieDto movieDto);
	MovieDto updateMovie(MovieDto movieDto);
	void removeMovieById(Long id);
	
}

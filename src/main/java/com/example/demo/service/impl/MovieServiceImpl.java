package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieDao;
import com.example.demo.model.dto.MovieCommentDto;
import com.example.demo.model.dto.MovieDetailsDto;
import com.example.demo.model.dto.MovieDto;
import com.example.demo.model.entity.Movie;
import com.example.demo.model.entity.MovieComment;
import com.example.demo.model.entity.MovieDetails;
import com.example.demo.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	MovieDao movieDao;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<MovieDto> getAllMovies() throws Exception{
		// TODO Auto-generated method stub
		List<Movie> movies = this.movieDao.findAll();
		List<MovieDto> moviesDto = new ArrayList<>();
		if(movies.isEmpty()) {
			throw new Exception("Data not found!");
		} 
		else {
			
			for(Movie movie: movies) {
				 MovieDto movieDto = MovieEntityToDto(movie);
				 moviesDto.add(movieDto);	 
			}
		}
		return moviesDto;
	}

	@Override
	public Optional<MovieDto> getMovieById(Long id) {
		// TODO Auto-generated method stub
		MovieDto movieDto;
		Optional<Movie> result = this.movieDao.findById(id);
		if(result.isPresent()) {
			Movie movie = result.get();
			movieDto = MovieEntityToDto(movie);
			return Optional.of(movieDto);
		}
		else return Optional.empty();
	}

	@Override
	public MovieDto saveMovie(MovieDto movieDto) {
		
		Movie movie = this.MovieDtoToEntity(movieDto);
		
		movie = this.movieDao.save(movie);
//		
		movieDto = modelMapper.map(movie, MovieDto.class);
		return movieDto;
	}

	@Override
	public MovieDto updateMovie(MovieDto movieDto) {
		// TODO Auto-generated method stub
		movieDto = this.saveMovie(movieDto);
		return movieDto;
	}

	@Override
	public void removeMovieById(Long id) {
		this.movieDao.deleteById(id);
	}
	
	private MovieDto MovieEntityToDto(Movie movie) {
		MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
		 if(movieDto.getMovieDetails() != null) {
			 MovieDetailsDto details = modelMapper.map(movie.getMovieDetails(), MovieDetailsDto.class);
			 movieDto.setMovieDetails(details);
		 }
		return movieDto;
	}
	
	private Movie MovieDtoToEntity(MovieDto dto) {
		Movie movie = new Movie();
		movie.setTitle(dto.getTitle());
		movie.setYear(dto.getYear());
		movie.setGenre(dto.getGenre());
		
		//for update 
		MovieDto oldMovieDto = null;
		if(dto.getId() != null) {
			oldMovieDto = this.getMovieById(dto.getId()).get();
			movie.setId(dto.getId());
			movie.setCreatedAt(oldMovieDto.getCreatedAt());
		}
		
		if(dto.getMovieDetails() != null) {
			MovieDetails details = new MovieDetails();
			details.setDetails(dto.getMovieDetails().getDetails());
			
			//for update
			if(dto.getMovieDetails().getId() != null) {
				details.setId(dto.getMovieDetails().getId());
				details.setCreatedAt(oldMovieDto.getMovieDetails().getCreatedAt());
			}
			details.setMovie(movie);
			movie.setMovieDetails(details);
		}
		
		if(dto.getMovieComments() != null) {
			List<MovieComment> comments = new ArrayList<>();
			
			for(MovieCommentDto commentDto : dto.getMovieComments()) {
				MovieComment movieComment = modelMapper.map(commentDto, MovieComment.class);
				comments.add(movieComment);
				movieComment.setMovie(movie);
			}
			
			
			movie.setMovieComments(comments);
			
		}
		return movie;
	}

}

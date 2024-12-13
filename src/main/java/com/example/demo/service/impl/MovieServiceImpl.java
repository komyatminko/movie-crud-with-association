package com.example.demo.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieCommentDao;
import com.example.demo.dao.MovieDao;
import com.example.demo.model.dto.MovieCommentDto;
import com.example.demo.model.dto.MovieDetailsDto;
import com.example.demo.model.dto.MovieDto;
import com.example.demo.model.entity.Movie;
import com.example.demo.model.entity.MovieComment;
import com.example.demo.model.entity.MovieDetails;
import com.example.demo.service.MovieService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class MovieServiceImpl implements MovieService {
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	MovieDao movieDao;
	
	@Autowired
	MovieCommentDao movieCommentDao;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<MovieDto> getAllMovies() throws Exception {
		System.out.println("this is get all method");
		// TODO Auto-generated method stub
		List<Movie> movies = this.movieDao.findAll();
		List<MovieDto> moviesDto = new ArrayList<>();
		if (movies.isEmpty()) {
			throw new Exception("Data not found!");
		} else {

			for (Movie movie : movies) {
				MovieDto movieDto = this.MovieEntityToDto(movie);
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
		if (result.isPresent()) {
			Movie movie = result.get();
			movieDto = this.MovieEntityToDto(movie);
			return Optional.of(movieDto);
		} else
			return Optional.empty();
	}


	@Override
	public MovieDto saveMovie(MovieDto movieDto) {
		
		Movie movie = MovieDtoToEntity(movieDto);

		try {
			movie = this.movieDao.save(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		movieDto = modelMapper.map(movie, MovieDto.class);
		return movieDto;
	}
	

	@Modifying
	@Transactional
	@Override
	public MovieDto updateMovie(MovieDto movieDto) {
		System.out.println("this is update method");
		
		
		deleteCommentsForUpdating(movieDto);
		
		//for updating movie
		Long id = movieDto.getId();
		Movie modifiedMovie = this.movieDao.getById(id);
		System.out.println("Comments that retrieve from db after deleting comments" + modifiedMovie.getMovieComments());
		System.out.println("After remove cmt, movie that retrieve again from db " + modifiedMovie);
		System.out.println("Comment count after delete comments " + modifiedMovie.getMovieComments().size());
		
		modifiedMovie.setTitle(movieDto.getTitle());
		modifiedMovie.setGenre(movieDto.getGenre());
		modifiedMovie.setYear(movieDto.getYear());
//		modifiedMovie.setCreatedAt(modifiedMovie.getCreatedAt());
		
		updateDetailsAndComments(movieDto, modifiedMovie);
		
		
		System.out.println("have bind changed comments to the movie..." + modifiedMovie);
		MovieDto dto = null;
		
		this.movieDao.save(modifiedMovie);
		dto = this.MovieEntityToDto(modifiedMovie);
		
		return dto;
	}

	private void deleteCommentsForUpdating(MovieDto movieDto) {
		// for remove an existing comment
		Movie oldMovie = this.movieDao.getById(movieDto.getId());
		System.out.println("Movie before deleting comments " + oldMovie);
		System.out.println("Old comment count " + oldMovie.getMovieComments().size());
		System.out.println("Request comment count " + movieDto.getMovieComments().size());
		if (movieDto.getMovieComments().size() < oldMovie.getMovieComments().size()) {
			List<MovieComment> commentsToRemove = new ArrayList<>();
			
			for (MovieCommentDto commentDto : movieDto.getMovieComments()) {
				commentsToRemove = oldMovie.getMovieComments().stream()
						.filter(cmt -> cmt.getId() != commentDto.getId()).collect(Collectors.toList());
				commentsToRemove.forEach(cmt -> System.out.println("comment to remove " + cmt));
				
				

				// retrieve comment to remove foreign key
				commentsToRemove.forEach(cmt -> {
//					
								int deleteCount = this.movieCommentDao.deleteCommentById(cmt.getId());
								System.out.println("Delete movie count " + deleteCount);
								
//								Movie movie = cmt.getMovie();
//								System.out.println("Movie ... " + movie);
//							
//								movie.getMovieComments().remove(cmt);
//								movie.getMovieComments().forEach(comment-> comment.setMovie(movie));
//								System.out.println("Movie to save for remove comment " + movie);
//								this.movieDao.save(movie);
								
//								Movie m = this.movieDao.getById(movieDto.getId());
//								System.out.println("Movie after remove comment and save movie to db " + m);
								
								entityManager.flush();
								entityManager.clear();
								
				});

			}
		}
	}
	
//	@Transactional
//	@Modifying
	private void updateDetailsAndComments(MovieDto movieDto, Movie modifiedMovie) {
		updatingDetails(movieDto, modifiedMovie);
		updatingComments(movieDto, modifiedMovie);
//		updatingCommentsTwo(movieDto, modifiedMovie);
	}


	@Transactional
	@Override
	public void removeMovieById(Long id) {
		Movie movie = this.movieDao.getById(id);
		System.out.println("movie to be deleted " + movie);
		
		List<MovieComment> updatedComments = new ArrayList<>();
		for(MovieComment comment: movie.getMovieComments()) {
			
			System.out.println("deleting comment id = " + comment.getId());
			comment.setMovie(null);
			updatedComments.add(comment);
			this.movieCommentDao.delete(comment);
			
		}
		
		movie.getMovieComments().clear();
		this.movieDao.deleteById(id);;
		
	}
	
	private Movie MovieDtoToEntity(MovieDto movieDto) {
		Movie movie = modelMapper.map(movieDto, Movie.class);
			
		//for save
		if (movieDto.getMovieDetails() != null) {
			MovieDetails details = modelMapper.map(movieDto.getMovieDetails(), MovieDetails.class);
			
			movie.setMovieDetails(details);
			details.setMovie(movie);

		}

		//for save
		if (movieDto.getMovieComments() != null) {
			List<MovieComment> comments = new ArrayList<>();
			
			for (MovieCommentDto commentDto : movieDto.getMovieComments()) {
				MovieComment movieComment = modelMapper.map(commentDto, MovieComment.class);
				
				movieComment.setMovie(movie);
				comments.add(movieComment);

			}
			
			
			movie.setMovieComments(comments);
		}
		return movie;
	}

	private MovieDto MovieEntityToDto(Movie movie) {
		MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
		if (movie.getMovieDetails() != null) {
			MovieDetailsDto details = modelMapper.map(movie.getMovieDetails(), MovieDetailsDto.class);
			movieDto.setMovieDetails(details);
		}

		if (movie.getMovieComments() != null) {
			List<MovieComment> movieComments = movie.getMovieComments();
			List<MovieCommentDto> movieCommentsDto = new ArrayList<>();

			for (MovieComment movieComment : movieComments) {
				MovieCommentDto comment = modelMapper.map(movieComment, MovieCommentDto.class);
				movieCommentsDto.add(comment);
			}
			movieDto.setMovieComments(movieCommentsDto);
		}
		return movieDto;
	}
	
	private void updatingDetails(MovieDto movieDto, Movie modifiedMovie) {
		
		//if request body doesn't have details obj
		if(movieDto.getMovieDetails() == null) {
			modifiedMovie.setMovieDetails(null);
		}
		else {
			MovieDetails details = modifiedMovie.getMovieDetails();
			if(details == null) {
				details = new MovieDetails();
				modifiedMovie.setMovieDetails(details);
			}
			details.setDetails(movieDto.getMovieDetails().getDetails());
			
			details.setMovie(modifiedMovie);
			modifiedMovie.setMovieDetails(details);
		}
	}
	
//	@Transactional
//	@Modifying
	private void updatingComments(MovieDto requestMovieDto, Movie oldMovie) {
		
		System.out.println("updating comments...");
		
		if(requestMovieDto.getMovieComments() != null) {
			
			List<MovieComment> updatedComments = new ArrayList<>();
			
			for(MovieCommentDto commentDto : requestMovieDto.getMovieComments()) {
				//for new comment
				if(commentDto.getId() == null) {
					MovieComment newComment = new MovieComment();
					newComment.setComment(commentDto.getComment());
					newComment.setMovie(oldMovie);
					updatedComments.add(newComment);
				}
				//to update existing comment
				else if(commentDto.getId() != null) {
					System.out.println("updating existing comment...");
					System.out.println("incoming updated comment with ID " + commentDto.getId());
					
					Optional<MovieComment> result = this.movieCommentDao.findById(commentDto.getId());
					
					MovieComment existingComment = null;
					if(result.isPresent()) {
						existingComment = result.get();
						System.out.println("Retrieve existing comment from DB to update " + existingComment);
					}
					else {
						System.out.println("Comment not found");
					}
					existingComment.setComment(commentDto.getComment());
					existingComment.setMovie(oldMovie);
					updatedComments.add(existingComment);
				}
			}
			
			
			
			System.out.println("updated comments " + updatedComments.toString());
			oldMovie.getMovieComments().clear();
			oldMovie.getMovieComments().addAll(updatedComments);
			
			
		}
		else {
			System.out.println("Comments are null ");
		}
		
	}
	
}


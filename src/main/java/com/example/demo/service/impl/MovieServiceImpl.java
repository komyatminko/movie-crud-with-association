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

import com.example.demo.dao.ActorDao;
import com.example.demo.dao.MovieCommentDao;
import com.example.demo.dao.MovieDao;
import com.example.demo.exception.ActorNotFoundException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.dto.ActorDto;
import com.example.demo.model.dto.MovieCommentDto;
import com.example.demo.model.dto.MovieDetailsDto;
import com.example.demo.model.dto.MovieDto;
import com.example.demo.model.entity.Actor;
import com.example.demo.model.entity.Movie;
import com.example.demo.model.entity.MovieComment;
import com.example.demo.model.entity.MovieDetails;
import com.example.demo.service.MovieService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
	
	@Autowired
	ActorDao actorDao;

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
	public MovieDto saveMovie(MovieDto movieDto) throws ActorNotFoundException{
		
		Movie movie = null;
		try {
			movie = MovieDtoToEntity(movieDto);
			movie = this.movieDao.save(movie);
			movieDto = modelMapper.map(movie, MovieDto.class);
		} catch (ActorNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ActorNotFoundException("Actor not found.");
		}
		
		return movieDto;
	}
	

	@Modifying
	@Transactional
	@Override
	public MovieDto updateMovie(MovieDto movieDto) {
		System.out.println("this is update method");
		
		
		deleteCommentsForUpdating(movieDto);
		deleteActorsForUpdating(movieDto);
		
		//for updating movie
		Long id = movieDto.getId();
		Movie modifiedMovie = this.movieDao.getById(id);
		System.out.println("Comments that retrieve from db after deleting comments" + modifiedMovie.getMovieComments());
		System.out.println("After remove cmt, movie that retrieve again from db " + modifiedMovie);
		System.out.println("Comment count after delete comments " + modifiedMovie.getMovieComments().size());
		
		modifiedMovie.setTitle(movieDto.getTitle());
		modifiedMovie.setGenre(movieDto.getGenre());
		modifiedMovie.setYear(movieDto.getYear());
		
		updateDetailsAndComments(movieDto, modifiedMovie);
		updateActors(movieDto, modifiedMovie);
		
		
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
//		System.out.println("Request comment count " + movieDto.getMovieComments().size());
		if (movieDto.getMovieComments() != null && movieDto.getMovieComments().size() < oldMovie.getMovieComments().size()) {
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
		
	}


	@Transactional
	@Modifying
	@Override
	public void removeMovieById(Long id) throws MovieNotFoundException{
		try {
			Movie movie = this.movieDao.getById(id);
			System.out.println("movie to be deleted " + movie);
			
			List<MovieComment> comments = movie.getMovieComments();
			comments.forEach(cmt -> {
				
				int deleteCount = this.movieCommentDao.deleteCommentById(cmt.getId());
				System.out.println("Delete movie ID : " + cmt.getId());
				
				entityManager.flush();
				entityManager.clear();
				
			});
			Movie movieRemovedComments = this.movieDao.getById(id);
			this.movieDao.delete(movieRemovedComments);
		}
		catch(EntityNotFoundException e) {
			throw new MovieNotFoundException("Movie not found.");
		}
			
	}
	
	private Movie MovieDtoToEntity(MovieDto movieDto) throws ActorNotFoundException{
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
		
		if(movieDto.getActors() != null) {
			System.out.println("actor present.");
			
			List<Actor> actors = new ArrayList<>();
			for(ActorDto actorDto : movieDto.getActors()) {
				
				Actor actor = modelMapper.map(actorDto, Actor.class);
				
				if(actorDto.getId() != null) {
					System.out.println("Incoming actor ID : " + actorDto.getId());
					try {
						Actor existingActor = this.actorDao.getById(actorDto.getId());
						System.out.println("Actor exist and actor in movies are " + existingActor.getMovies().toString());
						
						//existing actor but movies empty list
						if(existingActor.getMovies() == null) {
							System.out.println("actor with movies empty list");
							List<Movie> movies = new ArrayList<>();
							movies.add(movie);
							actor.setMovies(movies);
						}
						//existing actor with movies list
						else {
							System.out.println("actor with movies list");
							System.out.println("movie list size " + existingActor.getMovies().size());
							existingActor.getMovies().add(movie);
							actors.add(existingActor);
						}
					}
					catch(EntityNotFoundException e) {
						throw new ActorNotFoundException("Actor not found!");
					}
				}
				else {
					System.out.println("New actor");
					List<Movie> movies = new ArrayList<>();
					movies.add(movie);
					actor.setMovies(movies);
					actors.add(actor);
				}
				
			}
			movie.setMovieActors(actors);
			
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
		
		if(movie.getMovieActors() != null) {
			System.out.println("actor count" + movie.getMovieActors().size());
			List<Actor> actors = movie.getMovieActors();
			List<ActorDto> actorsDto = new ArrayList<>();
			
			for(Actor actor : actors) {
				ActorDto actorDto = modelMapper.map(actor, ActorDto.class);
				actorsDto.add(actorDto);
			}
			movieDto.setActors(actorsDto);
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
	
	private void updateActors(MovieDto requestMovieDto, Movie oldMovie) {

		System.out.println("updating actors...");

		if (requestMovieDto.getActors() != null) {

			List<Actor> updatedActors = new ArrayList<>();

			for (ActorDto actorDto : requestMovieDto.getActors()) {
				// for new actor
				if (actorDto.getId() == null) {

					System.out.println("Updating new actor...");

					Actor newActor = new Actor();
//					newActor.setComment(actorDto.getComment());
//					newActor.setMovie(oldMovie);
					List<Movie> movies = new ArrayList<>();
					movies.add(oldMovie);

					newActor.setFirstName(actorDto.getFirstName());
					newActor.setLastName(actorDto.getLastName());
					newActor.setBirthday(actorDto.getBirthday());
					newActor.setGender(actorDto.getGender());
					newActor.setMovies(movies);
					updatedActors.add(newActor);
				}
				// to update existing comment
				else if (actorDto.getId() != null) {
					System.out.println("updating existing actor...");
					System.out.println("incoming updated actor with ID " + actorDto.getId());

					Optional<Actor> result = this.actorDao.findById(actorDto.getId());

					Actor existingActor = null;
					if (result.isPresent()) {
						existingActor = result.get();
						System.out.println("Retrieve existing actor from DB to update " + existingActor);
					} else {
						System.out.println("Comment not found");
					}
					
					existingActor.setFirstName(actorDto.getFirstName());
					existingActor.setLastName(actorDto.getLastName());
					existingActor.setBirthday(actorDto.getBirthday());
					existingActor.setGender(actorDto.getGender());
					existingActor.getMovies().add(oldMovie);
					updatedActors.add(existingActor);
				}
			}

			System.out.println("updated actors " + updatedActors.toString());
			oldMovie.getMovieActors().clear();
			oldMovie.getMovieActors().addAll(updatedActors);

		} else {
			System.out.println("actors are null ");
		}

	}

	private void deleteActorsForUpdating(MovieDto movieDto) {
		// for remove an existing actor
		Movie oldMovie = this.movieDao.getById(movieDto.getId());
		System.out.println("Movie before deleting actors " + oldMovie);
		System.out.println("Old actors count " + oldMovie.getMovieActors().size());

		if (movieDto.getActors() != null
				&& movieDto.getActors().size() < oldMovie.getMovieActors().size()) {
			List<Actor> actorsToRemove = new ArrayList<>();

			for (ActorDto actorDto : movieDto.getActors()) {
				actorsToRemove = oldMovie.getMovieActors().stream().filter(actor -> actor.getId() != actorDto.getId())
						.collect(Collectors.toList());
				actorsToRemove.forEach(actor -> System.out.println("comment to remove " + actor));

				// retrieve actors to remove foreign key
				actorsToRemove.forEach(actor -> {
		
					int deleteCount = this.actorDao.deleteActorById(actor.getId());
					System.out.println("Delete actor count " + deleteCount);

					entityManager.flush();
					entityManager.clear();

				});

			}
		}
	}
	
}


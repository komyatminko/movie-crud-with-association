package com.example.demo.test.dao;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.ActorDao;
import com.example.demo.dao.MovieDao;
import com.example.demo.model.entity.Actor;
import com.example.demo.model.entity.Movie;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MovieWithActorTest {

	@Autowired
	MovieDao movieDao;
	
	@Autowired
	ActorDao actorDao;
	
//	@Test
//	@Transactional
//	public void getActorByIdTest(){
//		
//		Actor actor = this.actorDao.getById(1L);
//		System.out.println(actor.getFirstName() + " " + actor.getLastName());
//		
//		List<Movie> movie = actor.getMovies();
//		movie.forEach(System.out::println);
//		
//	}
	
	@Test
	@Transactional
	public void getMovieByIdTest(){
		
		Movie movie = this.movieDao.getById(1L);
		System.out.println(movie);
		
		List<Actor> actors = movie.getMovieActors();
		actors.forEach(actor -> System.out.println(actor.getFirstName() + " " + actor.getLastName()));
		
	}
}

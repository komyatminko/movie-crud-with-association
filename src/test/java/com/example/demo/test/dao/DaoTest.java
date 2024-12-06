package com.example.demo.test.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.demo.dao.MovieDao;
import com.example.demo.model.dto.GenreCount;
import com.example.demo.model.dto.MovieDto;
import com.example.demo.model.entity.Movie;
import com.example.demo.model.entity.MovieComment;
import com.example.demo.model.entity.MovieDetails;
import com.example.demo.service.MovieService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class DaoTest {

	
	@Autowired
	MovieDao movieDao;
	
	@Autowired
	MovieService movieService;
	
//	@Test
//	public void findByGenreTest() {
//		List<Movie> movies = this.movieDao.findByGenre("sci-fi");
//		movies.forEach(movie -> System.out.println(movie));
//	}
//	
//	@Test
//	public void findAllWithPageable() {
//		Page<Movie> movies = this.movieDao.findAll(PageRequest.of(0, 3));
//		movies.forEach(movie -> System.out.println(movie));
//	}
//	
//	@Test
//	public void findByYearBetweenTest() {
//		List<Movie> movies = this.movieDao.findByYearBetween(2000, 2024);
//		movies.forEach(movie -> System.out.println(movie));
//	}
//	
//	@Test
//	public void findByYearLessThanTest() {
//		List<Movie> movies = this.movieDao.findByYearLessThan(2019, Sort.by("title").descending());
//		movies.forEach(movie -> System.out.println(movie));
//	}
//	
//	@Test
//	public void findGenreTest() {
//		List<String> genres = this.movieDao.findGenres();
//		genres.forEach(genre -> System.out.println(genre));
//	}
//	
//	@Test
//	public void findGenreCountTest() {
//		List<Integer> counts = this.movieDao.findGenreCount("sci-fi");
//		counts.forEach(count -> System.out.println(count));
//	}
//	
//	@Test
//	public void findGenreAndCountTest() {
//		List<GenreCount> genreCount = this.movieDao.findGenreAndCount();
//		genreCount.forEach(genre -> System.out.println(genre.getGenre() + " " + genre.getCount()));
//	}
//	
//	@Test
//	public void findAllTest() {
//		List<Movie> movies = this.movieDao.findAll();
//		movies.forEach(movie -> System.out.println(movie));
//	}
//	
//	@Test
//	public void createMovieWithDetailTest() {
//		Movie movie = new Movie();
//		movie.setTitle("titanic");
//		movie.setYear(2020);
//		movie.setGenre("drama");
//		
//		MovieDetails movieDetails = new MovieDetails();
//		movieDetails.setDetails("avatar details");
//		
//		movieDetails.setMovie(movie);
//		movie.setDetails(movieDetails);
//		
//		Movie rs = this.movieDao.save(movie);
//		System.out.println(rs);
//	}
//	
//	@Transactional
//	@Test
//	public void movieWtihCommentTest() {
//		
//		try {
//			Optional<Movie> rs = this.movieDao.findById(1L);
//			Movie movie = rs.get();
//			System.out.println(movie.getTitle()); 
//			List<MovieComment> comments = movie.getMovieComments();
//			comments.forEach(cmt -> System.out.println(cmt.getComment()));
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@Test
	public void test() {
		MovieDto dto = this.movieService.getMovieById(9L).get();
		System.out.println(dto.getCreatedAt());
	}
}


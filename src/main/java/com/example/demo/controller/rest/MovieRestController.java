package com.example.demo.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.model.dto.MovieDto;
import com.example.demo.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieRestController {

	@Autowired
	MovieService movieService;
	
	@GetMapping
	public List<MovieDto> getAllMovies() {
		System.out.println("get all movies");
		List<MovieDto> movies = new ArrayList<>();
		try {
			movies = this.movieService.getAllMovies();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return  movies;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMovieById(@PathVariable Long id) {
		
		Optional<MovieDto> result = this.movieService.getMovieById(id);
		if(result.isPresent()) {
			return ResponseEntity.ok(result.get());
		}
		else return ResponseEntity.badRequest().body(null);
		
	}
	
	@PostMapping("/new")
	public ResponseEntity<Object> saveMovie(@RequestBody MovieDto movieDto, BindingResult result) {
		
		MovieDto dto;
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		else {
			dto = this.movieService.saveMovie(movieDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		} 
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateMovie(@RequestBody MovieDto movieDto, BindingResult result) {
		
		MovieDto dto;
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		else {
//			System.out.println("this is update route");
			dto = this.movieService.saveMovie(movieDto);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
		} 
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> removeMovieById(@PathVariable Long id) {
		
		try {
			this.movieService.removeMovieById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
}
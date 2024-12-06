package com.example.demo.model.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MovieDto {

	
	private Long id;
	
	private String title;

	private Integer year;
	
	private String genre;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private MovieDetailsDto movieDetails;
	
	private List<MovieCommentDto> movieComments;
	
	public MovieDto(Long id, String title, Integer year, String genre, Date createdAt, Date updatedAt, 
					MovieDetailsDto movieDetails, List<MovieCommentDto> movieComments) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.movieDetails = movieDetails;
		this.movieComments = movieComments;
	}

	public MovieDto() {}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getYear() {
		return this.year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Date getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public MovieDetailsDto getMovieDetails() {
		return movieDetails;
	}

	public void setMovieDetails(MovieDetailsDto details) {
		this.movieDetails = details;
	}

	public List<MovieCommentDto> getMovieComments() {
		return movieComments;
	}

	public void setMovieComments(List<MovieCommentDto> movieComments) {
		this.movieComments = movieComments;
	}

	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", title=" + title + ", year=" + year + ", genre=" + genre + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", movieDetails=" + movieDetails + ", movieComments="
				+ movieComments + "]";
	}
	
	
	
}

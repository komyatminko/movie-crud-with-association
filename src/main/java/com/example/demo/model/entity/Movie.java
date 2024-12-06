package com.example.demo.model.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity

public class Movie extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	String title;
	
	@Column
	Integer year;
	
	@Column
	String genre;
	
	@OneToOne(	mappedBy = "movie",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				optional = false,
				orphanRemoval = true)
	private MovieDetails movieDetails;
	
	@OneToMany(	cascade = CascadeType.ALL,
				orphanRemoval = true)
	@JoinColumn(name = "movie_id")
	List<MovieComment> movieComments;
	
	public Movie(String title, Integer year, String genre, MovieDetails details, List<MovieComment> comments) {
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.movieDetails = details;
		this.movieComments = comments;
	}
	
	public Movie() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public MovieDetails getMovieDetails() {
		return movieDetails;
	}

	public void setMovieDetails(MovieDetails details) {
		this.movieDetails = details;
	}

	public List<MovieComment> getMovieComments() {
		return movieComments;
	}

	public void setMovieComments(List<MovieComment> comments) {
		this.movieComments = comments;
	}

//	@Override
//	public String toString() {
//		return "Movie [title=" + title + ", year=" + year + ", genre=" + genre + ", movieDetails=" + movieDetails
//				+ ", comments=" + movieComments + ", id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
//				+ "]";
//	}

	
}

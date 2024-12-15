package com.example.demo.model.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.DialectOverride.Version;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.ToString;

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
	
	@OneToMany(	
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				orphanRemoval = true)
	@JoinColumn(name = "movie_id")
	List<MovieComment> movieComments;
	
	
	@ManyToMany(
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY
			)
	@JoinTable(name = "actor_in_movie",
				joinColumns = {@JoinColumn(name = "movie_id")},
				inverseJoinColumns = {@JoinColumn(name = "actor_id")})
	private List<Actor> movieActors;
	
	
	public Movie(	String title, Integer year, 
					String genre, 
					MovieDetails details, 
					List<MovieComment> comments,
					List<Actor> movieActors
				) {
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.movieDetails = details;
		this.movieComments = comments;
		this.movieActors = movieActors;
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

	public void addComment(MovieComment comment) {
		this.movieComments.add(comment);
		comment.setMovie(this);
	}
	
	public void removeComment(MovieComment comment) {
		this.movieComments.remove(comment);
		comment.setMovie(null);
	}
	
	public List<Actor> getMovieActors() {
		return movieActors;
	}

	public void setMovieActors(List<Actor> movieActors) {
		this.movieActors = movieActors;
	}

	@Override
	
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", genre=" + genre + ", movieDetails=" + movieDetails
				+ ", movieComments=" + movieComments + ", movieActors=" + movieActors + "]";
	}

	
}

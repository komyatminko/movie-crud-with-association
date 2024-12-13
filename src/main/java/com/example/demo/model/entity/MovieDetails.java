package com.example.demo.model.entity;

import java.io.Serializable;

import org.hibernate.annotations.DialectOverride.Version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class MovieDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private String details;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="movie_id")
	private Movie movie;
	
	public MovieDetails() {}

	public MovieDetails(Movie movie, String details) {
		this.movie = movie;
		this.details = details;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return this.getDetails();
	}

	

}

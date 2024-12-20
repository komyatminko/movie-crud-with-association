package com.example.demo.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

@Entity
public class MovieComment extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	public MovieComment() {}
	
	public MovieComment(String comment, Movie movie) {
		this.comment = comment;
		this.movie = movie;
	}

	@Column
	private String comment;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="movie_id", nullable = false)
	private Movie movie;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	@Override
	public String toString() {
		return "MovieComment [comment=" + this.getComment() + ", id=" + this.getId() + " movie_id=" + this.getMovie().getId() + "]";
	}



	
	
	
	
	
}

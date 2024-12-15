package com.example.demo.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.ToString;

@Entity
public class Actor extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private Date birthday;
	
	@Column
	private String gender;
	
	@ManyToMany(
			mappedBy = "movieActors",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<Movie> movies;

	public Actor(String firstName, String lastName, Date birthday, String gender, List<Movie> movies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
		this.movies = movies;
	}
	
	public Actor() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

//	@Override
//	public String toString() {
//		return "Actor [firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", gender="
//				+ gender + ", movies=" + movies + "]";
//	}
	
	
}

package com.example.demo.model.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
	
	

	public Actor(String firstName, String lastName, Date birthday, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
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
	
}

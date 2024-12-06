package com.example.demo.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ActorDto {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private Date birthday;
	
	private String gender;
	
	private Date createdAt;
	
	public ActorDto(Long id, String firstName, String lastName, Date birthday, String gender, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public ActorDto() {}

	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}

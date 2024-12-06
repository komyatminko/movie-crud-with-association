package com.example.demo.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
public class MovieDetailsDto {

	private Long id;
	
	private String details;

	private Date createdAt;
	
	private Date updatedAt;

	public MovieDetailsDto(Long id, String details, Date createdAt, Date updatedAt) {
		this.id = id;
		this.details = details;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public MovieDetailsDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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

	@Override
	public String toString() {
		return "MovieDetailsDto [id=" + id + ", details=" + details + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
	
}

package com.example.demo.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class MovieCommentDto {
	
	public MovieCommentDto() {}

	public MovieCommentDto(Long id, String comment, Date createdAt, Date updatedAt) {
		this.id = id;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private Long id;
	
	private String comment;

	private Date createdAt;

	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		return "MovieCommentDto [id=" + id + ", comment=" + comment + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
	
	
}

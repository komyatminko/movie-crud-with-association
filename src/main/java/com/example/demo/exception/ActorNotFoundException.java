package com.example.demo.exception;

public class ActorNotFoundException extends Exception{

	private String error;
	
	public ActorNotFoundException(String msg) {
		super(msg);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
}

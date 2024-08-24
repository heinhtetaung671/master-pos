package com.pos.master.model.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private List<String> errorMessages;
	
	{
		this.errorMessages = new ArrayList<String>();
	}
	
	public ValidationException(String message) {
		super(message);
		this.errorMessages.add(message);
	}
	
	public ValidationException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public List<String> getErrorMessages() {
		return errorMessages;
	}
}

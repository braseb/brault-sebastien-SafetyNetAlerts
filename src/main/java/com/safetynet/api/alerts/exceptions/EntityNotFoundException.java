package com.safetynet.api.alerts.exceptions;

public class EntityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5587391380141500127L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}

package com.safetynet.api.alerts.exceptions;

public class JsonDataException extends RuntimeException {

	private static final long serialVersionUID = 8214175931342065839L;
	
	public JsonDataException(String message) {
		super(message);
	}

	public JsonDataException(String message, Throwable cause) {
		super(message, cause);
	}
}

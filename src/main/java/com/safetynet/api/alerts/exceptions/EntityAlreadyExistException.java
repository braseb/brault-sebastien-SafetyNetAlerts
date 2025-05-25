package com.safetynet.api.alerts.exceptions;

public class EntityAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -276824473257327291L;
	private final Object element;

	public EntityAlreadyExistException(String message, Object element) {
		super(message);
		this.element = element;
	}

	public Object getElement() {
		return element;
	}
	
	

}

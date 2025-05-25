package com.safetynet.api.alerts.exceptions;

public class EntityNotFoundExceptionWithReturn extends RuntimeException{

	private static final long serialVersionUID = -6749212839351610134L;
	
	private final Object element;

	public EntityNotFoundExceptionWithReturn(String message, Object element) {
		super(message);
		this.element = element;
	}

	public Object getElement() {
		return element;
	}
	
	

}

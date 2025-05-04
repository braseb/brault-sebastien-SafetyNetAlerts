package com.safetynet.api.alerts.model.dto;

public class MemberHousehold {
	private String firstName;
	private String lastName;
		
	public MemberHousehold(String lastName, String firstName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	

}

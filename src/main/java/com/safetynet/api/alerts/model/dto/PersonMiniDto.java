package com.safetynet.api.alerts.model.dto;

public class PersonMiniDto {
	private String lastName;
	private String firstName;
	//private String address;
	
	
	
	public PersonMiniDto(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		//this.address = address;
	}
	
	public PersonMiniDto() {
		super();
		
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	//public String getAddress() {
	//	return address;
	//}
	
	//public void setAddress(String address) {
	//	this.address = address;
	//}
	
	
}

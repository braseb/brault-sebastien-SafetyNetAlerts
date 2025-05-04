package com.safetynet.api.alerts.model.dto;

public class PersonDto {
	private String lastName;
	private String firstName;
	private String address;
	private String phone;
		
	
	public PersonDto(String lastName, String firstName, String address, String phone) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phone = phone;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	
}

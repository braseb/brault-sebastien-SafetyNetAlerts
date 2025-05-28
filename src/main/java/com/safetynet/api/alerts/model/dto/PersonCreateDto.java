package com.safetynet.api.alerts.model.dto;

import jakarta.validation.constraints.NotBlank;

public class PersonCreateDto{
	
	private @NotBlank(message = "the lastName is necessary") String lastName;
	private @NotBlank(message = "the firstName is necessary") String firstName;
	private @NotBlank  String address;
	private @NotBlank  String city;
	private @NotBlank  String zip;
	private @NotBlank  String phone;
	private @NotBlank  String email;
	
	
	
	public PersonCreateDto(String lastName,
							String firstName, 
							String address, 
							String city,
							String zip, 
							String phone, 
							String email) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


}



package com.safetynet.api.alerts.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PersonCreateDto{
	
	private @NotBlank(message = "the lastName is necessary") @NotNull String lastName;
	private @NotBlank @NotNull String firstName;
	private @NotBlank @NotNull String address;
	private @NotBlank @NotNull String city;
	private @NotBlank @NotNull String zip;
	private @NotBlank @NotNull String phone;
	private @NotBlank @NotNull String email;
	
	
	
	public  PersonCreateDto(String lastName, 
			String firstName,
			String address,
			String city,
			String zip,
			String phone,
			String email) {}
	
	
		
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



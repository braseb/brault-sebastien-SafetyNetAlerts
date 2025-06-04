package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class PersonMedicalRecordWithAddressAndEmailDto extends PersonMedicalRecordDto {
	String address;
	String email;
	

	public PersonMedicalRecordWithAddressAndEmailDto(String lastName, String firstName, String address, int age,
			List<String> medications, List<String> allergies, String email) {
		super(lastName, firstName, age, medications, allergies);
		this.email = email;
		this.address = address;
	}
	
	public PersonMedicalRecordWithAddressAndEmailDto() {
		super();
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}

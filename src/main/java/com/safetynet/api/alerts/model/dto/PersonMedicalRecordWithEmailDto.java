package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class PersonMedicalRecordWithEmailDto extends PersonMedicalRecordDto {
	String email;

	public PersonMedicalRecordWithEmailDto(String lastName, String firstName, String address, int age,
			List<String> medications, List<String> allergies, String email) {
		super(lastName, firstName, address, age, medications, allergies);
		this.email = email;
	}
	
	public PersonMedicalRecordWithEmailDto() {
		super();
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

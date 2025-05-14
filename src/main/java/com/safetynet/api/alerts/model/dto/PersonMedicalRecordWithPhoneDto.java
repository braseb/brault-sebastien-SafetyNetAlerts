package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class PersonMedicalRecordWithPhoneDto extends PersonMedicalRecordDto {
	String phone;

	public PersonMedicalRecordWithPhoneDto(String lastName, String firstName, String address, int age,
			List<String> medications, List<String> allergies, String phone) {
		super(lastName, firstName, address, age, medications, allergies);
		this.phone = phone;
	}
	
	public PersonMedicalRecordWithPhoneDto() {
		super();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}

package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class PersonMedicalRecordDto extends PersonDto {
	
	List<String> medications;
	List<String> allergies;
	
	public PersonMedicalRecordDto(String lastName, String firstName,  int age, List<String> medications, List<String> allergies) {
		super(lastName, firstName, age);
		this.medications = medications;
		this.allergies = allergies;
	}
	
	public PersonMedicalRecordDto() {
		super();
		
	}
	
	
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	
}

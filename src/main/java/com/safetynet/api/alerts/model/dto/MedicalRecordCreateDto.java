package com.safetynet.api.alerts.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MedicalRecordCreateDto{
	
	private @NotBlank(message = "the lastName is necessary") String lastName;
	private @NotBlank(message = "the firstName is necessary") String firstName;
	private @NotBlank  @Pattern(regexp = "^\\d\\d/\\d\\d/\\d{4}$", message = "The date must be to this format MM/dd/yyyy") String birthdate;
	private @NotNull List<String> medications;
	private @NotNull List<String> allergies;

	
	
	
	public MedicalRecordCreateDto(String lastName,
							String firstName, 
							String birthdate, 
							List<String> medications,
							List<String> allergies) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
		
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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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



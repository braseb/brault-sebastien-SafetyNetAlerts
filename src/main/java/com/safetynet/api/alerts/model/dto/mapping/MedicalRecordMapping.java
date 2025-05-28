package com.safetynet.api.alerts.model.dto.mapping;

import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.dto.MedicalRecordCreateDto;
import com.safetynet.api.alerts.model.dto.MedicalRecordUpdateDto;

public class MedicalRecordMapping {
	
	public static MedicalRecord mapToMedicalRecord(MedicalRecordCreateDto medicalRecordCreate) {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName(medicalRecordCreate.getLastName());
		medicalRecord.setFirstName(medicalRecordCreate.getFirstName());
		medicalRecord.setBirthdate(medicalRecordCreate.getBirthdate());
		medicalRecord.setMedications(medicalRecordCreate.getMedications());
		medicalRecord.setAllergies(medicalRecordCreate.getAllergies());
				
		return medicalRecord;
	}
	
	public static MedicalRecordCreateDto mapToMedicalRecordCreateDto(MedicalRecord medicalRecord) {
		MedicalRecordCreateDto medicalRecordCreate = new MedicalRecordCreateDto(
				medicalRecord.getLastName(),
				medicalRecord.getFirstName(),
				medicalRecord.getBirthdate(),											
				medicalRecord.getMedications(),
				medicalRecord.getAllergies());
		return medicalRecordCreate;
	}
	
	public static MedicalRecord mapToMedicalRecord(String lastName, String firstName, MedicalRecordUpdateDto medicalRecordUpdate) {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName(lastName);
		medicalRecord.setFirstName(firstName);
		medicalRecord.setBirthdate(medicalRecordUpdate.birthdate());
		medicalRecord.setMedications(medicalRecordUpdate.medications());
		medicalRecord.setAllergies(medicalRecordUpdate.allergies());
		
		
		return medicalRecord;
	}
	
	public static MedicalRecordUpdateDto mapToMedicalRecordUpdateDto(MedicalRecord medicalRecord) {
		MedicalRecordUpdateDto medicalRecordUpdate = new MedicalRecordUpdateDto(															
				medicalRecord.getBirthdate(),
				medicalRecord.getMedications(),
				medicalRecord.getAllergies());
				
		
		
		return medicalRecordUpdate;
	}
}

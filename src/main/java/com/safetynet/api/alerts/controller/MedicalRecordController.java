package com.safetynet.api.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class MedicalRecordController {
	@Autowired
	MedicalRecordService medicalRecordService;
	
	@PostMapping("/medicalRecord")
	@Operation(summary = "Create a new medical record")
	public ResponseEntity<String> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		boolean created = medicalRecordService.createMedicalRecord(medicalRecord);
		if (created) {
			return new ResponseEntity<String> ("Medical record created", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String> ("Medical record not created", HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/medicalRecord/{lastName}/{firstName}")
	@Operation(summary = "Update a medical record")
	public ResponseEntity<String> updateMedicalRecord(@PathVariable String lastName, @PathVariable String firstName, @RequestBody MedicalRecord medicalRecordNew) {
		MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(lastName,firstName);
		
		if (medicalRecord.getLastName().isEmpty() &&  medicalRecord.getFirstName().isEmpty()) {
			return new ResponseEntity<String> ("Medical record not found", HttpStatus.NOT_FOUND);
		}
		
		boolean updated = medicalRecordService.updateMedicalRecord(lastName,firstName, medicalRecordNew);
				
		if (updated) {
			return new ResponseEntity<String> ("Medical record updated", HttpStatus.OK);
		}
		
		return new ResponseEntity<String> ("Datas invalids", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/medicalRecord/{lastName}/{firstName}")
	@Operation(summary = "Delete a medical record")
	public ResponseEntity<String> removeMedicalRecord(@PathVariable String lastName, @PathVariable String firstName){
		MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(lastName, firstName);
		if (medicalRecord.getLastName().isEmpty() &&  medicalRecord.getFirstName().isEmpty()) {
			return new ResponseEntity<String> ("Medical record not found", HttpStatus.NOT_FOUND);
		}
		boolean removed = medicalRecordService.removeMedicalRecord(lastName, firstName);
							
		
		if (removed) {
			return new ResponseEntity<String> ("The Medical record has been removed", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String> ("Error removing", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}

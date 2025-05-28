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
import com.safetynet.api.alerts.model.dto.MedicalRecordCreateDto;
import com.safetynet.api.alerts.model.dto.MedicalRecordUpdateDto;
import com.safetynet.api.alerts.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class MedicalRecordController {
	@Autowired
	MedicalRecordService medicalRecordService;
	
	@PostMapping("/medicalRecord")
	@Operation(summary = "Create a new medical record")
	public ResponseEntity<MedicalRecordCreateDto> createMedicalRecord(@RequestBody MedicalRecordCreateDto medicalRecord) {
		MedicalRecordCreateDto medicalRecordCreated = medicalRecordService.createMedicalRecord(medicalRecord);
		return new ResponseEntity<MedicalRecordCreateDto>(medicalRecordCreated, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/medicalRecord/{lastName}/{firstName}")
	@Operation(summary = "Update a medical record")
	public ResponseEntity<MedicalRecordUpdateDto> updateMedicalRecord(@PathVariable String lastName, @PathVariable String firstName, @RequestBody MedicalRecordUpdateDto medicalRecordUpdate) {
		MedicalRecordUpdateDto medicalRecordUpdated =  medicalRecordService.updateMedicalRecord(lastName,
																								firstName, 
																								medicalRecordUpdate);
			
		return ResponseEntity.ok(medicalRecordUpdated);
	}
	
	@DeleteMapping("/medicalRecord/{lastName}/{firstName}")
	@Operation(summary = "Delete a medical record")
	public ResponseEntity<String> removeMedicalRecord(@PathVariable String lastName, @PathVariable String firstName){
		medicalRecordService.removeMedicalRecord(lastName, firstName);
		return ResponseEntity.noContent().build();			
		
		
		
	}
}

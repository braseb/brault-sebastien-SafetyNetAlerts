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

import com.safetynet.api.alerts.model.dto.FireStationCreateDto;
import com.safetynet.api.alerts.model.dto.FireStationUpdateDto;
import com.safetynet.api.alerts.service.FireStationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class FireStationController {
	
	@Autowired
	FireStationService fireStationService;

	@PostMapping("/firestation")
	@Operation(summary = "Create a new fire station")
	public ResponseEntity<FireStationCreateDto> createFireStation(@Valid @RequestBody FireStationCreateDto fireStation) {
		FireStationCreateDto fireStationcreated = fireStationService.createFireStation(fireStation);
		return new ResponseEntity<FireStationCreateDto>(fireStationcreated, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/firestation/{address}")
	@Operation(summary = "Update the number of a fire station")
	public ResponseEntity<FireStationUpdateDto> updateFireStation(@PathVariable String address, @Valid @RequestBody FireStationUpdateDto fireStationUpdate) {
		FireStationUpdateDto fireStationUpdated = fireStationService.updateFireStation(address,fireStationUpdate);
		return ResponseEntity.ok(fireStationUpdated);
		
	}
	
	@DeleteMapping("/firestation/station/{stationNumber}")
	@Operation(summary = "Delete a fire station with his number")
	public ResponseEntity<?> removeFireStation(@PathVariable Integer stationNumber){
		
		fireStationService.removeFireStationByNumber(stationNumber);
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/firestation/address/{address}")
	@Operation(summary = "Delete a fire station with his address")
	public ResponseEntity<?> removeFireStation(@PathVariable String address){
		
		fireStationService.removeFireStationByAddress(address);
		return ResponseEntity.noContent().build();
		
	}
	
}

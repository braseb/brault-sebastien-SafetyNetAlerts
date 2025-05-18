package com.safetynet.api.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.service.FireStationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class FireStationController {
	
	@Autowired
	FireStationService fireStationService;

	@PostMapping("/firestation")
	@Operation(summary = "Create a new fire station")
	public ResponseEntity<String> createFireStation(@RequestBody FireStation fireStation) {
		boolean created = fireStationService.createFireStation(fireStation);
		if (created) {
			return new ResponseEntity<String> ("Fire sattion created", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String> ("Fire station not created", HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/firestation/{address}")
	@Operation(summary = "Update the number of a fire station")
	public ResponseEntity<String> updateFireStation(@PathVariable String address, @RequestBody FireStation fireStation) {
		Integer stationNumber = fireStationService.getStationNumberByAddress(address);
		
		if (stationNumber == null ) {
			return new ResponseEntity<String> ("Fire station not found", HttpStatus.NOT_FOUND);
		}
		
		boolean updated = fireStationService.updateFireStation(address,stationNumber);
				
		if (updated) {
			return new ResponseEntity<String> ("Fire station updated", HttpStatus.OK);
		}
		
		return new ResponseEntity<String> ("Datas invalids", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/firestation/station/{stationNumber}")
	@Operation(summary = "Delete a fire station with his number")
	public ResponseEntity<String> removeFireStation(@PathVariable Integer stationNumber){
		List<String> fireStationAddress = fireStationService.getAddressByStationNumber(stationNumber);
		if (fireStationAddress.isEmpty()) {
			return new ResponseEntity<String> ("Fire station not found", HttpStatus.NOT_FOUND);
		}
		boolean removed = fireStationAddress.stream()
							.allMatch(p -> fireStationService.removeFireStationByNumber(stationNumber));
		
		if (removed) {
			return new ResponseEntity<String> ("The fire station has been removed", HttpStatus.OK);
		}
		
		return new ResponseEntity<String> ("Error removing", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}

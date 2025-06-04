package com.safetynet.api.alerts.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FireDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.HouseholdDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithAddressAndEmailDto;
import com.safetynet.api.alerts.service.AlertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class AlertController {
		
	@Autowired
	private AlertService alertService;
	
	@GetMapping("/childAlert")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of childrens found",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ChildAlertDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No children found", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get the list of childrens at this address")
	public ResponseEntity<List<ChildAlertDto>> getChildAlert(@RequestParam final String address){
		
		List<ChildAlertDto> childAlert = alertService.getChildrenByAddress(address);
		if (childAlert.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(childAlert);
		}
		
		return ResponseEntity.ok(childAlert);
		
	}
	
	@GetMapping("/firestation")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of person covered by this number station, the number of children and the number of adult",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FirestationDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No person found", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get the list of person covered by this number station, the number of children and the number of adult")
	public ResponseEntity<FirestationDto> getPersonCoveredByFireStation(@RequestParam final int stationNumber){
		FirestationDto fireStation = alertService.getPersonCoveredByFireStation(stationNumber);
		if (fireStation.getNumberAdult() == 0 && fireStation.getNumberChild() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fireStation);
		}
		
		return new ResponseEntity<FirestationDto>(fireStation, HttpStatus.OK);
	}
	
	@GetMapping("/phoneAlert")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of phone number of persons covered by the station number",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = List.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No phone found", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get the list of phone number of persons covered by the station number")
	public ResponseEntity<List<String>> getPhoneAlert(@RequestParam final int stationNumber) {
		List<String> listPhone = alertService.getPhoneAlert(stationNumber);
		if(listPhone.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listPhone);
		}
		return new ResponseEntity<List<String>>(listPhone, HttpStatus.OK);
	}
	
	@GetMapping("/fire")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of residents living at this address and the number of the fire station serving it",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FireDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No resident found for this station number", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get the list of residents living at this address and the number of the fire station serving it.")
	public ResponseEntity<FireDto> getCasernNumberAndPersonsByAddress(@RequestParam String address){
		FireDto fireDto = alertService.getFirestationNumberAndPersonsByAddress(address);
		if(fireDto.getPersonsInfos().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fireDto);
		}
		
		return new ResponseEntity<FireDto>(fireDto, HttpStatus.OK);
	}
	
	@GetMapping("/flood/stations")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of all households served by the fire station. This list group person by address",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = HouseholdDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No household found for this list of station number", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get a list of all households served by the fire station. This list group person by address")
	public ResponseEntity<HouseholdDto> getHousehold(@RequestParam final List<Integer>  stations) {
		HouseholdDto houseHoldDto = alertService.getHouseholdsByStationNumbers(stations);
		if(houseHoldDto.getHousehold().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(houseHoldDto);
		}
		
		return new ResponseEntity<HouseholdDto>(houseHoldDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/personInfo")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of person with this lastname (lastname, firstname, age, address, medical infos)",
	        content = @Content(
	            mediaType = "application/json"
	            
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No person found for this lastname", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get a list of person with this lastname")
	public ResponseEntity<List<PersonMedicalRecordWithAddressAndEmailDto>> getPersoninfo(@RequestParam final String  lastName) {
		List<PersonMedicalRecordWithAddressAndEmailDto> personMedicalRecordWithEmailDto = alertService.getPersonMedicalRecordWithEmailByLastName(lastName);
		if(personMedicalRecordWithEmailDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personMedicalRecordWithEmailDto);
		}
		
		return new ResponseEntity<List<PersonMedicalRecordWithAddressAndEmailDto>>(personMedicalRecordWithEmailDto, HttpStatus.OK);
	}
	
	@GetMapping("/communityEmail")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "List of all the mails in the city",
	        content = @Content(
	            mediaType = "application/json"
	            
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "No email found for this city", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Get a list of all the mails in the city")
	public ResponseEntity<List<String>> getEmaiFromCity(@RequestParam final String  city) {
		List<String> listMail = alertService.getAllEmailByCity(city);
		if(listMail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listMail);
		}
		
		return new ResponseEntity<List<String>>(listMail, HttpStatus.OK);
	}
	
	
	
}

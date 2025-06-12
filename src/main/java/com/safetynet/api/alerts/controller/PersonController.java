package com.safetynet.api.alerts.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;
import com.safetynet.api.alerts.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class PersonController {

	private final Logger log = LogManager.getLogger();
    	
	@Autowired
	private PersonService personService;
    
	@PostMapping("/person")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Person created successfully",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = PersonCreateDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "400", 
	    		description = "Invalid input", 
	    		content = @Content(
	            mediaType = "application/json")
	            
	        ),
	    @ApiResponse(
	    		responseCode = "409", 
	    		description = "Person already exist", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	
	@Operation(summary = "Create a new person")
	public ResponseEntity<PersonCreateDto> createPerson(@Valid @RequestBody PersonCreateDto personToCreate) {
		
		PersonCreateDto personCreated = personService.createPerson(personToCreate);
		log.info("CREATED /person : {}", new Gson().toJson(personCreated));
		return new ResponseEntity<PersonCreateDto>(personCreated, HttpStatus.CREATED);
	}
	
	@PutMapping("/person/{lastName}/{firstName}")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Person updated successfully",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = PersonUpdateDto.class)
	        )
	    ),
	    @ApiResponse(
	    		responseCode = "400", 
	    		description = "Invalid input", 
	    		content = @Content(
	            mediaType = "application/json")
	            
	        ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "Person not found", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Update a person")
	public ResponseEntity<PersonUpdateDto> updatePerson(@PathVariable String lastName, @PathVariable String firstName, @Valid  @RequestBody PersonUpdateDto personToUpdate) {
		
		PersonUpdateDto personUpdated = personService.updatePerson(lastName, firstName, personToUpdate);
		log.info("UPDATED /person : {} {} : {}", lastName, firstName, new Gson().toJson(personUpdated));
		return ResponseEntity.ok(personUpdated);
		
	}
	
	@DeleteMapping("/person/{lastName}/{firstName}")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "204",
	        description = "Person deleted successfully",
	        content = @Content
	        
	    ),
	    @ApiResponse(
	    		responseCode = "404", 
	    		description = "Person not found", 
	    		content = @Content(
	            mediaType = "application/json")
	    	),
	})
	@Operation(summary = "Delete a person")
	public ResponseEntity<?> removePerson(@PathVariable String lastName, @PathVariable String firstName){
		personService.removePerson(lastName, firstName);
		log.info("DELETED /person : {} {}", lastName, firstName);
		return ResponseEntity.noContent().build();
			
	}
	
	

}

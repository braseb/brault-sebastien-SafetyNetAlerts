package com.safetynet.api.alerts.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;
import com.safetynet.api.alerts.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class PersonController {

    
    	
	@Autowired
	private PersonService personService;
    	
	
	@PostMapping("/person")
	@Operation(summary = "Create a new person")
	public ResponseEntity<PersonCreateDto> createPerson(@Valid @RequestBody PersonCreateDto personToCreate) {
		
		PersonCreateDto personCreated = personService.createPerson(personToCreate);
		return new ResponseEntity<PersonCreateDto>(personCreated, HttpStatus.CREATED);
	}
	
	@PutMapping("/person/{lastName}/{firstName}")
	@Operation(summary = "Update a person")
	public ResponseEntity<PersonUpdateDto> updatePerson(@PathVariable String lastName, @PathVariable String firstName, @Valid  @RequestBody PersonUpdateDto personToUpdate) {
		
		PersonUpdateDto personUpdated = personService.updatePerson(lastName, firstName, personToUpdate);
		return ResponseEntity.ok(personUpdated);
		
	}
	
	@DeleteMapping("/person/{lastName}/{firstName}")
	@Operation(summary = "Delete a person")
	public ResponseEntity<?> removePerson(@PathVariable String lastName, @PathVariable String firstName){
		personService.removePerson(lastName, firstName);
		return ResponseEntity.noContent().build();
			
	}
	
	

}

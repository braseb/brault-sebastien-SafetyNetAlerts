package com.safetynet.api.alerts.controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PersonController {

    	
	@Autowired
	private PersonService personService;
    
	
	@GetMapping("/persons")
	public List<Person> getPersons(){
		try {
			return personService.getPersonByName("Boyd", "John");
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	@PostMapping("/persons")
	@Operation(summary = "Créer une nouvelle personne")
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
		boolean created = personService.createPerson(person);
		if (created) {
			return new ResponseEntity<String> ("Bien créer", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String> ("Non créer", HttpStatus.BAD_REQUEST);
		
	}
	
	

}

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
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
		return new ResponseEntity<String> ("Bien créer", HttpStatus.CREATED);
	}
	
	

}

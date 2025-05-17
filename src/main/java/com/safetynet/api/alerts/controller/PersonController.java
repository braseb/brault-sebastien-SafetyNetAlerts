package com.safetynet.api.alerts.controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;


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
	
	@PostMapping("/person")
	@Operation(summary = "Créer une nouvelle personne")
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
		boolean created = personService.createPerson(person);
		if (created) {
			return new ResponseEntity<String> ("Bien créer", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String> ("Non créer", HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/person/{lastName}/{firstName}")
	public ResponseEntity<String> updatePerson(@PathVariable String lastName, @PathVariable String firstName, @RequestBody Person person) {
		List<Person> persons = personService.getPersonByName(lastName, firstName);
		
		
		boolean updated = persons.stream()
							.allMatch(p-> personService.updatePerson(lastName, firstName, person));
		
		if (persons.isEmpty()) {
			return new ResponseEntity<String> ("Personne non trouvée", HttpStatus.NOT_FOUND);
		}
		if (updated) {
			return new ResponseEntity<String> ("La personne a bien été mise à jour", HttpStatus.OK);
		}
		
		return new ResponseEntity<String> ("Données de mise à jour invalide", HttpStatus.BAD_REQUEST);
	}
	
	

}

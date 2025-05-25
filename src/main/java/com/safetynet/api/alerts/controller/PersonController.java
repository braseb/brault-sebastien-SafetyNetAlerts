package com.safetynet.api.alerts.controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.safetynet.api.alerts.model.Person;
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
	@Operation(summary = "Créer une nouvelle personne")
	public ResponseEntity<PersonCreateDto> createPerson(@Valid @RequestBody PersonCreateDto person) {
		PersonCreateDto personCreated = personService.createPerson(person);
		
		return new ResponseEntity<PersonCreateDto>(personCreated, HttpStatus.CREATED);
		
		
		
		
		
	}
	
	@PutMapping("/person/{lastName}/{firstName}")
	@Operation(summary = "Modifier une personne")
	public ResponseEntity<PersonUpdateDto> updatePerson(@PathVariable String lastName, @PathVariable String firstName, @RequestBody PersonUpdateDto personToUpdate) {
		//List<Person> persons = personService.getPersonByName(lastName, firstName);
		
		//if (persons.isEmpty()) {
		//	return new ResponseEntity<String> ("Personne non trouvée", HttpStatus.NOT_FOUND);
		//}
		
		//boolean updated = persons.stream()
		//					.allMatch(p-> personService.updatePerson(lastName, firstName, person));
		PersonUpdateDto personUpdated = personService.updatePerson(lastName, firstName, personToUpdate);
				
		//if (updated) {
		return ResponseEntity.ok(personUpdated);
		//}
		
		//return new ResponseEntity<String> ("Données de mise à jour invalide", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/person/{lastName}/{firstName}")
	@Operation(summary = "Supprimer une personne")
	public ResponseEntity<?> removePerson(@PathVariable String lastName, @PathVariable String firstName){
		//List<Person> persons = personService.getPersonByName(lastName, firstName);
		//if (persons.isEmpty()) {
		//	return new ResponseEntity<String> ("Personne non trouvée", HttpStatus.NOT_FOUND);
		//}
		//boolean removed = persons.stream()
		//					.allMatch(p -> personService.removePerson(lastName, firstName));
		personService.removePerson(lastName, firstName);
		
		return ResponseEntity.noContent().build();
		
		//if (removed) {
		//	return new ResponseEntity<String> ("La personne a bien été supprimée", HttpStatus.OK);
		//}
		
		//return new ResponseEntity<String> ("Erreur lors de la suppression", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	

}

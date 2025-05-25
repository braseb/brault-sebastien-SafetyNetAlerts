package com.safetynet.api.alerts.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.PersonMapping;
import com.safetynet.api.alerts.repository.PersonRepository;

@Service
public class PersonService {

    private final Person person;
	
	@Autowired
	private PersonRepository personRepository;

    PersonService(Person person) {
        this.person = person;
    }
	
	public List<Person> getPersonByName(String lastName, String firstName) {
		
		return personRepository.getPersonByName(lastName, firstName);
		
	}
	
public List<Person> getPersonByLastName(String lastName){
		
		return personRepository.getPersonByLastName(lastName);
		
	}
	
	public List<Person> getPersonByAddress(String address) {
		return personRepository.getPersonByAddress(address);
	}
	
	public List<Person> getPersonByCity(String city){
		return personRepository.getPersonByCity(city);
	}

	public PersonCreateDto createPerson(PersonCreateDto person) {		
		
		return personRepository.create(person);
		
	}
	
	public PersonUpdateDto updatePerson(String lastName, String firstName, PersonUpdateDto personUpdate) {
		try {
			Person person = PersonMapping.mapToPerson(lastName, firstName, personUpdate);
			return PersonMapping.mapToPersonUpdateDto(personRepository.update(person));
		} catch (EntityNotFoundException e) {
			throw e;
		}
		
		
	}
	
	public Boolean removePerson(String lastName, String firstName) {
		
			return personRepository.remove(lastName, firstName);
		
	}
	
	
}

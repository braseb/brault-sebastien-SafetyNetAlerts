package com.safetynet.api.alerts.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getPersonByName(String lastName, String firstName){
		
		return personRepository.getPersonByName(lastName, firstName);
		
	}
	
public List<Person> getPersonByLastName(String lastName){
		
		return personRepository.getPersonByLastName(lastName);
		
	}
	
	public List<Person> getPersonByAddress(String address){
		return personRepository.getPersonByAddress(address);
	}
	
	public List<Person> getPersonByCity(String city){
		return personRepository.getPersonByCity(city);
	}

	public boolean createPerson(Person person) {		
		return personRepository.create(person);
	}
	
	public boolean updatePerson(String lastName, String firstName, Person person) {
		return personRepository.update(lastName,firstName,person);
	}
	
	public boolean removePerson(String lastName, String firstName) {
		return personRepository.remove(lastName, firstName);
	}
	
	
}

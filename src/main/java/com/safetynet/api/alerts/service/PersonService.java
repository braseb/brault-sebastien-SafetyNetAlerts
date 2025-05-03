package com.safetynet.api.alerts.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getPersonByName(String lastName, String firstName) throws Exception{
		
		return personRepository.getPersonByName(lastName, firstName);
		
	}
}

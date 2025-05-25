package com.safetynet.api.alerts.model.dto.mapping;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;

public class PersonMapping {
	public static Person mapToPerson(PersonCreateDto personCreate) {
		Person person = new Person();
		person.setLastName(personCreate.getLastName());
		person.setFirstName(personCreate.getFirstName());
		person.setAddress(personCreate.getAddress());
		person.setCity(personCreate.getCity());
		person.setZip(personCreate.getZip());
		person.setPhone(personCreate.getPhone());
		person.setEmail(personCreate.getEmail());
		
		return person;
	}
	
	public static PersonCreateDto mapToPersonCreateDto(Person person) {
		PersonCreateDto personCreate = new PersonCreateDto(
															person.getLastName(),
															person.getFirstName(),
															person.getAddress(),
															person.getCity(),
															person.getZip(),
															person.getPhone(),
															person.getEmail());
		
		
		return personCreate;
	}
	
	public static Person mapToPerson(String lastName, String firstName, PersonUpdateDto personUpdate) {
		Person person = new Person();
		person.setLastName(lastName);
		person.setFirstName(firstName);
		person.setAddress(personUpdate.address());
		person.setCity(personUpdate.city());
		person.setZip(personUpdate.zip());
		person.setPhone(personUpdate.phone());
		person.setEmail(personUpdate.email());
		
		return person;
	}
	
	public static PersonUpdateDto mapToPersonUpdateDto(Person person) {
		PersonUpdateDto personUpdate = new PersonUpdateDto(															
															person.getAddress(),
															person.getCity(),
															person.getZip(),
															person.getPhone(),
															person.getEmail());
		
		
		return personUpdate;
	}
}

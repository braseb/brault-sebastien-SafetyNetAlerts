package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.exceptions.JsonDataException;
import com.safetynet.api.alerts.model.Person;


@Component
public class PersonRepository {
	
	@Autowired
	private JsonDatas datas;
	
private final Logger log = LogManager.getLogger();
	
	private List<Person> getDatasFromJson() {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		if (personArray != null) {
			try {
				Gson gson = new Gson();
				Type typeListPerson = new TypeToken<List<Person>>() {}.getType();
				List<Person> persons = gson.fromJson(personArray, typeListPerson);
				return persons;
			} catch (Exception e) {
				log.error("Unable to convert the persons key to list", e, personArray);
				throw new JsonDataException("Unable to get persons datas");
			}
		}
		else {
			log.error("Unable to get the fireStations key", personArray);
			throw new JsonDataException("Unable to get persons datas");
			
		}
		
	}
	
	private void setDatasFromJson(List<Person> persons) {
		Gson gson = new Gson();
		JsonObject jsonCache = datas.getFileCache();
		if (jsonCache != null) {
			try {
				JsonElement personsJson = gson.toJsonTree(persons);
				jsonCache.add("persons", personsJson);
				datas.writeJsonToFile();
			} catch (Exception e) {
				log.error("Unable to set persons datas", e);
				throw new JsonDataException("Unable to set persons key");
			}
			
		}
		else {
			log.error("Unable to get the json cache", jsonCache);
			throw new JsonDataException("Unable to set persons datas");
			
		}
		
	}
	
	public List<Person> getPersonByName(String lastName, String firstName) {
		List<Person> personsSelect = new ArrayList<Person>();
		
		List<Person> persons = getDatasFromJson();
		personsSelect =	persons.stream()
								.filter(p-> p.getFirstName().equalsIgnoreCase(firstName) && 
										p.getLastName().equalsIgnoreCase(lastName))
								.collect(Collectors.toList());
		
		return personsSelect;
	}
		
	public List<Person> getPersonByAddress(String address) {
		List<Person> personsSelect = new ArrayList<Person>();
		List<Person> persons = getDatasFromJson();
		
		personsSelect =	persons.stream()
								.filter(p-> p.getAddress().equalsIgnoreCase(address))
								.collect(Collectors.toList());
		
		return personsSelect;
	}
	
	public Person create(Person personCreate) {
		Person person = null;
		List<Person> persons = getDatasFromJson();
			
		boolean exist = persons.stream()
								.anyMatch(p-> 
									p.getFirstName().equalsIgnoreCase(personCreate.getFirstName()) && 
									p.getLastName().equalsIgnoreCase(personCreate.getLastName()));
		
		if (!exist) {
			persons.add(personCreate);
			setDatasFromJson(persons);
			person = personCreate;
		}
		
		else {
			throw new EntityAlreadyExistException("Person already exist", Map.of("lastname", personCreate.getLastName(),
																				"fistname", personCreate.getFirstName()));
		}
	
		return person;
	}
	
	public Person update(Person personUpdate)  {
		List<Person> persons = getDatasFromJson();
		Optional<Person> personFound = persons.stream()
							.filter(p -> p.getFirstName().equalsIgnoreCase(personUpdate.getFirstName()) 
									&& p.getLastName().equalsIgnoreCase(personUpdate.getLastName()))
							.peek(p -> {p.setAddress(personUpdate.getAddress());
										p.setCity(personUpdate.getCity());
										p.setEmail(personUpdate.getEmail());
										p.setPhone(personUpdate.getPhone());
										p.setZip(personUpdate.getZip());})
							.findFirst();
							
		if (personFound.isEmpty()) {
			throw new EntityNotFoundException("Person not found");
			
		}
		else {
			setDatasFromJson(persons);
			return personFound.get();
		}
		
	}
	
	public void remove(String lastName, String firstName) {
		List<Person> persons = getDatasFromJson();
		List<Person> personsToKeep =  persons.stream()
									.filter(p -> !(p.getFirstName().equalsIgnoreCase(firstName)) 
											|| !(p.getLastName().equalsIgnoreCase(lastName)))
									.toList();
		
		if (persons.size() != personsToKeep.size()) {
			setDatasFromJson(personsToKeep);
		}
		else {
			throw new EntityNotFoundException("Person not found");
		}
	}
	
	

	public List<Person> getPersonByLastName(String lastName) {
		List<Person> personsSelect = new ArrayList<Person>();
		List<Person> persons = getDatasFromJson();
		personsSelect =	persons.stream()
								.filter(p-> p.getLastName().equalsIgnoreCase(lastName))
								.collect(Collectors.toList());
		
		return personsSelect;
	}

	public List<Person> getPersonByCity(String city) {
		List<Person> personsSelect = new ArrayList<Person>();
		
		List<Person> persons = getDatasFromJson();
		personsSelect =	persons.stream()
								.filter(p-> p.getCity().equalsIgnoreCase(city))
								.collect(Collectors.toList());
		
		return personsSelect;
	}
	
	
}

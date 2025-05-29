package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.model.Person;


@Component
public class PersonRepository {
	
	@Autowired
	private JsonDatas datas;
	
	public PersonRepository() {
		
	}
	
	public List<Person> getPersonByName(String lastName, String firstName) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		
		List<Person> personsSelect = new ArrayList<Person>();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getFirstName().equalsIgnoreCase(firstName) && 
											p.getLastName().equalsIgnoreCase(lastName))
									.collect(Collectors.toList());
		}
				
		
		return personsSelect;
	}
		
	public List<Person> getPersonByAddress(String address) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> personsSelect = new ArrayList<Person>();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getAddress().equalsIgnoreCase(address))
									.collect(Collectors.toList());
		}
		
		
		
		return personsSelect;
	}
	
	
	
	public List<Person> getAllPerson(){
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> persons = new ArrayList<Person>();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			persons = gson.fromJson(personArray, personsListType);
		}
				
		return persons;
	}

	public Person create(Person personCreate) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		Person person = null;
				
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons  = gson.fromJson(personArray, personsListType);
			
			boolean exist = persons.stream()
									.anyMatch(p-> 
										p.getFirstName().equalsIgnoreCase(personCreate.getFirstName()) && 
										p.getLastName().equalsIgnoreCase(personCreate.getLastName()));
			
			if (!exist) {
				JsonElement personJson = gson.toJsonTree(personCreate);
				personArray.add(personJson);
				datas.getFileCache().add("persons", personArray);
				datas.writeJsonToFile();
				person = personCreate;
			}
			
			else {
				throw new EntityAlreadyExistException("Person already exist", Map.of("lastname", personCreate.getLastName(),
																					"fistname", personCreate.getFirstName()));
			}
		}
		
		return person;
	}
	
	public Person update(Person personUpdate)  {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		
				
		if (personArray != null){
			Gson gson = new Gson();
			
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons  = gson.fromJson(personArray, personsListType);
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
				JsonElement personsJson = gson.toJsonTree(persons);
				datas.getFileCache().add("persons", personsJson);
				datas.writeJsonToFile();
				return personFound.get();
			}
			
		}
		
		return null;
	}
	
	public void remove(String lastName, String firstName) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
				
		if (personArray != null){
			Gson gson = new Gson();
			
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons  = gson.fromJson(personArray, personsListType);
			List<Person> personsToKeep =  persons.stream()
										.filter(p -> !(p.getFirstName().equalsIgnoreCase(firstName)) 
												|| !(p.getLastName().equalsIgnoreCase(lastName)))
										.toList();
			
			if (persons.size() != personsToKeep.size()) {
				JsonElement personsJson = gson.toJsonTree(personsToKeep);
				datas.getFileCache().add("persons", personsJson);
				datas.writeJsonToFile();
			}
			else {
				throw new EntityNotFoundException("Person not found");
			}
			
		}
		
	}
	
	

	public List<Person> getPersonByLastName(String lastName) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> personsSelect = new ArrayList<Person>();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getLastName().equalsIgnoreCase(lastName))
									.collect(Collectors.toList());
		}
		
		
		
		return personsSelect;
	}

	public List<Person> getPersonByCity(String city) {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> personsSelect = new ArrayList<Person>();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getCity().equalsIgnoreCase(city))
									.collect(Collectors.toList());
		}
		
		return personsSelect;
	}
	
	
}

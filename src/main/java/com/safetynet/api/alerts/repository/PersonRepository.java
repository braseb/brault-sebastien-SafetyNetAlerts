package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.model.Person;

@Component
public class PersonRepository {
	
	@Autowired
	private JsonDatas datas;
	
	public PersonRepository() {
		
	}
	
	public List<Person> getPersonByName(String lastName, String firstName)  {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		
		List<Person> personsSelect = Collections.emptyList();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
									.collect(Collectors.toList());
		}	
		
		
		return personsSelect;
	}
		
	public List<Person> getPersonByAddress(String address)  {
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> personsSelect = Collections.emptyList();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			List<Person> persons = gson.fromJson(personArray, personsListType);
			personsSelect =	persons.stream()
									.filter(p-> p.getAddress().equals(address))
									.collect(Collectors.toList());
		}
		
		
		
		return personsSelect;
	}
	
	
	
	public List<Person> getAllPerson(){
		JsonArray personArray = datas.getFileCache().getAsJsonArray("persons");
		List<Person> persons = Collections.emptyList();
		
		if (personArray != null){
			Gson gson = new Gson();
			Type personsListType = new TypeToken<List<Person>>() {}.getType();
			persons = gson.fromJson(personArray, personsListType);
		}
				
		return persons;
	}
	
	
}

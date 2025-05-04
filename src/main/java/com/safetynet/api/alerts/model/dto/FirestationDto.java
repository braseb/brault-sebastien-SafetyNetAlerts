package com.safetynet.api.alerts.model.dto;

import java.util.ArrayList;
import java.util.List;

public class FirestationDto {
	private List<PersonDto> persons;
	private int numberChild;
	private int numberAdult;
	
	
	public FirestationDto() {
		super();
		numberAdult = 0;
		numberChild = 0;
		persons = new ArrayList<PersonDto>();
	}
	public List<PersonDto> getPersons() {
		return persons;
	}
	public void setPersons(List<PersonDto> persons) {
		this.persons = persons;
	}
	public int getNumberChild() {
		return numberChild;
	}
	public void setNumberChild(int numberChild) {
		this.numberChild = numberChild;
	}
	public int getNumberAdult() {
		return numberAdult;
	}
	public void setNumberAdult(int numberAdult) {
		this.numberAdult = numberAdult;
	}
	
}

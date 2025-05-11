package com.safetynet.api.alerts.model.dto;

import java.util.Collections;
import java.util.List;

public class FireDto {
	int stationNumber;
	List<PersonFullInfoDto> personsInfos;
	
	
	
	public FireDto() {
		super();
		stationNumber = -1;
		personsInfos = Collections.emptyList();
	}
	
	public int getStationNumber() {
		return stationNumber;
	}
	
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	public List<PersonFullInfoDto> getPersonsInfos() {
		return personsInfos;
	}
	
	public void addPersonFullInfo(PersonFullInfoDto personInfos) {
		personsInfos.add(personInfos);
	}
	
	public void setPersonFullInfo(List<PersonFullInfoDto> personsInfos) {
		this.personsInfos = personsInfos;
	}
	
	
}

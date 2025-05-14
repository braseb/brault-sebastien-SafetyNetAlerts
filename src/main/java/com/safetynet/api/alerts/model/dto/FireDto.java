package com.safetynet.api.alerts.model.dto;

import java.util.Collections;
import java.util.List;

public class FireDto {
	int stationNumber;
	List<PersonMedicalRecordWithPhoneDto> personsInfos;
	
	
	
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
	
	public List<PersonMedicalRecordWithPhoneDto> getPersonsInfos() {
		return personsInfos;
	}
	
	public void addPersonFullInfo(PersonMedicalRecordWithPhoneDto personInfos) {
		personsInfos.add(personInfos);
	}
	
	public void setPersonFullInfo(List<PersonMedicalRecordWithPhoneDto> personsInfos) {
		this.personsInfos = personsInfos;
	}
	
	
}

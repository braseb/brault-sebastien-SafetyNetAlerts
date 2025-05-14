package com.safetynet.api.alerts.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HouseholdDto {
	Map<String, List<PersonMedicalRecordWithPhoneDto>> households = new HashMap<String, List<PersonMedicalRecordWithPhoneDto>>();
	
	
	public Map<String, List<PersonMedicalRecordWithPhoneDto>> getHousehold() {
		return households;
	}

	public void setHousehold(Map<String, List<PersonMedicalRecordWithPhoneDto>> households) {
		this.households = households;
	}

	public void addPerson(String address, PersonMedicalRecordWithPhoneDto person) {
		households.computeIfAbsent(address, a -> new ArrayList<PersonMedicalRecordWithPhoneDto>()).add(person);
	}
	
	
	
	
	
	
}



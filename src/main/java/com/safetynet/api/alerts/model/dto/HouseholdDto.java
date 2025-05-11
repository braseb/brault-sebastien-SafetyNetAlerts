package com.safetynet.api.alerts.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HouseholdDto {
	Map<String, List<PersonFullInfoDto>> households = new HashMap<String, List<PersonFullInfoDto>>();
	
	
	public Map<String, List<PersonFullInfoDto>> getHousehold() {
		return households;
	}

	public void setHousehold(Map<String, List<PersonFullInfoDto>> households) {
		this.households = households;
	}

	public void addPerson(String address, PersonFullInfoDto person) {
		households.computeIfAbsent(address, a -> new ArrayList<PersonFullInfoDto>()).add(person);
	}
	
	
	
	
	
	
}



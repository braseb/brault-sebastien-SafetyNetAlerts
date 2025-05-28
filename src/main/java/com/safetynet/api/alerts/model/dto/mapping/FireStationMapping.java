package com.safetynet.api.alerts.model.dto.mapping;

import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.dto.FireStationCreateDto;
import com.safetynet.api.alerts.model.dto.FireStationUpdateDto;


public class FireStationMapping {
	public static FireStation mapToFireStation(FireStationCreateDto fireStationCreate) {
		FireStation fireStation = new FireStation();
		fireStation.setAddress(fireStationCreate.getAddress());
		fireStation.setStation(fireStationCreate.getStationNumber());
				
		return fireStation;
	}
	
	public static FireStationCreateDto mapToFireStationCreateDto(FireStation fireStation) {
		FireStationCreateDto fireStationCreate = new FireStationCreateDto(
															fireStation.getAddress(),
															fireStation.getStation());
		
		
		return fireStationCreate;
	}
	
	public static FireStation mapToFireStation(String address, FireStationUpdateDto fireStationUpdate) {
		FireStation fireStation = new FireStation();
		fireStation.setStation(fireStationUpdate.stationNumber());
		fireStation.setAddress(address);
				
		return fireStation;
	}
	
	public static FireStationUpdateDto mapToFireStationUpdateDto(FireStation fireStation) {
		FireStationUpdateDto fireStationUpdate = new FireStationUpdateDto(															
															fireStation.getStation());
		
		return fireStationUpdate;
	}
}

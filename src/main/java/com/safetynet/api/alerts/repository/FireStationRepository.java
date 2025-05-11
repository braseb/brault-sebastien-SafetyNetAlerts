package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.model.FireStation;

@Component
public class FireStationRepository {
	
	@Autowired
	private JsonDatas datas;
	
	public List<FireStation> getStationByStationNumber(int stationNumber) {
		List<FireStation> firestationSelect = Collections.emptyList();
		JsonArray firestationArray = datas.getFileCache().getAsJsonArray("firestations");
				
		if (firestationArray != null) {
			
			Gson gson = new Gson();
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations = gson.fromJson(firestationArray, typeListFirestation);
			
			firestationSelect = fireStations.stream()
										.filter(f -> f.getStation() == stationNumber)
										.toList();
			
		}
		
				
		return firestationSelect;
	}

	public List<FireStation> getStationNumberByAddress(String address) {
		List<FireStation> firestationSelect = Collections.emptyList();
		JsonArray firestationArray = datas.getFileCache().getAsJsonArray("firestations");
		if (firestationArray != null) {
					
			Gson gson = new Gson();
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations = gson.fromJson(firestationArray, typeListFirestation);
			
			firestationSelect = fireStations.stream()
											.filter(f -> f.getAddress().equals(address))
											.toList();
		
		}
	
		return firestationSelect;
	}
		
}

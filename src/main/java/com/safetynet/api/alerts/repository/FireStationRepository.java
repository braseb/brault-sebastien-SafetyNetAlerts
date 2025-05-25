package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.model.FireStation;


@Component
public class FireStationRepository {
	
	@Autowired
	private JsonDatas datas;
	
	public List<FireStation> getStationByStationNumber(int stationNumber) {
		List<FireStation> firestationSelect = new ArrayList<FireStation>();
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
	
	public List<FireStation> getStationByListOfStationNumber(List<Integer> stationNumbers) {
		List<FireStation> firestationSelect = new ArrayList<FireStation>();
		JsonArray firestationArray = datas.getFileCache().getAsJsonArray("firestations");
		System.out.println(firestationSelect);
				
		if (firestationArray != null) {
			
			Gson gson = new Gson();
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations = gson.fromJson(firestationArray, typeListFirestation);
			System.out.println(fireStations);
			
			for (Integer stationNumber : stationNumbers) {
				List<FireStation> firestationThisSelect = fireStations.stream()
															.filter(f -> f.getStation() == stationNumber.intValue())
															.toList();
				
				System.out.println(firestationThisSelect);
				firestationSelect.addAll(firestationThisSelect);
			}
			
			
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
											.filter(f -> f.getAddress().toUpperCase().equals(address.toUpperCase()))
											.toList();
		
		}
	
		return firestationSelect;
	}

	public boolean create(FireStation fireStation) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		boolean ret = false;
		
		if (fireStationArray != null){
			Gson gson = new Gson();
						
			JsonElement fireStationJson = gson.toJsonTree(fireStation);
			fireStationArray.add(fireStationJson);
			datas.getFileCache().add("firestations", fireStationArray);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}

	public boolean update(String address, Integer stationNumber) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		boolean ret = false;
		
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			fireStations.stream()
					.filter(f -> f.getAddress().toUpperCase().equals(address.toUpperCase()))
					.forEach(f -> f.setStation(stationNumber));
					
					
			
			JsonElement fireStationJson = gson.toJsonTree(fireStations);
			datas.getFileCache().add("firestations", fireStationJson);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}
	
	public boolean remove(Integer stationNumber){
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		boolean ret = false;
		
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			List<FireStation> fireStationsToKeep =  fireStations.stream()
										.filter(f -> !(f.getStation().equals(stationNumber)))
										.toList();
					
			
			JsonElement fireStationJson = gson.toJsonTree(fireStationsToKeep);
			datas.getFileCache().add("firestations", fireStationJson);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}

	public boolean remove(String address) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		boolean ret = false;
		
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			List<FireStation> fireStationsToKeep =  fireStations.stream()
										.filter(f -> !(f.getAddress().toUpperCase().equals(address.toUpperCase())))
										.toList();
					
			
			JsonElement fireStationJson = gson.toJsonTree(fireStationsToKeep);
			datas.getFileCache().add("firestations", fireStationJson);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}
		
}

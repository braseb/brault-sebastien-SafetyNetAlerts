package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
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
						
			for (Integer stationNumber : stationNumbers) {
				List<FireStation> firestationThisSelect = fireStations.stream()
															.filter(f -> f.getStation() == stationNumber.intValue())
															.toList();
								
				firestationSelect.addAll(firestationThisSelect);
			}
			
			
			
			
		}
		
				
		return firestationSelect;
	}

	public List<FireStation> getStationNumberByAddress(String address) {
		List<FireStation> fireStationSelect = new ArrayList<FireStation>();
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		if (fireStationArray != null) {
					
			Gson gson = new Gson();
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations = gson.fromJson(fireStationArray, typeListFirestation);
			
			fireStationSelect = fireStations.stream()
											.filter(f -> f.getAddress().toUpperCase().equals(address.toUpperCase()))
											.toList();
			
			
		
		}
	
		return fireStationSelect;
	}

	public FireStation create(FireStation fireStation) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		FireStation fireStationCreated = null;
				
		if (fireStationArray != null){
			Gson gson = new Gson();
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations = gson.fromJson(fireStationArray, typeListFirestation);
			
			boolean exist = fireStations.stream()
										.anyMatch(f-> 
							f.getAddress().equalsIgnoreCase(fireStation.getAddress()) && 
							f.getStation() == (fireStation.getStation()));
	
			if (!exist) {
				JsonElement fireStationJson = gson.toJsonTree(fireStation);
				fireStationArray.add(fireStationJson);
				datas.getFileCache().add("firestations", fireStationArray);
				datas.writeJsonToFile();
				fireStationCreated = fireStation;
				
			}
			else {
				throw new EntityAlreadyExistException("The fire station already exist", Map.of("address", fireStation.getAddress(),
																					"stationNumber", fireStation.getStation()));
			}
			
		}
		
		return fireStationCreated;
	}

	public FireStation update(String address, FireStation fireStation) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
				
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			Optional<FireStation> fireStationFound = fireStations.stream()
													.filter(f -> f.getAddress().equalsIgnoreCase(address.toUpperCase()))
													.peek(f -> f.setStation(fireStation.getStation()))
													.findAny();
								
			if (!fireStationFound.isPresent()) {
				throw new EntityNotFoundException("Fire station not found");
				
			}
			else {
				JsonElement fireStationJson = gson.toJsonTree(fireStations);
				datas.getFileCache().add("firestations", fireStationJson);
				datas.writeJsonToFile();
				return fireStationFound.get();
			}
			
		}
		
		return null;
	}
	
	public void remove(Integer stationNumber){
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
				
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			List<FireStation> fireStationsToKeep =  fireStations.stream()
										.filter(f -> !(f.getStation().equals(stationNumber)))
										.toList();
					
			if(fireStations.size() != fireStationsToKeep.size()) {
				JsonElement fireStationJson = gson.toJsonTree(fireStationsToKeep);
				datas.getFileCache().add("firestations", fireStationJson);
				datas.writeJsonToFile();
			}
			else {
				throw new EntityNotFoundException("Fire station not found");
			}
			
		}
		
	}

	public void remove(String address) {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
				
		if (fireStationArray != null){
			Gson gson = new Gson();
			
			Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
			List<FireStation> fireStations  = gson.fromJson(fireStationArray, typeListFirestation);
			List<FireStation> fireStationsToKeep =  fireStations.stream()
										.filter(f -> !(f.getAddress().toUpperCase().equals(address.toUpperCase())))
										.toList();
			
			if(fireStations.size() != fireStationsToKeep.size()) {
				JsonElement fireStationJson = gson.toJsonTree(fireStationsToKeep);
				datas.getFileCache().add("firestations", fireStationJson);
				datas.writeJsonToFile();
			}
			else {
				throw new EntityNotFoundException("Fire station not found");
			}
			
		}
		
	}
		
}

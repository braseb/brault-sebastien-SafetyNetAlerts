package com.safetynet.api.alerts.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.exceptions.JsonDataException;
import com.safetynet.api.alerts.model.FireStation;



@Component
public class FireStationRepository {
	
	@Autowired
	private JsonDatas datas;
	
	private final Logger log = LogManager.getLogger();
	
	private List<FireStation> getDatasFromJson() {
		JsonArray fireStationArray = datas.getFileCache().getAsJsonArray("firestations");
		if (fireStationArray != null) {
			try {
				Gson gson = new Gson();
				Type typeListFirestation = new TypeToken<List<FireStation>>() {}.getType();
				List<FireStation> fireStations = gson.fromJson(fireStationArray, typeListFirestation);
				return fireStations;
			} catch (Exception e) {
				log.error("Unable to convert the fireStations key to list", e, fireStationArray);
				throw new JsonDataException("Unable to get fireStation datas");
			}
		}
		else {
			log.error("Unable to get the fireStations key", fireStationArray);
			throw new JsonDataException("Unable to get fireStation datas");
			
		}
		
	}
	
	private void setDatasFromJson(List<FireStation> fireStations) {
		Gson gson = new Gson();
		JsonObject jsonCache = datas.getFileCache();
		if (jsonCache != null) {
			try {
				JsonElement fireStationsJson = gson.toJsonTree(fireStations);
				jsonCache.add("firestations", fireStationsJson);
				datas.writeJsonToFile();
			} catch (Exception e) {
				log.error("Unable to set fireStation datas", e);
				throw new JsonDataException("Unable to set fireStations key");
			}
			
		}
		else {
			log.error("Unable to get the json cache", jsonCache);
			throw new JsonDataException("Unable to set fireStation datas");
			
		}
		
	}
	
	public List<FireStation> getStationByStationNumber(int stationNumber) {
		List<FireStation> firestationSelect = new ArrayList<FireStation>();
		List<FireStation> fireStations = getDatasFromJson();
			
		firestationSelect = fireStations.stream()
									.filter(f -> f.getStation() == stationNumber)
									.toList();
				
		return firestationSelect;
	}
	
	public List<FireStation> getStationByListOfStationNumber(List<Integer> stationNumbers) {
		List<FireStation> firestationSelect = new ArrayList<FireStation>();
		
		List<FireStation> fireStations = getDatasFromJson();
					
		for (Integer stationNumber : stationNumbers) {
			List<FireStation> firestationThisSelect = fireStations.stream()
														.filter(f -> f.getStation() == stationNumber.intValue())
														.toList();
							
			firestationSelect.addAll(firestationThisSelect);
		}
				
		return firestationSelect;
	}

	public List<FireStation> getStationNumberByAddress(String address) {
		List<FireStation> fireStationSelect = new ArrayList<FireStation>();
		List<FireStation> fireStations = getDatasFromJson();
			
			fireStationSelect = fireStations.stream()
											.filter(f -> f.getAddress().equalsIgnoreCase(address))
											.toList();
	
		return fireStationSelect;
	}

	public FireStation create(FireStation fireStation) {
		FireStation fireStationCreated = null;
				
		List<FireStation> fireStations = getDatasFromJson();
			
		boolean exist = fireStations.stream()
									.anyMatch(f-> 
						f.getAddress().equalsIgnoreCase(fireStation.getAddress()) && 
						f.getStation() == (fireStation.getStation()));

		if (!exist) {
			//JsonElement fireStationJson = gson.toJsonTree(fireStation);
			//fireStationArray.add(fireStationJson);
			//datas.getFileCache().add("firestations", fireStationArray);
			//datas.writeJsonToFile();
			fireStations.add(fireStation);
			setDatasFromJson(fireStations);
			fireStationCreated = fireStation;
			
		}
		else {
			throw new EntityAlreadyExistException("The fire station already exist", Map.of("address", fireStation.getAddress(),
																				"stationNumber", fireStation.getStation()));
		}
	
		return fireStationCreated;
	}

	public FireStation update(FireStation fireStation) {
		List<FireStation> fireStations = getDatasFromJson();
		Optional<FireStation> fireStationFound = fireStations.stream()
												.filter(f -> f.getAddress().equalsIgnoreCase(fireStation.getAddress()))
												.peek(f -> f.setStation(fireStation.getStation()))
												.findAny();
							
		if (fireStationFound.isEmpty()) {
			throw new EntityNotFoundException("Fire station not found");
			
		}
		else {
			setDatasFromJson(fireStations);
			return fireStationFound.get();
		}
	}
	
	public void remove(Integer stationNumber){
		List<FireStation> fireStations = getDatasFromJson();
		List<FireStation> fireStationsToKeep =  fireStations.stream()
									.filter(f -> !(f.getStation().equals(stationNumber)))
									.toList();
				
		if(fireStations.size() != fireStationsToKeep.size()) {
			setDatasFromJson(fireStationsToKeep);
		}
		else {
			throw new EntityNotFoundException("Fire station not found");
		}
		
	}

	public void remove(String address) {
		List<FireStation> fireStations = getDatasFromJson();
		List<FireStation> fireStationsToKeep =  fireStations.stream()
									.filter(f -> !(f.getAddress().equalsIgnoreCase(address)))
									.toList();
		
		if(fireStations.size() != fireStationsToKeep.size()) {
			setDatasFromJson(fireStationsToKeep);
		}
		else {
			throw new EntityNotFoundException("Fire station not found");
		}
		
	}
		
}

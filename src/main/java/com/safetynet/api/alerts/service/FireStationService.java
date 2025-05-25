package com.safetynet.api.alerts.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.repository.FireStationRepository;


@Service
public class FireStationService {
	
	@Autowired
	private FireStationRepository fireStationRepository;
	
	public List<String> getAddressByStationNumber(int stationnumber) {
		List<FireStation> listFireStation = fireStationRepository.getStationByStationNumber(stationnumber);
				
		List<String> address  = listFireStation.stream()
												.map(f -> f.getAddress())
												.collect(Collectors.toList());
				
		return address;
	}
	
	public List<String> getAddressByListOfStationNumber(List<Integer> stationNumbers) {
		List<FireStation> listFireStation = fireStationRepository.getStationByListOfStationNumber(stationNumbers);
				
		List<String> address  = listFireStation.stream()
												.map(f -> f.getAddress())
												.distinct()
												.collect(Collectors.toList());
												
				
		return address;
	}
	
	public Integer getStationNumberByAddress(String address) {
		List<FireStation> listFireStation = fireStationRepository.getStationNumberByAddress(address);
		System.out.println(listFireStation);
		Optional<Integer> stationNumber  = listFireStation.stream()
												.map(f -> f.getStation())
												.findFirst();
			
		return stationNumber.orElse(null);
	}
	
	public boolean createFireStation(FireStation fireStation) {		
		return fireStationRepository.create(fireStation);
	}
	
	public boolean updateFireStation(String address, Integer stationNumber) {
		return fireStationRepository.update(address,stationNumber);
	}
	
	public boolean removeFireStationByNumber(Integer stationNumber) {
		return fireStationRepository.remove(stationNumber);
	}
	
	public boolean removeFireStationByAddress(String address) {
		return fireStationRepository.remove(address);
	}
}

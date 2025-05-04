package com.safetynet.api.alerts.service;

import java.util.List;
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
}

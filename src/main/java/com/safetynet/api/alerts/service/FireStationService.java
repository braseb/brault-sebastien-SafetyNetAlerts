package com.safetynet.api.alerts.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundExceptionWithReturn;
import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.dto.FireStationCreateDto;
import com.safetynet.api.alerts.model.dto.FireStationUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.FireStationMapping;
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
		if (address.isEmpty()) {
			throw new EntityNotFoundExceptionWithReturn("No Address found with this station number", stationnumber);
		}
		
		return address;
	}
	
	public List<String> getAddressByListOfStationNumber(List<Integer> stationNumbers) {
		List<FireStation> listFireStation = fireStationRepository.getStationByListOfStationNumber(stationNumbers);
				
		List<String> address  = listFireStation.stream()
												.map(f -> f.getAddress())
												.distinct()
												.collect(Collectors.toList());
		if (address.isEmpty()) {
			throw new EntityNotFoundException("Fire station not found");
		}										
				
		return address;
	}
	
	public Integer getStationNumberByAddress(String address) {
		List<FireStation> listFireStation = fireStationRepository.getStationNumberByAddress(address);
		System.out.println(listFireStation);
		Optional<Integer> stationNumber  = listFireStation.stream()
												.map(f -> f.getStation())
												.findAny();
		if (stationNumber.isEmpty()) {
			throw new EntityNotFoundExceptionWithReturn("Fire station number not found", address);
		}
		
		return stationNumber.orElse(null);
	}
	
	public FireStationCreateDto createFireStation(FireStationCreateDto fireStationCreate) {		
		
		try {
			FireStation fireStation = FireStationMapping.mapToFireStation(fireStationCreate);
			return FireStationMapping.mapToFireStationCreateDto(fireStationRepository.create(fireStation));
		} catch (EntityAlreadyExistException e) {
			throw e;
		}
		
	}
	
	public FireStationUpdateDto updateFireStation(String address, FireStationUpdateDto fireStationUpdate) {
		try {
			FireStation fireStation = FireStationMapping.mapToFireStation(address, fireStationUpdate);
			return FireStationMapping.mapToFireStationUpdateDto(fireStationRepository.update(fireStation));
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}
		
	
	public void removeFireStationByNumber(Integer stationNumber) {
		try {
			fireStationRepository.remove(stationNumber);
		} catch (EntityNotFoundException e) {
			throw e;
		}
		
	}
	
	public void removeFireStationByAddress(String address) {
		try {
			fireStationRepository.remove(address);
		} catch (EntityNotFoundException e) {
			throw e;
		}
		
	}
}

package com.safetynet.api.alerts.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FireDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.HouseholdDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithEmailDto;
import com.safetynet.api.alerts.service.AlertService;

@RestController
public class AlertController {
		
	@Autowired
	private AlertService alertService;
	
	@GetMapping("/childAlert")
	public List<ChildAlertDto> getChildAlert(@RequestParam("address") final String address){
		
		return alertService.getChildrenByAdress(address);
		
	}
	
	@GetMapping("/firestation")
	public FirestationDto getPersonCoveredByFireStation(@RequestParam("stationNumber") final int stationNumber){
		return alertService.getPersonCoveredByFireStation(stationNumber);
	}
	
	@GetMapping("/phoneAlert")
	public List<String> getPhoneAlert(@RequestParam int firestation) {
		return alertService.getPhoneAlert(firestation);
	}
	
	@GetMapping("/fire")
	public FireDto getCasernNumberAndPersonsByAddress(@RequestParam String address){
		return alertService.getFirestationNumberAndPersonsByAddress(address);
	}
	
	@GetMapping("/flood/stations")
	public HouseholdDto getHousehold(@RequestParam(name = "stations") List<Integer>  stations) {
		return alertService.getHouseholdsByStationNumbers(stations);
	}
	
	
	@GetMapping("/personInfo")
	public List<PersonMedicalRecordWithEmailDto> getPersoninfo(@RequestParam String  lastName) {
		return alertService.getPersonMedicalRecordWithEmailByLastName(lastName);
	}
	
	@GetMapping("/communityEmail")
	public List<String> getEmaiFromCity(@RequestParam String  city) {
		return alertService.getAllEmailByCity(city);
	}
	
	
	
}

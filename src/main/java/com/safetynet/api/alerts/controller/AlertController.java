package com.safetynet.api.alerts.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.alerts.model.PersonMedicalRecord;
import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
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
}

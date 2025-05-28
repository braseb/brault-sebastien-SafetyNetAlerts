package com.safetynet.api.alerts.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public class FireStationCreateDto{
	
	private @NotBlank(message = "the address is necessary") String address;
	private @Min(value = 1) int stationNumber;
		
	public FireStationCreateDto(String address,
							int stationNumber) {
		super();
		this.address = address;
		this.stationNumber = stationNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

}



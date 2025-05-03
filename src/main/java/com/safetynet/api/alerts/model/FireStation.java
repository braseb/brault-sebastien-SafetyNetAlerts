package com.safetynet.api.alerts.model;

import org.springframework.stereotype.Component;

@Component
public class FireStation {
	String address;
	Integer station;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStation() {
		return station;
	}
	public void setStation(Integer station) {
		this.station = station;
	}
	
}

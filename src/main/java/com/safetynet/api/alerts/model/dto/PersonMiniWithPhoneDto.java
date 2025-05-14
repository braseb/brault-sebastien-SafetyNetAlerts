package com.safetynet.api.alerts.model.dto;

public class PersonMiniWithPhoneDto extends PersonMiniDto{
	String phone;

	public PersonMiniWithPhoneDto(String lastName, String firstName, String address, String phone) {
		super(lastName, firstName, address);
		this.phone = phone;
	}
	
	public PersonMiniWithPhoneDto() {
		super();
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}

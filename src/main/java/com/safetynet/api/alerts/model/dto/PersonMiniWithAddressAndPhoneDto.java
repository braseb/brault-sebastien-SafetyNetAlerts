package com.safetynet.api.alerts.model.dto;

public class PersonMiniWithAddressAndPhoneDto extends PersonMiniDto{
	String address;
	String phone;

	public PersonMiniWithAddressAndPhoneDto(String lastName, String firstName, String address, String phone) {
		super(lastName, firstName);
		this.phone = phone;
		this.address = address;
	}
	
	public PersonMiniWithAddressAndPhoneDto() {
		super();
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}

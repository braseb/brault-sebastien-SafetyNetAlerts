package com.safetynet.api.alerts.model.dto;

public class PersonDto extends PersonMiniDto{
	
	private int age;
		
	
	public PersonDto(String lastName, String firstName, String address, int age) {
		super(lastName,firstName,address);
		this.age = age;
	}
	
	public PersonDto() {
		super();
		
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}

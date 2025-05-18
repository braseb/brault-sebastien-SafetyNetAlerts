package com.safetynet.api.alerts.model.dto;

public class PersonDto extends PersonMiniDto{
	
	private Integer age;
		
	
	public PersonDto(String lastName, String firstName, String address, Integer age) {
		super(lastName,firstName,address);
		this.age = age;
	}
	
	public PersonDto() {
		super();
		
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
}

package com.safetynet.api.alerts.model.dto;

public class PersonDto extends PersonMiniDto{
	
	private Integer age;
		
	
	public PersonDto(String lastName, String firstName, Integer age) {
		super(lastName,firstName);
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

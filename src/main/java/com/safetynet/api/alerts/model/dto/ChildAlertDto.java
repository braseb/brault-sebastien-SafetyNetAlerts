package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class ChildAlertDto {
	private String lastName;
	private String firstName;
	private Integer age;
	private List<MemberHouseholdDto> membersHousehold;
		
	public ChildAlertDto(String lastName, String firstName, Integer age, List<MemberHouseholdDto> membersHousehold) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
		this.membersHousehold = membersHousehold;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public List<MemberHouseholdDto> getMembersHousehold() {
		return membersHousehold;
	}
	public void setMembersHousehold(List<MemberHouseholdDto> membersHousehold) {
		this.membersHousehold = membersHousehold;
	}
	
	public void addMembersHousehold(MemberHouseholdDto membersHousehold) {
		this.membersHousehold.add(membersHousehold);
	}
	
	
}

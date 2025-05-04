package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class ChildAlertDto {
	private String lastName;
	private String firstName;
	private int age;
	private List<MemberHousehold> membersHousehold;
		
	public ChildAlertDto(String lastName, String firstName, int age, List<MemberHousehold> membersHousehold) {
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
	
	public List<MemberHousehold> getMembersHousehold() {
		return membersHousehold;
	}
	public void setMembersHousehold(List<MemberHousehold> membersHousehold) {
		this.membersHousehold = membersHousehold;
	}
	
	public void addMembersHousehold(MemberHousehold membersHousehold) {
		this.membersHousehold.add(membersHousehold);
	}
	
	
}

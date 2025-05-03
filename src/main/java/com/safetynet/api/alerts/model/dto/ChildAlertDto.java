package com.safetynet.api.alerts.model.dto;

import java.util.List;

public class ChildAlertDto {
	private String lastName;
	private String firstName;
	private int age;
	private List<String> memberHousehold;
	
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
	public List<String> getMemberHousehold() {
		return memberHousehold;
	}
	public void setMemberHousehold(List<String> memberHousehold) {
		this.memberHousehold = memberHousehold;
	}
	
	public void addMemberHousehold(String memberHousehold) {
		this.memberHousehold.add(memberHousehold);
	}
	
	
}

package com.safetynet.api.alerts.model;



public class PersonMedicalRecord {
	
	private Person person;
	private MedicalRecord medicalRecord;
	
	public PersonMedicalRecord(Person person, MedicalRecord medicalRecord) {
		super();
		this.person = person;
		this.medicalRecord = medicalRecord;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	
	
}

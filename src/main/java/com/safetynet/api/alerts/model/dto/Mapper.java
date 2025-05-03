package com.safetynet.api.alerts.model.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.PersonMedicalRecord;

@Component
public class Mapper {
	public ChildAlertDto toChilAlertDto(PersonMedicalRecord personMedicalRecord) {
		ChildAlertDto childAlertDto = new ChildAlertDto();
		Person person = personMedicalRecord.getPerson();
		
		childAlertDto.setLastName(person.getLastName());
		childAlertDto.setFirstName(person.getFirstName());
		
		LocalDate birthDate = LocalDate.parse(personMedicalRecord.getMedicalRecord().getBirthdate()
											,DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.FRANCE));
		
		int age = Period.between(birthDate ,LocalDate.now()).getYears();
		
		childAlertDto.setAge(age);
		return childAlertDto;
		
	}
}

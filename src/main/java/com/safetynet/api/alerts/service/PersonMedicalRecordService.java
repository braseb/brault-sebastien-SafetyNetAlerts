package com.safetynet.api.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.PersonMedicalRecord;
import com.safetynet.api.alerts.repository.MedicalRecordRepository;
import com.safetynet.api.alerts.repository.PersonRepository;

@Service
public class PersonMedicalRecordService {
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	public List<PersonMedicalRecord> getPersonMedicalRecord(String address) throws Exception{
		List<Person> listPerson = personRepository.getPersonByAddress(address);
		List<MedicalRecord> listMedicalRecord = medicalRecordRepository.getAllMedicalRecord();
						
		List<PersonMedicalRecord> listPersonMedicalRecord = listPerson.stream()
																		.map(p -> {MedicalRecord med =  listMedicalRecord.stream()
																					.filter(m -> m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName()))
																					.findFirst()
																					.orElse(null);
																					return new PersonMedicalRecord(p, med);
																					})
																		.collect(Collectors.toList());
		
		
		List<PersonMedicalRecord> listChildAlert = listPersonMedicalRecord.stream()
																			.filter(p -> Period.between(LocalDate.parse(
																						p.getMedicalRecord().getBirthdate(),
																						DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))
																					,LocalDate.now()).getYears() < 18)
																			.collect(Collectors.toList());
																			
																	
		
		return listChildAlert;
		
	}
	
}

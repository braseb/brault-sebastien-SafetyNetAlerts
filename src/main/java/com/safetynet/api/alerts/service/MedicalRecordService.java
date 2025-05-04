package com.safetynet.api.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.repository.MedicalRecordRepository;


@Service
public class MedicalRecordService {

    
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

    
	
	public int getAge(String lastName, String firstName) {
		Optional<MedicalRecord> optMedicalRecord = medicalRecordRepository.getMedicalRecordByName(lastName, firstName);
		
		int age = -1;
		
		if (optMedicalRecord.isPresent()){
			MedicalRecord medicalRecord = optMedicalRecord.get();
			age = Period.between(LocalDate.parse(
					medicalRecord.getBirthdate(),
					DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))
				,LocalDate.now()).getYears();
			
			
		}
		return age;
		
	}
	
	
		
	
}

package com.safetynet.api.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.dto.MedicalRecordCreateDto;
import com.safetynet.api.alerts.model.dto.MedicalRecordUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.MedicalRecordMapping;
import com.safetynet.api.alerts.repository.MedicalRecordRepository;


@Service
public class MedicalRecordService {

	private static final Logger log  = LogManager.getLogger();
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

    
	
	public Integer getAge(String lastName, String firstName) {
		Optional<MedicalRecord> optMedicalRecord = medicalRecordRepository.getMedicalRecordByName(lastName, firstName);
		
		Integer age = null;
		
		if (optMedicalRecord.isPresent()){
			MedicalRecord medicalRecord = optMedicalRecord.get();
			age = Period.between(LocalDate.parse(
					medicalRecord.getBirthdate(),
					DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))
				,LocalDate.now()).getYears();
			
			
		}
		return age;
		
	}
	
	public Integer getAge(MedicalRecord medicalRecord){
		Integer age = null;
		
		try {
			age = Period.between(LocalDate.parse(
					medicalRecord.getBirthdate(),
					DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US))
				,LocalDate.now()).getYears();
		} catch (Exception e) {
			log.error("Date Format incorect, it must be 'MM/dd/yyy'");
		}
		
		
		return age;
	}
	
	public MedicalRecord getMedicalRecordByName(String lastName, String firstName){
		Optional<MedicalRecord> optMedicalRecord = medicalRecordRepository.getMedicalRecordByName(lastName, firstName);
		return optMedicalRecord.orElse(new MedicalRecord());
		
	}
	
	public MedicalRecordCreateDto createMedicalRecord(MedicalRecordCreateDto medicalRecordCreate){		
		try {
			MedicalRecord medicalRecord = MedicalRecordMapping.mapToMedicalRecord(medicalRecordCreate);
			return MedicalRecordMapping.mapToMedicalRecordCreateDto(medicalRecordRepository.create(medicalRecord));
		} catch (EntityAlreadyExistException e) {
			throw e;
		}
		
	}
	
	public MedicalRecordUpdateDto updateMedicalRecord(String lastName, String firstName, MedicalRecordUpdateDto medicalRecordUpdate){
		try {
			MedicalRecord medicalRecord = MedicalRecordMapping.mapToMedicalRecord(lastName, firstName, medicalRecordUpdate);
			return MedicalRecordMapping.mapToMedicalRecordUpdateDto(medicalRecordRepository.update(medicalRecord));
		} catch (EntityNotFoundException e) {
			throw e;
		}
		
	}
	
	public void removeMedicalRecord(String lastName, String firstName){
		try {
			medicalRecordRepository.remove(lastName, firstName);
		} catch (EntityNotFoundException e) {
			throw e;
		}
		
	}
	
	
	
	
		
	
}

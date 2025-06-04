package com.safetynet.api.alerts.repository;


import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.exceptions.JsonDataException;
import com.safetynet.api.alerts.model.MedicalRecord;



@Component
public class MedicalRecordRepository {
	
	@Autowired
	private JsonDatas datas;
	
	private final Logger log = LogManager.getLogger();
	
	private List<MedicalRecord> getDatasFromJson() {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		if (medicalRecordArray != null) {
			try {
				Gson gson = new Gson();
				Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
				List<MedicalRecord> medicalRecords = gson.fromJson(medicalRecordArray, typeListMedicalRecord);
				return medicalRecords;
			} catch (Exception e) {
				log.error("Unable to convert the medicalrecords key to list", e, medicalRecordArray);
				throw new JsonDataException("Unable to get medicalrecords datas");
			}
		}
		else {
			log.error("Unable to get the fireStations key", medicalRecordArray);
			throw new JsonDataException("Unable to get medicalrecords datas");
			
		}
		
	}
	
	private void setDatasFromJson(List<MedicalRecord> medicalRecords) {
		Gson gson = new Gson();
		JsonObject jsonCache = datas.getFileCache();
		if (jsonCache != null) {
			try {
				JsonElement medicalRecordsJson = gson.toJsonTree(medicalRecords);
				jsonCache.add("medicalrecords", medicalRecordsJson);
				datas.writeJsonToFile();
			} catch (Exception e) {
				log.error("Unable to set medicalrecords datas", e);
				throw new JsonDataException("Unable to set medicalrecords key");
			}
			
		}
		else {
			log.error("Unable to get the json cache", jsonCache);
			throw new JsonDataException("Unable to set medicalrecords datas");
			
		}
		
	}
	
	
	public List<MedicalRecord> getAllMedicalRecord() {
		List<MedicalRecord> medicalRecords = getDatasFromJson();
		return medicalRecords;
	}
	
	public Optional<MedicalRecord> getMedicalRecordByName(String lastName, String firstName) {
		Optional<MedicalRecord> medicalRecordsSelect = null;
		List<MedicalRecord> medicalRecords = getDatasFromJson();
			
		medicalRecordsSelect = medicalRecords.stream()
													.filter(m -> m.getLastName().equalsIgnoreCase(lastName) 
															&& m.getFirstName().equalsIgnoreCase(firstName))
													.findFirst();
				
		return medicalRecordsSelect;
	}


	public MedicalRecord create(MedicalRecord medicalRecordCreate) {
		List<MedicalRecord> medicalRecords = getDatasFromJson();
		MedicalRecord medicalRecord = null;
					
		boolean exist = medicalRecords.stream()
							.anyMatch(m -> m.getLastName().equalsIgnoreCase(medicalRecordCreate.getLastName()) 
									&& m.getFirstName().equalsIgnoreCase(medicalRecordCreate.getFirstName()));
			
		if (!exist) {
			medicalRecords.add(medicalRecordCreate);
			setDatasFromJson(medicalRecords);
			medicalRecord = medicalRecordCreate;
		}
		else {
			throw new EntityAlreadyExistException("Medical record already exist", 
					Map.of("lastname", medicalRecordCreate.getLastName(),
							"fistname", medicalRecordCreate.getFirstName()));
		}
		
		return medicalRecord;
	}


	public MedicalRecord update(MedicalRecord medicalRecord) {
		List<MedicalRecord> medicalRecords = getDatasFromJson();
				
		Optional<MedicalRecord> medicalRecordFound = medicalRecords.stream()
								.filter(m -> m.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()) 
										&& m.getLastName().equalsIgnoreCase(medicalRecord.getLastName()))
								.peek(m -> {m.setBirthdate(medicalRecord.getBirthdate());
												m.setMedications(medicalRecord.getMedications());
												m.setAllergies(medicalRecord.getAllergies());})
								.findFirst();
		
		if (medicalRecordFound.isEmpty()) {
			throw new EntityNotFoundException("Medical record is not found");
		}
		else {
			setDatasFromJson(medicalRecords);
			return medicalRecordFound.get();
		}
		
	}


	public void remove(String lastName, String firstName) {
		List<MedicalRecord> medicalRecords = getDatasFromJson();
				
		List<MedicalRecord> medicalRecordsToKeep = medicalRecords.stream()
									.filter(m -> !m.getFirstName().equalsIgnoreCase(firstName) 
											|| !m.getLastName().equalsIgnoreCase(lastName))
									.toList();
		
		if (medicalRecords.size() != medicalRecordsToKeep.size()) {
			setDatasFromJson(medicalRecordsToKeep);
		}
		else {
			throw new EntityNotFoundException("Medical record is not found");
		}
		
	}
}

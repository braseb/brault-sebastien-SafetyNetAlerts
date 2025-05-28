package com.safetynet.api.alerts.repository;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.exceptions.EntityAlreadyExistException;
import com.safetynet.api.alerts.exceptions.EntityNotFoundException;
import com.safetynet.api.alerts.model.MedicalRecord;



@Component
public class MedicalRecordRepository {
	
	@Autowired
	private JsonDatas datas;
	
	public MedicalRecordRepository() {
		
	}
	
	
	public List<MedicalRecord> getAllMedicalRecord() {
		JsonArray medicalRecordsArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		if (medicalRecordsArray != null) {
			Gson gson = new Gson();
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			medicalRecords = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
		}
		
				
		return medicalRecords;
	}
	
	public Optional<MedicalRecord> getMedicalRecordByName(String lastName, String firstName) {
		JsonArray medicalRecordsArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		
		Optional<MedicalRecord> medicalRecordsSelect = null;
		if (medicalRecordsArray != null) {
			Gson gson = new Gson();
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> listMedicalRecord = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
			
			medicalRecordsSelect = listMedicalRecord.stream()
														.filter(m -> m.getLastName().toUpperCase().equals(lastName.toUpperCase()) 
																&& m.getFirstName().toUpperCase().equals(firstName.toUpperCase()))
														.findFirst();
			
		}
		
				
		return medicalRecordsSelect;
	}


	public MedicalRecord create(MedicalRecord medicalRecordCreate) {
		JsonArray medicalRecordsArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		MedicalRecord medicalRecord = null;
				
		if (medicalRecordsArray != null){
			Gson gson = new Gson();
			JsonElement medicalRecordJson = gson.toJsonTree(medicalRecordCreate);
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> listMedicalRecord = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
			
			boolean exist = listMedicalRecord.stream()
								.anyMatch(m -> m.getLastName().equalsIgnoreCase(medicalRecordCreate.getLastName()) 
										&& m.getFirstName().equalsIgnoreCase(medicalRecordCreate.getFirstName()));
			
			if (!exist) {
				medicalRecordsArray.add(medicalRecordJson);
				datas.getFileCache().add("medicalrecords", medicalRecordsArray);
				datas.writeJsonToFile();
				medicalRecord = medicalRecordCreate;
			}
			else {
				throw new EntityAlreadyExistException("Medical record already exist", 
						Map.of("lastname", medicalRecordCreate.getLastName(),
								"fistname", medicalRecordCreate.getFirstName()));
			}
		}
		return medicalRecord;
	}


	public MedicalRecord update(MedicalRecord medicalRecord) {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
				
		if (medicalRecordArray != null){
			Gson gson = new Gson();
			
			Type medicalRecordListType = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> medicalRecords  = gson.fromJson(medicalRecordArray, medicalRecordListType);
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
				JsonElement medicalRecordsJson = gson.toJsonTree(medicalRecords);
				datas.getFileCache().add("medicalrecords", medicalRecordsJson);
				datas.writeJsonToFile();
				return medicalRecordFound.get();
			}
			
		}
		return null;
		
	}


	public void remove(String lastName, String firstName) {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
				
		if (medicalRecordArray != null){
			Gson gson = new Gson();
			
			Type medicalRecordListType = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> medicalRecords  = gson.fromJson(medicalRecordArray, medicalRecordListType);
			List<MedicalRecord> medicalRecordsToKeep = medicalRecords.stream()
										.filter(m -> !m.getFirstName().toUpperCase().equals(firstName.toUpperCase()) 
												&& !m.getLastName().toUpperCase().equals(lastName.toUpperCase()))
										.toList();
			
			if (medicalRecords.size() != medicalRecordsToKeep.size()) {
				JsonElement medicalRecordsJson = gson.toJsonTree(medicalRecordsToKeep);
				datas.getFileCache().add("medicalrecords", medicalRecordsJson);
				datas.writeJsonToFile();
			}
			else {
				throw new EntityNotFoundException("Medical record is not found");
			}
		}
	}
}

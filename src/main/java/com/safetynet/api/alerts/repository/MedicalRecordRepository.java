package com.safetynet.api.alerts.repository;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.safetynet.api.alerts.datas.JsonDatas;
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


	public boolean create(MedicalRecord medicalRecord) {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		boolean ret = false;
		
		if (medicalRecordArray != null){
			Gson gson = new Gson();
			JsonElement medicalRecordJson = gson.toJsonTree(medicalRecord);
			medicalRecordArray.add(medicalRecordJson);
			datas.getFileCache().add("medicalrecords", medicalRecordArray);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}


	public boolean update(String lastName, String firstName, MedicalRecord medicalRecord) {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		boolean ret = false;
		
		if (medicalRecordArray != null){
			Gson gson = new Gson();
			
			Type medicalRecordListType = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> medicalRecords  = gson.fromJson(medicalRecordArray, medicalRecordListType);
			medicalRecords.stream()
					.filter(m -> m.getFirstName().toUpperCase().equals(firstName.toUpperCase()) 
							&& m.getLastName().toUpperCase().equals(lastName.toUpperCase()))
					.forEach(m -> {m.setBirthdate(medicalRecord.getBirthdate());
									m.setMedications(medicalRecord.getMedications());
									m.setAllergies(medicalRecord.getAllergies());});
			
			JsonElement medicalRecordsJson = gson.toJsonTree(medicalRecords);
			datas.getFileCache().add("medicalrecords", medicalRecordsJson);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}


	public boolean remove(String lastName, String firstName) {
		JsonArray medicalRecordArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		boolean ret = false;
		
		if (medicalRecordArray != null){
			Gson gson = new Gson();
			
			Type medicalRecordListType = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> medicalRecords  = gson.fromJson(medicalRecordArray, medicalRecordListType);
			List<MedicalRecord> medicalRecordsToKeep = medicalRecords.stream()
										.filter(m -> !m.getFirstName().toUpperCase().equals(firstName.toUpperCase()) 
												&& !m.getLastName().toUpperCase().equals(lastName.toUpperCase()))
										.toList();
			
			JsonElement medicalRecordsJson = gson.toJsonTree(medicalRecordsToKeep);
			datas.getFileCache().add("medicalrecords", medicalRecordsJson);
			ret = datas.writeJsonToFile();
			
			
		}
		
		return ret;
	}
}

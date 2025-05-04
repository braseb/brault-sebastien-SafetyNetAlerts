package com.safetynet.api.alerts.repository;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
		List<MedicalRecord> medicalRecords = Collections.emptyList();
		if (medicalRecordsArray != null) {
			Gson gson = new Gson();
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			medicalRecords = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
		}
		
				
		return medicalRecords;
	}
	
	public Optional<MedicalRecord> getMedicalRecordByName(String lastName, String firstName)  {
		JsonArray medicalRecordsArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		Optional<MedicalRecord> medicalRecordsSelect = null;
		if (medicalRecordsArray != null) {
			Gson gson = new Gson();
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			List<MedicalRecord> listMedicalRecord = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
			
			medicalRecordsSelect = listMedicalRecord.stream()
														.filter(m -> m.getLastName().equals(lastName) && m.getFirstName().equals(firstName))
														.findFirst();
			
		}
		
				
		return medicalRecordsSelect;
	}
}

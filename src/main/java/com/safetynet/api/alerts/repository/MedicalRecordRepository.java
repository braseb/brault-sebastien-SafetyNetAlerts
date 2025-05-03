package com.safetynet.api.alerts.repository;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

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
		//gson = new Gson();
		//JsonReader reader = new JsonReader(new FileReader(fileJsonPath));
		//fileCache = JsonParser.parseReader(reader).getAsJsonObject();
	}
	
	
	public List<MedicalRecord> getAllMedicalRecord() throws IOException {
		JsonArray medicalRecordsArray = datas.getFileCache().getAsJsonArray("medicalrecords");
		List<MedicalRecord> medicalRecords = Collections.emptyList();
		if (medicalRecordsArray != null) {
			Gson gson = new Gson();
			Type typeListMedicalRecord = new TypeToken<List<MedicalRecord>>() {}.getType();
			medicalRecords = gson.fromJson(medicalRecordsArray, typeListMedicalRecord);
		}
		
				
		return medicalRecords;
	}
}

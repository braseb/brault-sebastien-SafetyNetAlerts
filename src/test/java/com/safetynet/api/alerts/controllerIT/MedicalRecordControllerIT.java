package com.safetynet.api.alerts.controllerIT;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.dto.MedicalRecordCreateDto;
import com.safetynet.api.alerts.model.dto.MedicalRecordUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.MedicalRecordMapping;



@SpringBootTest
@AutoConfigureMockMvc

public class MedicalRecordControllerIT {
	
	@MockitoBean
	private JsonDatas jsonDatas;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void  setUp() {
		JsonObject mockJson = new JsonObject();
        
		Gson gson = new Gson();
				
		List<MedicalRecord> medicalRecords = List.of(new MedicalRecord("toto",
																		"tata",
																		"08/05/1983",
																		Collections.emptyList(),
																		Collections.emptyList()),
													new MedicalRecord("titi",
																		"tutu",
																		"10/17/2020",
																		Collections.emptyList(),
																		Collections.emptyList()));
		JsonElement medicalRecorsJson = gson.toJsonTree(medicalRecords);
		mockJson.add("medicalrecords", medicalRecorsJson);
				
        when(jsonDatas.getFileCache()).thenReturn(mockJson);
        doNothing().when(jsonDatas).writeJsonToFile();
        
	}
		
	
	@Test
	public void createMedicalRecordTest() throws Exception {
		
		Gson gson = new Gson();
		
		MedicalRecord newMedicalRecord = new MedicalRecord("testLastName", 
															"testFirstName", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordCreateDto newMedicalRecordDto = MedicalRecordMapping.mapToMedicalRecordCreateDto(newMedicalRecord);
		
		String json = gson.toJson(newMedicalRecordDto);
		mockMvc.perform(post("/medicalRecord")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.lastName", is("testLastName")))
				.andExpect(jsonPath("$.firstName", is("testFirstName")));
	}
	
	@Test
	public void createMedicalRecordAlreadyExistTest() throws Exception {
		
		Gson gson = new Gson();
		
		MedicalRecord newMedicalRecord = new MedicalRecord("toto", 
															"tata", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordCreateDto newMedicalRecordDto = MedicalRecordMapping.mapToMedicalRecordCreateDto(newMedicalRecord);
		
		String json = gson.toJson(newMedicalRecordDto);
		mockMvc.perform(post("/medicalRecord")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isConflict());
	}
	
	@Test
	public void createMedicalRecordNotValidTest() throws Exception {
		
		Gson gson = new Gson();
		
		MedicalRecord newMedicalRecord = new MedicalRecord("testLastName", 
															"testFirstName", 
															"08/05/83", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordCreateDto newMedicalRecordDto = MedicalRecordMapping.mapToMedicalRecordCreateDto(newMedicalRecord);
		
		String json = gson.toJson(newMedicalRecordDto);
		mockMvc.perform(post("/medicalRecord")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateMedicalRecordTest() throws Exception {
		Gson gson = new Gson();
		
		MedicalRecord updateMedicalRecord = new MedicalRecord("", 
															"", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordUpdateDto updateMedicalRecordDto = MedicalRecordMapping.mapToMedicalRecordUpdateDto(updateMedicalRecord);
				
		String json = gson.toJson(updateMedicalRecordDto);
		
		mockMvc.perform(put("/medicalRecord/{lastName}/{firstName}", "toto", "tata")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.birthdate", is("08/05/1983")));
	}
	
	@Test
	public void updateMedicalRecordNotFoundTest() throws Exception {
		Gson gson = new Gson();
		
		MedicalRecord updateMedicalRecord = new MedicalRecord("", 
															"", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordUpdateDto updateMedicalRecordDto = MedicalRecordMapping.mapToMedicalRecordUpdateDto(updateMedicalRecord);
				
		String json = gson.toJson(updateMedicalRecordDto);
		
		mockMvc.perform(put("/medicalRecord/{lastName}/{firstName}", "lastNameNotFound", "firstNameNotFound")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isNotFound());
	}
	
	@Test
	public void removeMedicalRecordTest() throws Exception {
				
		mockMvc.perform(delete("/medicalRecord/{lastName}/{firstName}", "toto", "tata"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		mockMvc.perform(delete("/medicalRecord/{lastName}/{firstName}", "toto", "tata"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		
	}
	
	@Test
	public void removeMedicalRecordNotFoundTest() throws Exception {
				
		mockMvc.perform(delete("/medicalRecord/{lastName}/{firstName}", "lastNameNotFound", "firstNameNotFound"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		
	}
			
}

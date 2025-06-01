package com.safetynet.api.alerts.controllerTest;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.safetynet.api.alerts.controller.MedicalRecordController;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.dto.MedicalRecordCreateDto;
import com.safetynet.api.alerts.model.dto.MedicalRecordUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.MedicalRecordMapping;
import com.safetynet.api.alerts.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private MedicalRecordService medicalRecordService;
	
	@Test
	public void createTest() throws Exception {
		
		Gson gson = new Gson();
		
		MedicalRecord newMedicalRecord = new MedicalRecord("testLastName", 
															"testFirstName", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		
		MedicalRecordCreateDto newMedicalRecordCreateDto = MedicalRecordMapping.mapToMedicalRecordCreateDto(newMedicalRecord);
		
		when(medicalRecordService.createMedicalRecord((any(MedicalRecordCreateDto.class)))).thenReturn(newMedicalRecordCreateDto);
		String json = gson.toJson(newMedicalRecordCreateDto);
		
		mockMvc.perform(post("/medicalRecord")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.lastName", is("testLastName")))
					.andExpect(jsonPath("$.firstName", is("testFirstName")));
	}
	
	@Test
	public void updateTest() throws Exception {
		Gson gson = new Gson();
		
		MedicalRecord updateMedicalRecord = new MedicalRecord("testLastName", 
															"testFirstName", 
															"08/05/1983", 
															Collections.emptyList(),
															Collections.emptyList());
		MedicalRecordUpdateDto updateMedicalRecordUpdateDto = MedicalRecordMapping.mapToMedicalRecordUpdateDto(updateMedicalRecord);
		
		when(medicalRecordService.updateMedicalRecord(anyString() , anyString(), any(MedicalRecordUpdateDto.class))).thenReturn(updateMedicalRecordUpdateDto);
		String json = gson.toJson(updateMedicalRecordUpdateDto);
		
		mockMvc.perform(put("/medicalRecord/{lastName}/{firstName}", "testLastName", "testFirstName")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.birthdate", is("08/05/1983")));
					
	}
	
	@Test
	public void removeTest() throws Exception {
				
		doNothing().when(medicalRecordService).removeMedicalRecord(anyString(), anyString());
		
		mockMvc.perform(delete("/medicalRecord/{lastName}/{firstName}", "testLastName", "testFirstName"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		verify(medicalRecordService).removeMedicalRecord(anyString(), anyString());
		
	}
		
}

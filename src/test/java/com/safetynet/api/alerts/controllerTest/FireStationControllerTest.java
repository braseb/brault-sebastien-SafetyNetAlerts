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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.safetynet.api.alerts.controller.FireStationController;
import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.dto.FireStationCreateDto;
import com.safetynet.api.alerts.model.dto.FireStationUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.FireStationMapping;
import com.safetynet.api.alerts.service.FireStationService;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private FireStationService fireStationService;
	
	@Test
	public void createFireStationTest() throws Exception {
		
		Gson gson = new Gson();
		
		FireStation newFireStation = new FireStation("my address2", 3);
		FireStationCreateDto newFireStationDto = FireStationMapping.mapToFireStationCreateDto(newFireStation);
		
		when(fireStationService.createFireStation(any(FireStationCreateDto.class))).thenReturn(newFireStationDto);
		String json = gson.toJson(newFireStationDto);
		
		mockMvc.perform(post("/firestation")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.address", is("my address2")))
					.andExpect(jsonPath("$.stationNumber", is(3)));
	}
	
	@Test
	public void updateFireStationTest() throws Exception {
		Gson gson = new Gson();
		
		FireStation updateFireStation = new FireStation("my address", 3);
		FireStationUpdateDto updateFireStationDto = FireStationMapping.mapToFireStationUpdateDto(updateFireStation);
		
		when(fireStationService.updateFireStation(anyString() , any(FireStationUpdateDto.class))).thenReturn(updateFireStationDto);
		String json = gson.toJson(updateFireStationDto);
		
		mockMvc.perform(put("/firestation/{address}", "my address")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.stationNumber", is(3)));
	}
	
	@Test
	public void removeFireStationByNumberTest() throws Exception {
				
		doNothing().when(fireStationService).removeFireStationByNumber(any(Integer.class));
		
		mockMvc.perform(delete("/firestation//station/{stationNumber}", "1"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		verify(fireStationService).removeFireStationByNumber(any(Integer.class));
	}
	
	@Test
	public void removeFireStationByAddressTest() throws Exception {
				
		doNothing().when(fireStationService).removeFireStationByAddress(anyString());
		
		mockMvc.perform(delete("/firestation/address/{address}", "my address"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		verify(fireStationService).removeFireStationByAddress(anyString());
	}
	
	
		
}

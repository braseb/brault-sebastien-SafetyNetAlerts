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
import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.dto.FireStationCreateDto;
import com.safetynet.api.alerts.model.dto.FireStationUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.FireStationMapping;


@SpringBootTest
@AutoConfigureMockMvc

public class FireStationControllerIT {
	
	@MockitoBean
	private JsonDatas jsonDatas;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void  setUp() {
		JsonObject mockJson = new JsonObject();
        
		Gson gson = new Gson();
				
		List<FireStation> fireStations = List.of(new FireStation("my address",
																	Integer.valueOf(1)),
												new FireStation("my address2",
																	Integer.valueOf(2)));
		JsonElement fireStationsJson = gson.toJsonTree(fireStations);		
		mockJson.add("firestations", fireStationsJson);
		
        when(jsonDatas.getFileCache()).thenReturn(mockJson);
        doNothing().when(jsonDatas).writeJsonToFile();
        
	}
		
	
	@Test
	public void createFireStationTest() throws Exception {
		
		Gson gson = new Gson();
		
		FireStation newFireStation = new FireStation("my address3", 3);
		FireStationCreateDto newFireStationDto = FireStationMapping.mapToFireStationCreateDto(newFireStation);
		
		String json = gson.toJson(newFireStationDto);
		mockMvc.perform(post("/firestation")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.address", is("my address3")))
				.andExpect(jsonPath("$.stationNumber", is(3)));
	}
	
	@Test
	public void updateFireStationTest() throws Exception {
		Gson gson = new Gson();
		
		FireStation updateFireStation = new FireStation("my address", 3);
		FireStationUpdateDto updateFireStationDto = FireStationMapping.mapToFireStationUpdateDto(updateFireStation);
				
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
				
		mockMvc.perform(delete("/firestation/station/{stationNumber}", "1"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		mockMvc.perform(delete("/firestation/station/{stationNumber}", "1"))
		.andDo(print())
		.andExpect(status().isNotFound());
		
		
	}
	
	@Test
	public void removeFireStationByAddressTest() throws Exception {
				
		mockMvc.perform(delete("/firestation/address/{address}", "my address"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		mockMvc.perform(delete("/firestation/address/{address}", "my address"))
					.andDo(print())
					.andExpect(status().isNotFound());
	}
	
			
}

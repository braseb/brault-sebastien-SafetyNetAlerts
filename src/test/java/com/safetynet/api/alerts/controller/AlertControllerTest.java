package com.safetynet.api.alerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.MemberHouseholdDto;
import com.safetynet.api.alerts.model.dto.PersonMiniWithPhoneDto;
import com.safetynet.api.alerts.service.AlertService;

@WebMvcTest(controllers = AlertController.class)
public class AlertControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private AlertService alertService;
	
	@Test
	public void getChilAlertTest() throws Exception {
		List<ChildAlertDto> chilsAlert = List.of(new ChildAlertDto("toto", "tata", 8, List.of(new MemberHouseholdDto("papa", "amoi"))));
		
		when(alertService.getChildrenByAddress("my address")).thenReturn(chilsAlert);
		mockMvc.perform(get("/childAlert").param("address", "my address"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].lastName").value("toto"))
					.andExpect(jsonPath("$[0].membersHousehold.[0].lastName").value("papa"));
	}
	
	@Test
	public void getPersonCoveredByFireStationTest() throws Exception {
		FirestationDto firestationDto = new FirestationDto();
		firestationDto.setPersons(List.of(new PersonMiniWithPhoneDto("toto", "tata", "my address", "0000"),
											new PersonMiniWithPhoneDto("toto", "children1", "my address", "0000"),
											new PersonMiniWithPhoneDto("toto", "chldren2", "my address", "0000")));
		firestationDto.setNumberAdult(1);
		firestationDto.setNumberChild(2);
				
		when(alertService.getPersonCoveredByFireStation(1)).thenReturn(firestationDto);
		
		mockMvc.perform(get("/firestation")
					.param("stationNumber", "1"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.numberAdult").value(1))
					.andExpect(jsonPath("$.numberChild").value(2))
					.andExpect(jsonPath("$.persons[0].lastName").value("toto"));
	}
	
	@Test
	public void getPhoneAlertTest() throws Exception {
		List<String> phonesAddress = List.of("0000", "1111");
		when(alertService.getPhoneAlert(1)).thenReturn(phonesAddress);
		
		mockMvc.perform(get("/phoneAlert")
					.param("firestation", "1"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0]").value("0000"))
					.andExpect(jsonPath("$[1]").value("1111"));
	}
}

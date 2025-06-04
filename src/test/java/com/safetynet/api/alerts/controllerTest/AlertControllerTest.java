package com.safetynet.api.alerts.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.api.alerts.controller.AlertController;
import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FireDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.HouseholdDto;
import com.safetynet.api.alerts.model.dto.MemberHouseholdDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithAddressAndEmailDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithPhoneDto;
import com.safetynet.api.alerts.model.dto.PersonMiniWithAddressAndPhoneDto;
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
		firestationDto.setPersons(List.of(new PersonMiniWithAddressAndPhoneDto("toto", "tata", "my address", "0000"),
											new PersonMiniWithAddressAndPhoneDto("toto", "children1", "my address", "0000"),
											new PersonMiniWithAddressAndPhoneDto("toto", "chldren2", "my address", "0000")));
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
					.param("stationNumber", "1"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0]").value("0000"))
					.andExpect(jsonPath("$[1]").value("1111"));
	}
	
	@Test
	public void getCasernNumberAndPersonsByAddressTest() throws Exception {
		FireDto fireDto = new FireDto();
		fireDto.setStationNumber(1);
		fireDto.setPersonFullInfo(List.of(new PersonMedicalRecordWithPhoneDto("toto", 
																		"tata", 
																		19, 
																		Collections.emptyList(),
																		Collections.emptyList(),
																		"0000")));
		when(alertService.getFirestationNumberAndPersonsByAddress("my address")).thenReturn(fireDto);
		
		mockMvc.perform(get("/fire")
				.param("address", "my address"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stationNumber").value("1"))
				.andExpect(jsonPath("$.personsInfos[0].lastName").value("toto"));
	}
	
	@Test
	public void getHouseholdTest() throws Exception {
		HouseholdDto houseHoldDto = new HouseholdDto();
		houseHoldDto.setHousehold(Map.of("my address", List.of(new PersonMedicalRecordWithPhoneDto("toto",
																							"tata",
																							19, 
																							Collections.emptyList(),
																							Collections.emptyList(),
																							"0000"),
																new PersonMedicalRecordWithPhoneDto("titi",
																		"tata",
																		21, 
																		Collections.emptyList(),
																		Collections.emptyList(),
																		"1111"))));
		
		when(alertService.getHouseholdsByStationNumbers(List.of(1, 2))).thenReturn(houseHoldDto);
		
		mockMvc.perform(get("/flood/stations")
				.param("stations", "1,2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.household.['my address'][0].lastName").value("toto"))
				.andExpect(jsonPath("$.household.['my address'][1].lastName").value("titi"));
	}
	
	@Test
	public void getPersoninfoTest() throws Exception {
		List<PersonMedicalRecordWithAddressAndEmailDto> personMedicalRecordWithEmailDto = 
												List.of(new PersonMedicalRecordWithAddressAndEmailDto("toto",
												"tata",
												"my address",
												19, 
												Collections.emptyList(),
												Collections.emptyList(),
												"mail1@mail.com"),
												new PersonMedicalRecordWithAddressAndEmailDto("toto",
												"titi",
												"my address2",
												21, 
												Collections.emptyList(),
												Collections.emptyList(),
												"mail2@mail.com"));
		when(alertService.getPersonMedicalRecordWithEmailByLastName("toto")).thenReturn(personMedicalRecordWithEmailDto);
		
		mockMvc.perform(get("/personInfo")
				.param("lastName", "toto"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].email").value("mail1@mail.com"))
				.andExpect(jsonPath("$[1].email").value("mail2@mail.com"));
	}
	
	@Test
	public void getEmaiFromCityTest() throws Exception {
		List<String> listMail = List.of("mail1@mail.com","mail2@mail.com");
												
		when(alertService.getAllEmailByCity("city")).thenReturn(listMail);
		
		mockMvc.perform(get("/communityEmail")
				.param("city", "city"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("mail1@mail.com"))
				.andExpect(jsonPath("$[1]").value("mail2@mail.com"));
	}
		
}

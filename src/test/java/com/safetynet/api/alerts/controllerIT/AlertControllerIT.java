package com.safetynet.api.alerts.controllerIT;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.safetynet.api.alerts.datas.JsonDatas;
import com.safetynet.api.alerts.model.FireStation;
import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.Person;



//@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

public class AlertControllerIT {
	
	@MockitoBean
	private JsonDatas jsonDatas;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void  setUp() {
		JsonObject mockJson = new JsonObject();
        
		Gson gson = new Gson();
				
		List<Person> persons = List.of(new Person("toto", 
													"tata",
													"my address",
													"city",
													"86000",
													"0000",
													"mail1@mail.com"),
										new Person("titi", 
												"tutu",
												"my address2",
												"city",
												"86000",
												"1111",
												"mail2@mail.com"));
		JsonElement personsJson = gson.toJsonTree(persons);
		mockJson.add("persons", personsJson);
		
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
		
		List<FireStation> fireStations = List.of(new FireStation("my address",
																	Integer.valueOf(1)),
												new FireStation("my address2",
																	Integer.valueOf(2)));
		JsonElement fireStationsJson = gson.toJsonTree(fireStations);		
		mockJson.add("firestations", fireStationsJson);
		
        when(jsonDatas.getFileCache()).thenReturn(mockJson);
        
	}
	
	@Test
	public void getUrlNotFoundTest() throws Exception {
		mockMvc.perform(get("/unknownUrl"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void noAllowedMethodTest() throws Exception {
		mockMvc.perform(delete("/childAlert").param("address", "my address2"))
		.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void getChilAlertTest() throws Exception {
			
		mockMvc.perform(get("/childAlert").param("address", "my address2"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].lastName", is("titi")));
	}
	
	@Test
	public void getChilAlertNotFoundTest() throws Exception {
			
		mockMvc.perform(get("/childAlert").param("address", "my address not exist"))
					.andDo(print())
					.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPersonCoveredByFireStationTest() throws Exception {
		mockMvc.perform(get("/firestation")
				.param("stationNumber", "2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberAdult",is(0)))
				.andExpect(jsonPath("$.numberChild",is(1)))
				.andExpect(jsonPath("$.persons[0].lastName", is("titi")));
	}
	
	@Test
	public void getPersonCoveredByFireStationNotFoundTest() throws Exception {
		mockMvc.perform(get("/firestation")
				.param("stationNumber", "3"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPhoneAlertTest() throws Exception {
		mockMvc.perform(get("/phoneAlert")
				.param("stationNumber", "1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]",is("0000")));
				
	}
	
	@Test
	public void getPhoneAlertNotFoundTest() throws Exception {
		mockMvc.perform(get("/phoneAlert")
				.param("stationNumber", "3"))
				.andDo(print())
				.andExpect(status().isNotFound());
				
	}
	
	@Test
	public void getCasernNumberAndPersonsByAddressTest() throws Exception {
		mockMvc.perform(get("/fire")
				.param("address", "my address"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stationNumber", is(1)))
				.andExpect(jsonPath("$.personsInfos[0].lastName", is("toto")));
	}
	
	@Test
	public void getCasernNumberAndPersonsByAddressNotFoundTest() throws Exception {
		mockMvc.perform(get("/fire")
				.param("address", "my address not found"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getHouseholdTest() throws Exception {
		mockMvc.perform(get("/flood/stations")
				.param("stations", "1,2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.household.['my address'][0].lastName").value("toto"))
				.andExpect(jsonPath("$.household.['my address2'][0].lastName").value("titi"));
	}
	
	@Test
	public void getHouseholdNotFoundTest() throws Exception {
		mockMvc.perform(get("/flood/stations")
				.param("stations", "10,11"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPersoninfoTest() throws Exception {
		mockMvc.perform(get("/personInfo")
				.param("lastName", "titi"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].email").value("mail2@mail.com"));
	}
	
	@Test
	public void getPersoninfoNotFoundTest() throws Exception {
		mockMvc.perform(get("/personInfo")
				.param("lastName", "noExist"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getEmaiFromCityTest() throws Exception {
		mockMvc.perform(get("/communityEmail")
				.param("city", "city"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("mail1@mail.com"))
				.andExpect(jsonPath("$[1]").value("mail2@mail.com"));
	}
	
	@Test
	public void getEmaiFromCityNotFoundTest() throws Exception {
		mockMvc.perform(get("/communityEmail")
				.param("city", "cityNotFound"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	
	
		
}

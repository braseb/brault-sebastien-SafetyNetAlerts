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
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.PersonMapping;


@SpringBootTest
@AutoConfigureMockMvc

public class PersonControllerIT {
	
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
				
        when(jsonDatas.getFileCache()).thenReturn(mockJson);
        doNothing().when(jsonDatas).writeJsonToFile();
        
	}
		
	
	@Test
	public void createPersonTest() throws Exception {
		
		Gson gson = new Gson();
		
		Person newPerson = new Person("testLastName", 
										"testFirstName", 
										"my address3", 
										"city", 
										"37000", 
										"2222", 
										"mail3@mail.com");
		PersonCreateDto newPersonDto = PersonMapping.mapToPersonCreateDto(newPerson);
		
		String json = gson.toJson(newPersonDto);
		mockMvc.perform(post("/person")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.lastName", is("testLastName")))
				.andExpect(jsonPath("$.firstName", is("testFirstName")));
	}
	
	@Test
	public void updatePersonTest() throws Exception {
		Gson gson = new Gson();
		
		Person updatePerson = new Person("", 
										"", 
										"my address3", 
										"city", 
										"37000", 
										"2222", 
										"mail3@mail.com");
		PersonUpdateDto updatePersonDto = PersonMapping.mapToPersonUpdateDto(updatePerson);
				
		String json = gson.toJson(updatePersonDto);
		
		mockMvc.perform(put("/person/{lastName}/{firstName}", "toto", "tata")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.address", is("my address3")))
					.andExpect(jsonPath("$.email", is("mail3@mail.com")));
	}
	
	@Test
	public void removePersonTest() throws Exception {
				
		mockMvc.perform(delete("/person/{lastName}/{firstName}", "toto", "tata"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		mockMvc.perform(delete("/person/{lastName}/{firstName}", "toto", "tata"))
					.andDo(print())
					.andExpect(status().isNotFound());
		
		
	}
			
}

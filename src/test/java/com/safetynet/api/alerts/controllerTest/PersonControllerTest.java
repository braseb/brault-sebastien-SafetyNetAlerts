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
import com.safetynet.api.alerts.controller.PersonController;
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.PersonCreateDto;
import com.safetynet.api.alerts.model.dto.PersonUpdateDto;
import com.safetynet.api.alerts.model.dto.mapping.PersonMapping;
import com.safetynet.api.alerts.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private PersonService personService;
	
	@Test
	public void createTest() throws Exception {
		
		Gson gson = new Gson();
		
		Person newPerson = new Person("testLastName", 
										"testFirstName", 
										"my address3", 
										"city", 
										"37000", 
										"2222", 
										"mail3@mail.com");
		
		PersonCreateDto newPersonCreateDto = PersonMapping.mapToPersonCreateDto(newPerson);
		
		when(personService.createPerson((any(PersonCreateDto.class)))).thenReturn(newPersonCreateDto);
		String json = gson.toJson(newPersonCreateDto);
		
		mockMvc.perform(post("/person")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.lastName", is("testLastName")))
					.andExpect(jsonPath("$.address", is("my address3")));
	}
	
	@Test
	public void updateTest() throws Exception {
		Gson gson = new Gson();
		
		Person updatePerson = new Person("testLastName", 
										"testFirstName", 
										"my address3", 
										"city", 
										"37000", 
										"2222", 
										"mail3@mail.com");
		PersonUpdateDto updatePersonUpdateDto = PersonMapping.mapToPersonUpdateDto(updatePerson);
		
		when(personService.updatePerson(anyString() , anyString(), any(PersonUpdateDto.class))).thenReturn(updatePersonUpdateDto);
		String json = gson.toJson(updatePersonUpdateDto);
		
		mockMvc.perform(put("/person/{lastName}/{firstName}", "testLastName", "testFirstName")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.address", is("my address3")))
					.andExpect(jsonPath("$.zip", is("37000")));
	}
	
	@Test
	public void removeTest() throws Exception {
				
		doNothing().when(personService).removePerson(anyString(), anyString());
		
		mockMvc.perform(delete("/person/{lastName}/{firstName}", "testLastName", "testFirstName"))
					.andDo(print())
					.andExpect(status().isNoContent());
		
		verify(personService).removePerson(anyString(), anyString());
		
	}
		
}

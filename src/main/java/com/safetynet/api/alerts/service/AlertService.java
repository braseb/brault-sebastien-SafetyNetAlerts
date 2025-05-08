package com.safetynet.api.alerts.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.MemberHousehold;
import com.safetynet.api.alerts.model.dto.PersonDto;


@Service
public class AlertService {

	
	private static final Logger log  = LogManager.getLogger();
    private final FireStationService fireStationService;
	
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordService medicalRecordService;

    AlertService(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }
	
	public List<ChildAlertDto> getChildrenByAdress(String address){
		List<Person> persons = personService.getPersonByAddress(address);
		List<ChildAlertDto> listChilAlert = new ArrayList<ChildAlertDto>();
		System.out.println(persons);
		log.info("list of person : {}", persons);
				
		for (Person person : persons) {
			List<MemberHousehold> members = new ArrayList<MemberHousehold>();
						
			int age = medicalRecordService.getAge(person.getLastName(), person.getFirstName());
			System.out.println(age);
			if (age <= 18 && age > -1) {
				ChildAlertDto childDto = new ChildAlertDto(person.getLastName(), person.getFirstName(), age, members);
				
				persons.stream()
						.filter(p -> !p.equals(person))
						.forEach(p -> childDto.addMembersHousehold(new MemberHousehold(p.getLastName(), p.getFirstName())));
			
				listChilAlert.add(childDto);
			}
			
			
		}
		return listChilAlert;
	}
	
	public FirestationDto getPersonCoveredByFireStation(int stationNumber){
		List<String> addressFireStations = fireStationService.getAddressByStationNumber(stationNumber);
		FirestationDto fireStationDto = new FirestationDto();
		List<PersonDto> personsDto = new ArrayList<PersonDto>();
				
		for (String addressFireStation : addressFireStations) {
			
		
			List<Person> persons = personService.getPersonByAddress(addressFireStation);
			List<PersonDto> personsDtoByAddress = persons.stream()
													.filter(p -> p.getAddress().equals(addressFireStation))
													.map(p -> {return new PersonDto(p.getLastName(),
																					p.getFirstName(),
																					p.getAddress(),
																					p.getPhone());})
													.collect(Collectors.toList());
			
			personsDto.addAll(personsDtoByAddress);
		
		}		
		
		personsDto.sort(Comparator.comparing(p -> ((PersonDto) p).getLastName())
									.thenComparing(p -> ((PersonDto) p).getFirstName()));
		//number of child and adult
		Map<Boolean, List<PersonDto>> part = personsDto.stream()
				.collect(Collectors.partitioningBy(p -> medicalRecordService.getAge(p.getLastName(), p.getFirstName()) <= 18));
																
		fireStationDto.setPersons(personsDto);
		fireStationDto.setNumberChild(part.get(true).size());
		fireStationDto.setNumberAdult(part.get(false).size());
				
		return fireStationDto;
		
	}
	
	public List<String> getPhoneAlert(int fireStation){
		List<String> addressfireStation = fireStationService.getAddressByStationNumber(fireStation);
		List<String> phones = new ArrayList<String>();
		for (String address : addressfireStation) {
			List<Person> persons = personService.getPersonByAddress(address);
			List<String> phonesAddress = persons.stream()
												.map(Person::getPhone)
												.distinct()
												.toList();
			phones.addAll(phonesAddress);
		}
		
		return phones;
		
	}
	
	
}

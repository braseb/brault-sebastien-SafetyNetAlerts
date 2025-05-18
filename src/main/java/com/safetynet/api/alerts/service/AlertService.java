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

import com.safetynet.api.alerts.model.MedicalRecord;
import com.safetynet.api.alerts.model.Person;
import com.safetynet.api.alerts.model.dto.ChildAlertDto;
import com.safetynet.api.alerts.model.dto.FireDto;
import com.safetynet.api.alerts.model.dto.FirestationDto;
import com.safetynet.api.alerts.model.dto.HouseholdDto;
import com.safetynet.api.alerts.model.dto.MemberHouseholdDto;
import com.safetynet.api.alerts.model.dto.PersonDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithEmailDto;
import com.safetynet.api.alerts.model.dto.PersonMedicalRecordWithPhoneDto;
import com.safetynet.api.alerts.model.dto.PersonMiniWithPhoneDto;



@Service
public class AlertService {

    private static final Logger log  = LogManager.getLogger();
    private final FireStationService fireStationService;
    
	
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordService medicalRecordService;

    AlertService(FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }
	
	public List<ChildAlertDto> getChildrenByAdress(String address){
		List<Person> persons = personService.getPersonByAddress(address);
		List<ChildAlertDto> listChilAlert = new ArrayList<ChildAlertDto>();
		log.info("list of person : {}", persons);
				
		for (Person person : persons) {
			List<MemberHouseholdDto> members = new ArrayList<MemberHouseholdDto>();
						
			Integer age = medicalRecordService.getAge(person.getLastName(), person.getFirstName());
			if (age.intValue() <= 18 && age != null) {
				ChildAlertDto childDto = new ChildAlertDto(person.getLastName(), person.getFirstName(), age, members);
				
				persons.stream()
						.filter(p -> !p.equals(person))
						.forEach(p -> childDto.addMembersHousehold(new MemberHouseholdDto(p.getLastName(), p.getFirstName())));
			
				listChilAlert.add(childDto);
			}
			
			
		}
		return listChilAlert;
	}
	
	public FirestationDto getPersonCoveredByFireStation(int stationNumber){
		List<String> addressFireStations = fireStationService.getAddressByStationNumber(stationNumber);
		FirestationDto fireStationDto = new FirestationDto();
		List<PersonMiniWithPhoneDto> personMiniWithPhoneDto = new ArrayList<PersonMiniWithPhoneDto>();
				
		for (String addressFireStation : addressFireStations) {
			
		
			List<Person> persons = personService.getPersonByAddress(addressFireStation);
			List<PersonMiniWithPhoneDto> personsDtoByAddress = persons.stream()
													.filter(p -> p.getAddress().equals(addressFireStation))
													.map(p -> {return new PersonMiniWithPhoneDto(p.getLastName(),
																					p.getFirstName(),
																					p.getAddress(),
																					p.getPhone());})
													.collect(Collectors.toList());
			
			personMiniWithPhoneDto.addAll(personsDtoByAddress);
		
		}		
		
		personMiniWithPhoneDto.sort(Comparator.comparing(p -> ((PersonDto) p).getLastName())
									.thenComparing(p -> ((PersonDto) p).getFirstName()));
		//number of child and adult
		Map<Boolean, List<PersonMiniWithPhoneDto>> part = personMiniWithPhoneDto.stream()
				.collect(Collectors.partitioningBy(p -> medicalRecordService.getAge(p.getLastName(), p.getFirstName()) <= 18));
																
		fireStationDto.setPersons(personMiniWithPhoneDto);
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
	
	public FireDto getFirestationNumberAndPersonsByAddress(String address) {
		List<Person> persons = personService.getPersonByAddress(address);
		int stationNumber = fireStationService.getStationNumberByAddress(address);
		FireDto fireDto = new FireDto();
				
		List<PersonMedicalRecordWithPhoneDto> listPersonFullInfoDto = persons.stream()
						.map(p -> {MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(p.getLastName(), p.getFirstName());
									PersonMedicalRecordWithPhoneDto personMedicalRecordWithPhoneDto = new PersonMedicalRecordWithPhoneDto();
									personMedicalRecordWithPhoneDto.setLastName(p.getLastName());
									personMedicalRecordWithPhoneDto.setFirstName(p.getFirstName());
									personMedicalRecordWithPhoneDto.setPhone(p.getPhone());
									personMedicalRecordWithPhoneDto.setMedications(medicalRecord.getMedications());
									personMedicalRecordWithPhoneDto.setAllergies(medicalRecord.getAllergies());
									personMedicalRecordWithPhoneDto.setAge(medicalRecordService.getAge(medicalRecord));
									return personMedicalRecordWithPhoneDto;})
						
						.collect(Collectors.toList());
		fireDto.setPersonFullInfo(listPersonFullInfoDto);
		fireDto.setStationNumber(stationNumber);
		return fireDto;
		
	}
	
	public HouseholdDto getHouseholdsByStationNumbers(List<Integer> stationNumbers){
		List<String> address = fireStationService.getAddressByListOfStationNumber(stationNumbers);
		HouseholdDto household = new HouseholdDto();
				
		address.stream()
				.forEach(a -> {List<Person> persons = personService.getPersonByAddress(a);
								persons.stream()
								.forEach(p -> {PersonMedicalRecordWithPhoneDto personMedicalRecordWithPhoneDto = new PersonMedicalRecordWithPhoneDto();
												MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(p.getLastName(), p.getFirstName());
												personMedicalRecordWithPhoneDto.setLastName(p.getLastName());
												personMedicalRecordWithPhoneDto.setFirstName(p.getFirstName());
												personMedicalRecordWithPhoneDto.setPhone(p.getPhone());
												personMedicalRecordWithPhoneDto.setMedications(medicalRecord.getMedications());
												personMedicalRecordWithPhoneDto.setAllergies(medicalRecord.getAllergies());
												personMedicalRecordWithPhoneDto.setAge(medicalRecordService.getAge(medicalRecord));
												household.addPerson(a, personMedicalRecordWithPhoneDto);
												});
								});
		
		return household;
							
	}
	
	public List<PersonMedicalRecordWithEmailDto> getPersonMedicalRecordWithEmailByLastName(String lastName){
		List<Person> persons = personService.getPersonByLastName(lastName);
		List<PersonMedicalRecordWithEmailDto> personMedicalRecordWithEmailDtos = persons.stream()
							.map(p -> {MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(p.getLastName(), p.getFirstName());
										PersonMedicalRecordWithEmailDto personMedicalRecordWithEmailDto = new PersonMedicalRecordWithEmailDto();
										personMedicalRecordWithEmailDto.setLastName(p.getLastName());
										personMedicalRecordWithEmailDto.setFirstName(p.getFirstName());
										personMedicalRecordWithEmailDto.setAddress(p.getAddress());
										personMedicalRecordWithEmailDto.setEmail(p.getEmail());
										personMedicalRecordWithEmailDto.setAge(medicalRecordService.getAge(p.getLastName(), p.getFirstName()));
										personMedicalRecordWithEmailDto.setMedications(medicalRecord.getMedications());
										personMedicalRecordWithEmailDto.setAllergies(medicalRecord.getAllergies());
										return personMedicalRecordWithEmailDto;})
							.collect(Collectors.toList());
		
		return personMedicalRecordWithEmailDtos;
	}
	
	public List<String> getAllEmailByCity(String city){
		List<Person> persons =  personService.getPersonByCity(city);
		List<String> emails = persons.stream()
									.map(Person::getEmail)
									.distinct()
									.sorted()
									.toList();
		
		return emails;
	}
	
	
}

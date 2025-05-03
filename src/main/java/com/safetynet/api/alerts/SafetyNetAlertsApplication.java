package com.safetynet.api.alerts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.api.alerts.repository.PersonRepository;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//System.out.println(new PersonRepository().getPersonByName("Boyd", "John"));
		
	}

}

package com.basics.concepts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.basics.concepts"})
public class ProfOfConceptsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfOfConceptsApplication.class, args);
	}

}

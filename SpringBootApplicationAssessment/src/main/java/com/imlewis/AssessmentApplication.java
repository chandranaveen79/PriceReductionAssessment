package com.imlewis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Naveen Chandra
 * SpringBoot Main class to execute the SpringBoot application
 */
@SpringBootApplication
public class AssessmentApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AssessmentApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}
}

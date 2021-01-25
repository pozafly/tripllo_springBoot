package com.pozafly.tripllo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ServletComponentScan
public class TriplloApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" +
			"classpath:application.properties," +
			"classpath:application-db.properties," +
			"classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(TriplloApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}

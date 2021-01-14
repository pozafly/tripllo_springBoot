package com.pozafly.tripllo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ServletComponentScan
public class TriplloApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriplloApplication.class, args);
	}

}

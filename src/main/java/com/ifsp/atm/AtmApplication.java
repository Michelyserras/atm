package com.ifsp.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmApplication.class, args);
		System.out.println("\n=== ATM APPLICATION STARTED ===");
		System.out.println("H2 Console: http://localhost:8080/h2-console");
		System.out.println("API Demo: http://localhost:8080/api/factory/demo-factory");
		System.out.println("================================\n");
	}

}

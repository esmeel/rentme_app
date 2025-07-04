package com.rentme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.rentme")
@EnableJpaRepositories(basePackages = "com.rentme.repository")
@EntityScan(basePackages = "com.rentme.model")

public class RentmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentmeApplication.class, args);
	}

}

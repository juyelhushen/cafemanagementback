package com.cafemanagementapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.cafemanagementapp.entity")
public class CafeManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeManagementAppApplication.class, args);
	}

}

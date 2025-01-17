package com.plantplaces.photos.plantplacespostprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableCaching
@EnableJms
public class PlantplacespostprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantplacespostprocessorApplication.class, args);
	}

}

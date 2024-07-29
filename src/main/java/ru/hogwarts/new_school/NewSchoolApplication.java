package ru.hogwarts.new_school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class NewSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewSchoolApplication.class, args);
	}

}

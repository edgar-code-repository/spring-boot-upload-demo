package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringBootUploadDemoApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		// This line is just to check if the application can see AZURE SQL Server environment variables
		System.getenv().forEach((key, value) -> System.out.println(key + "=" + value));
		SpringApplication.run(SpringBootUploadDemoApplication.class, args);
	}

}

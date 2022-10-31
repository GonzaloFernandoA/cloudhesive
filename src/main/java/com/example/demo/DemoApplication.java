package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner { 

	 	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Override
    public void run(String... args) {
		 System.out.println("Hello World from Application Runner");
    }
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

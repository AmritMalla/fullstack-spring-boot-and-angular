package com.linkedin.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration // enableAutoConfiguration will intelligently configure beans that are likely to need in your spring context
@ComponentScan(basePackages = "com.linkedin")
public class LinkedInFullStackAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedInFullStackAppApplication.class, args);
	}

}

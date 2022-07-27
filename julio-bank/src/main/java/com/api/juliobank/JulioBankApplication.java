package com.api.juliobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JulioBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(JulioBankApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "Ola mundo";
	}
}

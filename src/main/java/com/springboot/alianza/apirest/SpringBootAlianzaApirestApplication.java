package com.springboot.alianza.apirest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Alianza",
				version = "1.0.0",
				description = "Este proyecto es una prueba",
				contact = @Contact(
						name = "Jerson Villa",
						email = "ing.jersonvilla@gmail.com"
						),
				license = @License(
						name = "Linkedin",
						url = "https://www.linkedin.com/in/jerson-villamizar/"
						)
				)
		)
public class SpringBootAlianzaApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAlianzaApirestApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}

package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutantDetectorApplication.class, args);

		System.out.println("----------------------------------------------------------");
		System.out.println(" Servidor iniciado correctamente");
		System.out.println(" Documentación Swagger: http://localhost:8080/swagger-ui.html");
		System.out.println(" Base de datos H2:      http://localhost:8080/h2-console");
		System.out.println("----------------------------------------------------------");
	}

}
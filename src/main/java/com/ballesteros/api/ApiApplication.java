package com.ballesteros.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal para ejecutar la aplicación Spring Boot.
 */
@SpringBootApplication
public class ApiApplication {

	/**
	 * Método principal para ejecutar la aplicación.
	 *
	 * @param args los argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

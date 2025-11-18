package com.tuempresa.chifawufuproyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// --- AÑADIR ESTAS DOS IMPORTACIONES ---
import jakarta.annotation.PostConstruct;
import java.util.Locale;

@SpringBootApplication
public class ChifawufuproyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChifawufuproyectoApplication.class, args);
	}

	// --- AÑADIR ESTE MÉTODO COMPLETO ---
	/**
	 * Establece la Localización (Locale) por defecto a Español-Perú.
	 * Esto asegura que Thymeleaf use "S/" como símbolo de moneda.
	 * @PostConstruct asegura que se ejecute una vez al iniciar la app.
	 */
	@PostConstruct
	public void init() {
		// Esta es la forma moderna (Java 19+) de establecer la región
		Locale.setDefault(Locale.of("es", "PE"));
	}
}
package com.impulsart.ImpulsArtApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ImpulsArtAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpulsArtAppApplication.class, args);
	}

	@Configuration
	public static class Myconfiguration {
		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedOrigins("*") // Permitir todos los orígenes
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
							.allowedHeaders("*") // Permitir todos los encabezados
							.allowCredentials(false); // Desactivar credenciales si permites todos los orígenes
				}
			};
		}
	}

}

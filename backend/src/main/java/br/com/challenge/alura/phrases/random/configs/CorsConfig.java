package br.com.challenge.alura.phrases.random.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/series/frases") // all=/**
				.allowedOrigins("http://127.0.0.1:5500") // origin of the requisition
				.allowedMethods("GET") // GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, CONNECT
		;
	}
}

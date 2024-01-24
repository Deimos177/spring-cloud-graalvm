package br.com.deimos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloGraalvm {
	
	@Value("${message}")
	String message;

	@GetMapping("/hello")
	ResponseEntity<String> home() {
		return ResponseEntity.ok(message);
	}
}
package br.com.deimos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.deimos.dto.BookDTO;
import br.com.deimos.service.BookService;

@RestController
@RequestMapping("/book")
public class Bookcontroller {
	
	@Autowired
	private BookService service;

	@PostMapping
	ResponseEntity<Object> createBook(@RequestBody BookDTO dto) {
		return service.createBook(dto);
	}
}
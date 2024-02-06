package br.com.deimos.service;

import org.springframework.http.ResponseEntity;

import br.com.deimos.dto.BookRecordDTO;

public interface BookService {
	
	public ResponseEntity<Object> createBook(BookRecordDTO dto);
}
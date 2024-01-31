package br.com.deimos.service;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.deimos.dto.BookDTO;
import br.com.deimos.entity.Book;
import br.com.deimos.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class BookService {

	@Autowired
	private BookRepository repository;

	public ResponseEntity<Object> createBook(BookDTO bookDto) {
		
		String nullFields = verifyFields(bookDto);

		Book bookToSave = new Book();
		BeanUtils.copyProperties(bookDto, bookToSave);
		
		if(nullFields != null)
			return ResponseEntity.badRequest().body(nullFields);
		
		if(Objects.nonNull(repository.findBookByName(bookDto.getName())))
			return ResponseEntity.badRequest().body("Book already exists");

		bookToSave.setReleaseDate(LocalDate.of(bookDto.getReleaseDate().getYear(), bookDto.getReleaseDate().getMonth(),
				bookDto.getReleaseDate().getDayOfMonth()));

		Book savedBook = repository.save(bookToSave);

		BeanUtils.copyProperties(savedBook, bookDto);

		return ResponseEntity.ok(bookDto);
	}
	
	public String verifyFields(BookDTO book) {

		if (book.getShortDescription().length() > 100)
			return "Error, description is too long";
		else if (book.getName().isBlank())
			return "Empty name is not allowed";
		else if (book.getShortDescription().isBlank())
			return "Empty description is not allowed";
		else if(book.getReleaseDate() == null || book.getReleaseDate().toString().isBlank())
			return "Empty released date is not allowed";
		return null;
	}
}
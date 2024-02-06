package br.com.deimos.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.deimos.dto.BookRecordDTO;
import br.com.deimos.entity.Book;
import br.com.deimos.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository repository;

	@Override
	public ResponseEntity<Object> createBook(BookRecordDTO bookDto) {
		
		String nullFields = verifyFields(bookDto);

		Book bookToSave = new Book();
		BeanUtils.copyProperties(bookDto, bookToSave);
		
		if(nullFields != null)
			return ResponseEntity.badRequest().body(nullFields);
		
		if(Objects.nonNull(repository.findBookByName(bookDto.name())))
			return ResponseEntity.badRequest().body("Book already exists");

		bookToSave.setReleaseDate(LocalDateTime.of(bookDto.releaseDate().getYear(), bookDto.releaseDate().getMonth(), bookDto.releaseDate().getDayOfMonth(), bookDto.releaseDate().getHour(), bookDto.releaseDate().getMinute()));

		Book saved = repository.save(bookToSave);
		
		BookRecordDTO savedBook = new BookRecordDTO(saved.getUUID(), saved.getName(), saved.getShortDescription(), saved.getReleaseDate());

		return ResponseEntity.ok(savedBook);
	}
	
	public String verifyFields(BookRecordDTO book) {

		if (book.shortDescription().length() > 100)
			return "Error, description is too long";
		else if (book.name().isBlank())
			return "Empty name is not allowed";
		else if (book.shortDescription().isBlank())
			return "Empty description is not allowed";
		else if(book.releaseDate() == null || book.releaseDate().toString().isBlank())
			return "Empty released date is not allowed";
		return null;
	}
}
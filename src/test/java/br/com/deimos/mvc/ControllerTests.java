package br.com.deimos.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.deimos.dto.BookRecordDTO;
import br.com.deimos.entity.Book;
import br.com.deimos.service.BookService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.yml")
public class ControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	private BookService service;

	BookRecordDTO expectedBook;
	BookRecordDTO book;
	private static ObjectMapper mapper = new ObjectMapper();
	DateTimeFormatter formatter;
	String date;

	@BeforeEach
	void setUp() {
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		date = "25.07.2020 15:25";
		expectedBook = new BookRecordDTO("0477fbe0-be46-4ee2-9b1f-0b6cc82672c0", "Test book", "Just some description",
				LocalDateTime.parse(date, formatter));
		book = new BookRecordDTO(null, "Test book", "Just some description",
				LocalDateTime.parse(date, formatter));
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	void should_return_user_when_saved() throws URISyntaxException, Exception {

		Book bookToSave = new Book();

		BeanUtils.copyProperties(book, bookToSave);

		Mockito.when(service.createBook(book)).thenReturn(ResponseEntity.ok(expectedBook));
		
		String json = mapper.writeValueAsString(bookToSave);

		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isOk());
	}
	@Test
	void should_return_error_due_to_empty_name() throws URISyntaxException, Exception {
		book = new BookRecordDTO(null, null, "Just some description",
				LocalDateTime.parse(date, formatter));

		Book bookToSave = new Book();

		BeanUtils.copyProperties(book, bookToSave);

		Mockito.when(service.createBook(book)).thenReturn(ResponseEntity.badRequest().body("Empty name is not allowed"));
		
		String json = mapper.writeValueAsString(bookToSave);

		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_error_due_to_description() throws URISyntaxException, Exception {
		book = new BookRecordDTO(null, "Test book", null,
				LocalDateTime.parse(date, formatter));

		Book bookToSave = new Book();

		BeanUtils.copyProperties(book, bookToSave);

		Mockito.when(service.createBook(book)).thenReturn(ResponseEntity.badRequest().body("Empty description is not allowed"));
		
		String json = mapper.writeValueAsString(bookToSave);

		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_error_due_to_release_date() throws URISyntaxException, Exception {
		book = new BookRecordDTO(null, "Test book", "Just some description",
				null);

		Book bookToSave = new Book();

		BeanUtils.copyProperties(book, bookToSave);

		Mockito.when(service.createBook(book)).thenReturn(ResponseEntity.badRequest().body("Empty released date is not allowed"));
		
		String json = mapper.writeValueAsString(bookToSave);

		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_error_due_to_long_description() throws URISyntaxException, Exception {
		book = new BookRecordDTO(null, "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma", "Just some description",
				LocalDateTime.parse(date, formatter));

		Book bookToSave = new Book();

		BeanUtils.copyProperties(book, bookToSave);

		Mockito.when(service.createBook(book)).thenReturn(ResponseEntity.badRequest().body("Error, description is too long"));
		
		String json = mapper.writeValueAsString(bookToSave);

		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isBadRequest());
	}
}

package br.com.bruce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.bruce.dto.ToolKitRecordDTO;

@SpringBootTest
@ActiveProfiles("test")
public class DtoLoader {
	
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	
	String validName = "Test Entity", ValidDescription = "Just some description";
	LocalDateTime validDate = LocalDateTime.parse("25.07.2020 15:25", formatter);
	boolean validActive = true;
	
	
	@Test
	@DisplayName("Check if the entity is initialized correctly")
	void should_initialize_entity_correctly() {
		
		ToolKitRecordDTO dto = new ToolKitRecordDTO(10L, "Test Entity", "Just some description", LocalDateTime.parse("25.07.2020 15:25", formatter), true);
		
		assertThat(dto).isNotNull();
		assertEquals(validName, dto.name());
		assertEquals(ValidDescription, dto.description());
		assertEquals(validDate, dto.releaseDate());
		assertEquals(validActive, dto.active());
	}
}
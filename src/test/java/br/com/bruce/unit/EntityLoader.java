package br.com.bruce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.bruce.entity.ToolkitEntity;

@SpringBootTest
@ActiveProfiles("test")
public class EntityLoader {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	
	String validName = "Test Entity", ValidDescription = "Just some description";
	LocalDateTime validDate = LocalDateTime.parse("25.07.2020 15:25", formatter);
	boolean validActive = true;
	
	
	@Test
	@DisplayName("Check if the entity is initialized correctly")
	void should_initialize_entity_correctly() {
		
		ToolkitEntity entity = new ToolkitEntity(10L, "Test Entity", "Just some description", LocalDateTime.parse("25.07.2020 15:25", formatter), true);
		
		assertThat(entity).isNotNull();
		assertEquals(validName, entity.getName());
		assertEquals(ValidDescription, entity.getDescription());
		assertEquals(validDate, entity.getReleaseDate());
		assertEquals(validActive, entity.getActive());
	}
}
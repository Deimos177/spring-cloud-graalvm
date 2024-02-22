package br.com.bruce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.bruce.entity.ToolkitEntity;
import br.com.bruce.repository.ToolkitRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ToolkitRepositoryTest {

	@Autowired
	private ToolkitRepository repository;
	DateTimeFormatter formatter;
	String date;

	@BeforeEach
	void setUp() {
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		date = "25.07.2020 15:25";
		ToolkitEntity entity = new ToolkitEntity(null, "Example", "Just some description",
				LocalDateTime.parse(date, formatter), true);
		repository.save(entity);
	}

	@Test
	@DisplayName("Should save entity")
	public void should_return_entity_when_saved() {

		ToolkitEntity entity = new ToolkitEntity(null, "Example 1", "Just some description 1",
				LocalDateTime.parse(date, formatter), true);
		ToolkitEntity entitySaved = repository.save(entity);

		assertNotNull(entitySaved);
		assertThat(entitySaved.getId()).isNotNull();
		assertThat(entitySaved.getName()).isNotNull();
		assertThat(entitySaved.getDescription()).isNotNull();
		assertThat(entitySaved.getReleaseDate()).isNotNull();
		assertThat(entitySaved.getActive()).isNotNull();
	}

	@Test
	@DisplayName("Should return entity by name")
	public void should_return_found_entityk() {
		ToolkitEntity foundEntity = repository.findToolkitByName("Example").get(); 

		assertNotNull(foundEntity);
		assertThat(foundEntity.getId()).isNotNull();
		assertThat(foundEntity.getName()).isNotNull();
		assertThat(foundEntity.getDescription()).isNotNull();
		assertThat(foundEntity.getReleaseDate()).isNotNull();
		assertThat(foundEntity.getActive()).isNotNull();
	}

	@Test
	@DisplayName("Should return deleted entity")
	public void should_return_deleted_entity() {
		ToolkitEntity foundEntity = repository.findToolkitByName("Example").get(); 
		repository.delete(foundEntity);
		Optional<ToolkitEntity> shouldBeNullEntity = repository.findToolkitByName("Example"); 
		assertThat(shouldBeNullEntity.isEmpty());
	}
	
	@Test
	@DisplayName("Should return updated user")
	public void should_return_uptaded_user() {
		ToolkitEntity foundEntity = repository.findToolkitByName("Example").get(); 
		foundEntity.setDescription("Just some updated description");
		foundEntity.setActive(false);
		repository.save(foundEntity);
		Optional<ToolkitEntity> compareEntity = repository.findById(foundEntity.getId());
		assertThat(compareEntity.isPresent());
		assertEquals(foundEntity, compareEntity.get());
	}
	
	@Test
	@DisplayName("Should return entities list")
	public void should_return_entities_list() {
		
		List<ToolkitEntity> entities = repository.findAll();
		
		assertThat(entities).isNotNull();
		entities.forEach(entity ->{
			assertThat(entity.getName()).isNotBlank();
		});
	}
}

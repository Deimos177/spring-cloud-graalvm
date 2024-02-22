package br.com.bruce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.bruce.dto.ToolKitRecordDTO;
import br.com.bruce.dto.ToolkitUpdateDto;
import br.com.bruce.entity.ToolkitEntity;
import br.com.bruce.repository.ToolkitRepository;
import br.com.bruce.service.impl.ToolKitServiceImpl;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ToolKitServiceImplTest {

	@InjectMocks
	ToolKitServiceImpl service;
	@Mock
	ToolkitRepository repository;
	@Mock
	private ArgumentCaptor<ToolkitEntity> bookArgumentCaptor;
	ToolKitRecordDTO request;
	ToolkitEntity requestBook = new ToolkitEntity();
	ToolkitEntity expectedSavedEntity = new ToolkitEntity();
	DateTimeFormatter formatter;
	String date;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		date = "25.07.2020 15:25";
	}

	@Test
	@DisplayName("Create test entity")
	@Order(1)
	void when_save_user_it_should_return_user() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		BeanUtils.copyProperties(request, requestBook);

		lenient().when(this.repository.save(requestBook)).thenReturn(expectedSavedEntity);

		ToolKitRecordDTO created = (ToolKitRecordDTO) this.service.createEntity(request).getBody();

		assertThat(created).isNotNull();
		verify(repository, times(1)).save(requestBook);
		assertThat(created.name()).isNotEmpty();
		assertThat(created.id().toString()).isNotBlank();
		assertThat(created.releaseDate().toString()).isNotBlank();
		assertThat(created.description()).isNotBlank();

	}

	@Test
	@DisplayName("Return error due to existent entity")
	@Order(2)
	void should_throw_exception_due_to_existent_book() {

		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				null);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);

		lenient().when(this.repository.findToolkitByName(request.name())).thenReturn(Optional.of(expectedSavedEntity));

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			this.service.createEntity(request);
		});

		assertEquals("Entity already exists", exception.getMessage());
	}

	@Test
	@DisplayName("Return all existent entities")
	@Order(3)
	void should_return_all_entities() {
		List<ToolkitEntity> expectedList = new ArrayList<ToolkitEntity>();
		for (int index = 0; index < 5; index += 1) {
			expectedList.add(new ToolkitEntity(1L, String.format("Test {}", index), "Just some description",
					LocalDateTime.parse(date, formatter), true));
		}

		lenient().when(this.repository.findAll()).thenReturn(expectedList);

		List<ToolKitRecordDTO> dtos = service.findAllEntites().getBody();

		assertNotNull(dtos);
		for (int index = 0; index < dtos.size(); index += 1) {
			assertEquals(expectedList.get(index).getId(), dtos.get(index).id());
			assertEquals(expectedList.get(index).getName(), dtos.get(index).name());
			assertEquals(expectedList.get(index).getDescription(), dtos.get(index).description());
			assertEquals(expectedList.get(index).getReleaseDate(), dtos.get(index).releaseDate());
			assertEquals(expectedList.get(index).getActive(), dtos.get(index).active());
		}
	}

	@Test
	@DisplayName("Should return entity found by name")
	@Order(4)
	void should_return_entity_by_name() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		lenient().when(this.repository.findToolkitByName(request.name())).thenReturn(Optional.of(expectedSavedEntity));

		ToolKitRecordDTO dto = service.findEntityByName(request.name()).getBody();

		assertNotNull(dto);
		assertEquals(expectedSavedEntity.getId(), dto.id());
		assertEquals(expectedSavedEntity.getName(), dto.name());
		assertEquals(expectedSavedEntity.getDescription(), dto.description());
		assertEquals(expectedSavedEntity.getReleaseDate(), dto.releaseDate());
		assertEquals(expectedSavedEntity.getActive(), dto.active());
	}

	@Test
	@DisplayName("Should full updated entity")
	@Order(5)
	void should_update_full_entity() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Test", "Test 1", "Just some update", false);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.of(expectedSavedEntity));
		expectedSavedEntity.setActive(false);
		expectedSavedEntity.setName("Test 1");
		expectedSavedEntity.setDescription("Just some update");
		lenient().when(this.repository.save(expectedSavedEntity)).thenReturn(expectedSavedEntity);

		ResponseEntity<Object> response = service.updateEntity(dto);

		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	}

	@Test
	@DisplayName("Should update just the name of entity")
	@Order(6)
	void should_update_entity_name() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Test", "Test 1", null, null);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.of(expectedSavedEntity));
		expectedSavedEntity.setActive(false);
		expectedSavedEntity.setName("Test 1");
		lenient().when(this.repository.save(expectedSavedEntity)).thenReturn(expectedSavedEntity);

		ResponseEntity<Object> response = service.updateEntity(dto);

		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	@DisplayName("Should update just the description of entity")
	@Order(7)
	void should_update_entity_description() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Test", null, "Just some update", null);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.of(expectedSavedEntity));
		expectedSavedEntity.setDescription("Just some update");
		lenient().when(this.repository.save(expectedSavedEntity)).thenReturn(expectedSavedEntity);

		ResponseEntity<Object> response = service.updateEntity(dto);

		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	@DisplayName("Should update just the active of entity")
	@Order(8)
	void should_update_entity_active() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Test", null, null, false);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.of(expectedSavedEntity));
		expectedSavedEntity.setActive(false);
		lenient().when(this.repository.save(expectedSavedEntity)).thenReturn(expectedSavedEntity);

		ResponseEntity<Object> response = service.updateEntity(dto);

		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	@DisplayName("Should throw error due to inexistent entity")
	@Order(9)
	void should_throw_error_due_to_inexistent_entity() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Teste", null, null, false);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.empty());

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			this.service.updateEntity(dto);
		});

		assertEquals("Entity not found check the name", exception.getMessage());
	}
	
	@Test
	@DisplayName("Should throw internal server error")
	@Order(10)
	void Should_throw_internal_server_error() {
		request = new ToolKitRecordDTO(null, "Test", "Just some description", LocalDateTime.parse(date, formatter),
				true);
		BeanUtils.copyProperties(request, expectedSavedEntity);
		expectedSavedEntity.setId(1L);
		ToolkitUpdateDto dto = new ToolkitUpdateDto("Test", null, null, false);
		lenient().when(this.repository.findToolkitByName(dto.actualName()))
				.thenReturn(Optional.of(expectedSavedEntity));
		expectedSavedEntity.setActive(false);
		lenient().when(this.repository.save(expectedSavedEntity)).thenReturn(null);

		InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
			this.service.updateEntity(dto);
		});

		assertEquals("Some went wrong during the update, please, retry again later", exception.getMessage());
	}
}
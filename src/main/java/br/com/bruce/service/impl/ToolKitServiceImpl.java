package br.com.bruce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.bruce.dto.ToolKitRecordDTO;
import br.com.bruce.dto.ToolkitUpdateDto;
import br.com.bruce.entity.ToolkitEntity;
import br.com.bruce.repository.ToolkitRepository;
import br.com.bruce.service.ToolKitService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;

@Service
public class ToolKitServiceImpl implements ToolKitService {

	@Autowired
	private ToolkitRepository repository;

	@Override
	public ResponseEntity<Object> createEntity(ToolKitRecordDTO bookDto) {

		ToolkitEntity bookToSave = new ToolkitEntity();
		BeanUtils.copyProperties(bookDto, bookToSave);

		if (repository.findToolkitByName(bookDto.name()).isPresent()) {
			throw new BadRequestException("Entity already exists");
		}

		ToolkitEntity saved = repository.save(bookToSave);

		ToolKitRecordDTO savedBook = new ToolKitRecordDTO(saved.getId(), saved.getName(), saved.getDescription(),
				saved.getReleaseDate(), saved.getActive());

		return ResponseEntity.ok(savedBook);
	}

	@Override
	public ResponseEntity<List<ToolKitRecordDTO>> findAllEntites() {
		List<ToolKitRecordDTO> dtos = new ArrayList<>();
		List<ToolkitEntity> entities = repository.findAll();

		entities.forEach(entity -> {
			dtos.add(new ToolKitRecordDTO(entity.getId(), entity.getName(), entity.getDescription(),
					entity.getReleaseDate(), entity.getActive()));
		});

		if (dtos.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(dtos);
	}

	@Override
	public ResponseEntity<ToolKitRecordDTO> findEntityByName(String name) {

		Optional<ToolkitEntity> entityOp = repository.findToolkitByName(name);

		if (entityOp.isEmpty())
			throw new BadRequestException("Entity not found check the name");

		ToolKitRecordDTO dto = new ToolKitRecordDTO(entityOp.get().getId(), entityOp.get().getName(),
				entityOp.get().getDescription(), entityOp.get().getReleaseDate(), entityOp.get().getActive());

		return ResponseEntity.ok(dto);
	}

	@Override
	public ResponseEntity<Object> updateEntity(ToolkitUpdateDto dto) {
		Optional<ToolkitEntity> entityOpt = repository.findToolkitByName(dto.actualName());
		if(entityOpt.isEmpty())
			throw new BadRequestException("Entity not found check the name");
		ToolkitEntity entity = entityOpt.get();
		if(dto.description() == null && dto.active() == null && !dto.newName().equalsIgnoreCase(entity.getName()) && dto.newName() != null) {
			entity.setName(dto.newName());
		}else if(dto.description() == null) {
			entity.setActive(dto.active());
		}else if(dto.active() == null) {
			entity.setDescription(dto.description());
		}else {
			entity.setName(dto.newName());
			entity.setActive(dto.active());
			entity.setDescription(dto.description());
		}
		
		ToolkitEntity saved = repository.save(entity);
		if(Objects.isNull(saved))
			throw new InternalServerErrorException("Some went wrong during the update, please, retry again later");
		
		return ResponseEntity.accepted().body("Entity updated");
	}
}

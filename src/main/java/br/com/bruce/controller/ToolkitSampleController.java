package br.com.bruce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruce.dto.ToolKitRecordDTO;
import br.com.bruce.dto.ToolKitUpdateRequestRecordDto;
import br.com.bruce.dto.ToolkitUpdateDto;
import br.com.bruce.service.ToolKitService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/toolkit")
public class ToolkitSampleController {

	@Autowired
	private ToolKitService service;

	@PostMapping
	ResponseEntity<Object> createBook(@RequestBody @Valid ToolKitRecordDTO dto) {
		return service.createEntity(dto);
	}

	@GetMapping
	ResponseEntity<List<ToolKitRecordDTO>> findAllEntites() {

		return service.findAllEntites();
	}

	@GetMapping("/{name}")
	ResponseEntity<ToolKitRecordDTO> findEntityByName(@PathVariable(name = "name") String name) {

		return service.findEntityByName(name);
	}

	@PatchMapping("/{name}")
	ResponseEntity<Object> updateEntity(@RequestBody @Valid ToolKitUpdateRequestRecordDto requestDto,
			@PathVariable(name = "name") String Oldname) {

		return service.updateEntity(
				new ToolkitUpdateDto(Oldname, requestDto.newName(), requestDto.description(), requestDto.active()));
	}
}
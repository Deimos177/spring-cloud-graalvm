package br.com.bruce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.bruce.dto.ToolKitRecordDTO;
import br.com.bruce.dto.ToolkitUpdateDto;

public interface ToolKitService {
	
	public ResponseEntity<Object> createEntity(ToolKitRecordDTO dto);
	public ResponseEntity<List<ToolKitRecordDTO>> findAllEntites();
	public ResponseEntity<ToolKitRecordDTO> findEntityByName(String name);
	public ResponseEntity<Object> updateEntity(ToolkitUpdateDto dto);
}
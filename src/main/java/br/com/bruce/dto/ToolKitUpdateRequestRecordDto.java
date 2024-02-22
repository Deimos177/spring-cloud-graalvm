package br.com.bruce.dto;

import jakarta.validation.constraints.Size;

public record ToolKitUpdateRequestRecordDto(
		@Size(max = 50, min = 3, message = "do not have to be less than 3 characters and above than 50 characters") String newName,
		@Size(max = 10000, message = "do not have to be greater than 10000 characters") String description,
		Boolean active) {
}

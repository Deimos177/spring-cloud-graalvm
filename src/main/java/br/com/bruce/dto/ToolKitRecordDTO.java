package br.com.bruce.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ToolKitRecordDTO(Long id,
		@NotBlank(message = "cannot be null") @Size(max = 50, min = 3, message = "do not have to be less than 3 characters and above than 50 characters") String name,
		@NotBlank(message = "cannot be null") @Size(max = 10000, message = "do not have to be greater than 10000 characters") String description,
		@NotNull(message = "cannot be null") LocalDateTime releaseDate,
		@NotNull(message = "cannot be null") Boolean active) {
}
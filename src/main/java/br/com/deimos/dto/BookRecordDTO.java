package br.com.deimos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record BookRecordDTO(String UUID, @NotBlank String name, @NotBlank String shortDescription, @NotBlank LocalDateTime releaseDate) {

}
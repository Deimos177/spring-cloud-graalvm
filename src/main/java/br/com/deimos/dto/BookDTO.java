package br.com.deimos.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDTO {
	
	private String UUID;
	private String name;
	private String shortDescription;
	private LocalDate releaseDate;
}
package br.com.deimos.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table
@RequiredArgsConstructor
@Data
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String UUID;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String shortDescription;
	@Column(nullable = false)
	private LocalDate releaseDate;
}
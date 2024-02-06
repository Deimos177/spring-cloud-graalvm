package br.com.deimos.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.deimos.entity.Book;

@Repository
@ConfigurationProperties
public interface BookRepository extends JpaRepository<Book, String>{
	public Book findBookByName(String name);
}
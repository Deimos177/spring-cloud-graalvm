package br.com.deimos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.deimos")
@EnableAutoConfiguration
@EntityScan(basePackages = "br.com.deimos")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

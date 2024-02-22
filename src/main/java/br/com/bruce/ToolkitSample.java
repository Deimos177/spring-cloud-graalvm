package br.com.bruce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.bruce.repository")
@EntityScan(basePackages = "br.com.bruce")
public class ToolkitSample {

	public static void main(String[] args) {
		SpringApplication.run(ToolkitSample.class, args);
	}

}
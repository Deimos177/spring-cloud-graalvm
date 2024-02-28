package br.com.bruce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "br.com.bruce.repository")
//@EntityScan(basePackages = "br.com.bruce.entity")
public class ToolkitSample {

	public static void main(String[] args) {
		SpringApplication.run(ToolkitSample.class, args);
	}

}
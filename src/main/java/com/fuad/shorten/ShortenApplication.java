package com.fuad.shorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class ShortenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenApplication.class, args);
	}

}

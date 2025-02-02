package com.odin;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimecoreApplication {

	public static void main(String[] args) {
		// import environment variables from .env
		Dotenv dotenv = Dotenv.configure()
				.filename(".env")
				.load();

		System.setProperty("DB_MYSQL_URL", dotenv.get("DB_MYSQL_URL"));
		System.setProperty("DB_MYSQL_USERNAME", dotenv.get("DB_MYSQL_USERNAME"));
		System.setProperty("DB_MYSQL_PASSWORD", dotenv.get("DB_MYSQL_PASSWORD"));
		SpringApplication.run(TimecoreApplication.class, args);
	}



}

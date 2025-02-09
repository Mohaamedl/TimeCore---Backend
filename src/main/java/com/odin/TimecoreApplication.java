package com.odin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Main application class for the Timecore application.
 */
@SpringBootApplication
public class TimecoreApplication {

    /**
     * Default constructor.
     */
    public TimecoreApplication() {
    }

    /**
     * Main method to start the Timecore application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // import environment variables from .env
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("DB_MYSQL_URL", dotenv.get("DB_MYSQL_URL"));
        System.setProperty("DB_MYSQL_USERNAME", dotenv.get("DB_MYSQL_USERNAME"));
        System.setProperty("DB_MYSQL_PASSWORD", dotenv.get("DB_MYSQL_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        SpringApplication.run(TimecoreApplication.class, args);
    }
}



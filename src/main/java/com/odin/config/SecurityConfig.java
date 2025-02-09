package com.odin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Default constructor.
     */
    public SecurityConfig() {
    }

    /**
     * Defines a PasswordEncoder bean for password hashing.
     *
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
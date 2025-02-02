package com.odin.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;


import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
public class AppConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Autorize -> Autorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

                return http.build();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new org.springframework.web.multipart.support.StandardServletMultipartResolver();
    }

    private CorsConfigurationSource corsConfigurationSource()
    {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(
                        Arrays.asList(
                                "http://localhost:5173",
                                "http://localhost:3000"
                        )

                );

                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };

    }




}

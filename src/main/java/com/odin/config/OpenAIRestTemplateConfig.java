package com.odin.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class OpenAIRestTemplateConfig {
    @Value("${openai.api.key}")
    private String openaiApiKey;

    @PostConstruct
    public void init() {
        if (openaiApiKey == null || openaiApiKey.trim().isEmpty()) {
            throw new IllegalStateException("OpenAI API key is not set!");
        }
        System.out.println("OpenAI API key prefix: " + openaiApiKey.substring(0, 8) + "...");
    }

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBearerAuth(openaiApiKey);
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            System.out.println("Making request to: " + request.getURI());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
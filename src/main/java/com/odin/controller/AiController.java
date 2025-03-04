package com.odin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.odin.dto.ChatRequest;
import com.odin.dto.ChatResponse;
import com.odin.service.interfaces.UserService;

@RestController
@RequestMapping("api/ai")
public class AiController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private UserService userService;


    @GetMapping("/chat")
    public ResponseEntity<?> complete(@RequestHeader("Authorization") String jwt,
                                      @RequestParam("prompt") String prompt)
    {
        try {
            if (userService.findUserByJwt(jwt).getId() == null )
            {
                throw new Exception("User not found by the given JWT");

            }

            ChatRequest request = new ChatRequest(model, prompt);
            request.setStore(true);  // Set store to true

            // Add debug logging
            System.out.println("Making request to OpenAI with model: " + model);
            System.out.println("API URL: " + apiUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<ChatResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                ChatResponse.class
            );
            
            ChatResponse response = responseEntity.getBody();
            
            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.badRequest().body("No response from OpenAI");
            }
            
            return ResponseEntity.ok(response.getChoices().getFirst().getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
}

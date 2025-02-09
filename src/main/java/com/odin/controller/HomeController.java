package com.odin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling basic application endpoints.
 * Provides health check and test endpoints.
 */
@RestController
public class HomeController {

    /**
     * Default constructor
     */
    public HomeController() {
        // Default constructor
    }

    /**
     * Basic health check endpoint
     * @return String indicating API is working
     */
    @GetMapping
    public String home() {
        return "Testing api";
    }

    /**
     * Secure endpoint test
     * @return String indicating secure endpoint is working
     */
    @GetMapping("/api")
    public String secure() {
        return "Secure endpoint";
    }
}

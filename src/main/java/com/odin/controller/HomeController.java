package com.odin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping
    public String home()
    {
        return "Testing api";
    }
    @GetMapping("/api")
    public String secure()
    {
        return "Secure endpoint";
    }



}

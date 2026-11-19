package com.ejemplo.catalogo.adapter.in.web.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of("mensaje", "¡Hola, Spring Boot!");
    }
}


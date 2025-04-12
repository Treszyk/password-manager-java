package com.treszyk.passwordmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Operation(summary = "Hello World endpoint", description = "Returns a simple Hello World message")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}

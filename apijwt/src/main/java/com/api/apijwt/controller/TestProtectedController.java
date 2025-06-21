package com.api.apijwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProtectedController {

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "Você acessou um endpoint protegido!";
    }
}
package com.example.securservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/login"})
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = {"/index"})
    public String helloPage() {
        return "index";
    }
}


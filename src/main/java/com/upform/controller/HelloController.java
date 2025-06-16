package com.upform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHi() {
        return "Hey Court \uD83D\uDC4B UpForm is alive!";
    }
}

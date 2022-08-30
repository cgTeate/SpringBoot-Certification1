package com.calvinteater.springtest1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class firstController {

    @GetMapping("/welcome")
    public String first_api(){
        return  "Welcome to the first_api Project";
    }
}

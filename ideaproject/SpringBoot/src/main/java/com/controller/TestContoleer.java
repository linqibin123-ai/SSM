package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoleer {

    @RequestMapping("/sayhello")
    public String sayHello() {
        return "Hello World!";
    }
}

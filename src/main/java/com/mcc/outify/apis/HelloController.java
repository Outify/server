package com.mcc.outify.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    String Hello() {
        return "Hello, World! This is ARLO's outify";
    }

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}

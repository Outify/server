package com.mcc.outify.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {

    @GetMapping("/")
    String Hello() {
        return "index";
    }
}

@RestController
class ApiController {

    @GetMapping("/healthcheck")
    public String getMessage() {
        return "ok";
    }
}
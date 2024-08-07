package com.mcc.outify.apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    String Hello(Model model) {
        model.addAttribute("message", "Hello, World! This is ARLO's outify");
        return "index";
    }
}

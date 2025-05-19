package com.bakeryshehan.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "staff-dashboard.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin-panel.html";
    }
}

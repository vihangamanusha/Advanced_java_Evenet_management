package com.fot.eventsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home"; // must match home.html
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}


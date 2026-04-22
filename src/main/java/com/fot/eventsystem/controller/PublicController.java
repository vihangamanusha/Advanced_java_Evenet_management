package com.fot.eventsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/public/home")
    public String publicHome() {
        return "public/home"; // public/home.html
    }
}
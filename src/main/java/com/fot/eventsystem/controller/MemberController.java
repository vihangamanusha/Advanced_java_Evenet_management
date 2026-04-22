package com.fot.eventsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/member/home")
    public String memberHome() {
        return "member/home"; // member/home.html
    }
}
package com.fot.eventsystem.controller;

import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VenueRepository venueRepository;

    @GetMapping("/")
    public String home() {
        return "home"; // must match home.html
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("venues", venueRepository.findAll());
        return "about";
    }
}


package com.fot.eventsystem.controller;

import com.fot.eventsystem.repository.NewsRepository;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/")
    public String home(Model model) {
            model.addAttribute("newsList", newsRepository.findAll());
            return "home";
        }


    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("venues", venueRepository.findAll());
        return "about";
    }




}


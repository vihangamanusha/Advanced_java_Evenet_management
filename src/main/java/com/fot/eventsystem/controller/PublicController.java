package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class PublicController {

    @Autowired
    private VenueRepository venueRepository;

    @GetMapping("/public/home")
    public String publicHome(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        // if not logged in go home
        if (user == null) {
            return "redirect:/";
        }

        // send user to page
        model.addAttribute("user", user);

        // send venues (for booking form dropdown)
        model.addAttribute("venues", venueRepository.findAll());

        return "public/home";
    }
}
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

        try {
            User user = (User) session.getAttribute("loggedUser");

            // if not logged in
            if (user == null) {
                return "redirect:/";
            }

            model.addAttribute("user", user);
            model.addAttribute("venues", venueRepository.findAll());

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/?error=true";
        }

        return "public/home";
    }
}
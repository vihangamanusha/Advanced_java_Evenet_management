package com.fot.eventsystem.controller;

import org.springframework.ui.Model;
import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/venues")
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    // Show page
    @GetMapping
    public String showVenues(Model model) {
        model.addAttribute("venues", venueRepository.findAll());
        return "admin/manage-venues";
    }

    // Add venue
    @PostMapping("/add")
    public String addVenue(Venues venue) {
        venueRepository.save(venue);
        return "redirect:/admin/venues";
    }

    // Delete venue
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueRepository.deleteById(id);
        return "redirect:/admin/venues";
    }
}
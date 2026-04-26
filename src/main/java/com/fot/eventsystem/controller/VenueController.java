package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@RequestMapping("/admin/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // SHOW ALL
    @GetMapping
    public String showVenues(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        return "admin/manage-venues";
    }

    // SAVE
    @PostMapping("/save")
    public String saveVenue(@RequestParam String name,
                            @RequestParam int capacity,
                            @RequestParam double price,
                            @RequestParam MultipartFile imageFile) {

        try {
            Venues venue = new Venues();
            venue.setName(name);
            venue.setCapacity(capacity);
            venue.setPrice(price);

            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename().replace(" ", "_");
                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                imageFile.transferTo(new File(uploadDir + fileName));
                venue.setImageName(fileName);
            }

            venueService.saveVenue(venue);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/venues";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "redirect:/admin/venues";
    }

    // EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editVenue(@PathVariable Long id, Model model) {

        model.addAttribute("venue", venueService.getVenueById(id));
        model.addAttribute("venues", venueService.getAllVenues());

        return "admin/manage-venues";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateVenue(@ModelAttribute Venues venue,
                              @RequestParam("imageFile") MultipartFile file) {

        try {
            Venues existing = venueService.getVenueById(venue.getId());

            if (existing != null) {

                existing.setName(venue.getName());
                existing.setCapacity(venue.getCapacity());
                existing.setPrice(venue.getPrice());

                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    String uploadDir = System.getProperty("user.dir") + "/uploads/";

                    file.transferTo(new File(uploadDir + fileName));
                    existing.setImageName(fileName);
                }

                venueService.saveVenue(existing);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/venues";
    }
}
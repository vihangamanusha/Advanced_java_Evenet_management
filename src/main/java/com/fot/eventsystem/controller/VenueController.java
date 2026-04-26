package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/venues")
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    // SHOW ALL
    @GetMapping
    public String showVenues(Model model) {
        model.addAttribute("venues", venueRepository.findAll());
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

                /*
                String uploadDir = System.getProperty("user.dir")
                        + "/src/main/resources/static/images/";*/
                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                imageFile.transferTo(new File(uploadDir + fileName));

                venue.setImageName(fileName);
            }

            venueRepository.save(venue);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/venues";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueRepository.deleteById(id);
        return "redirect:/admin/venues";
    }

    // ✅ EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editVenue(@PathVariable Long id, Model model) {

        Venues venue = venueRepository.findById(id).orElse(null);

        model.addAttribute("venue", venue);
        model.addAttribute("venues", venueRepository.findAll());

        return "admin/manage-venues";
    }

    @PostMapping("/update")
    public String updateVenue(@ModelAttribute Venues venue,
                              @RequestParam("imageFile") MultipartFile file) {

        try {
            Venues existing = venueRepository.findById(venue.getId()).orElse(null);

            if (existing != null) {

                existing.setName(venue.getName());
                existing.setCapacity(venue.getCapacity());
                existing.setPrice(venue.getPrice());

                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    file.transferTo(new File("src/main/resources/static/images/" + fileName));
                    existing.setImageName(fileName);
                }

                venueRepository.save(existing);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/venues";
    }
}
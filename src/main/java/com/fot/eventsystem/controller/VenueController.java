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

    // ✅ Show page
    @GetMapping
    public String showVenues(Model model) {
        model.addAttribute("venues", venueRepository.findAll());
        return "admin/manage-venues";
    }

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

            // ✅ STEP 2 HERE (SAVE IMAGE FILE)
            if (!imageFile.isEmpty()) {

                String originalName = imageFile.getOriginalFilename();

                String fileName = originalName.replace(" ", "_");

                String uploadDir = System.getProperty("user.dir")
                        + "/src/main/resources/static/images/";

                File saveFile = new File(uploadDir + fileName);

                imageFile.transferTo(saveFile);

                venue.setImageName(fileName);
            }

            venueRepository.save(venue);

            return "redirect:/admin/venues";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/venues?error";
        }
    }
    // ✅ Delete venue
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueRepository.deleteById(id);
        return "redirect:/admin/venues";
    }
}
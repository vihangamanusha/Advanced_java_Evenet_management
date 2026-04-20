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

    // ✅ Save venue with image
    @PostMapping("/save")
    public String saveVenue(
            @RequestParam("name") String name,
            @RequestParam("capacity") int capacity,
            @RequestParam("price") double price,
            @RequestParam("imageFile") MultipartFile file
    ) throws IOException {

        Venues venue = new Venues();
        venue.setName(name);
        venue.setCapacity(capacity);
        venue.setPrice(price);

        // 🔥 Save image
        String uploadDir = "src/main/resources/static/images/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        file.transferTo(new File(uploadDir + fileName));

        venue.setImage(fileName);

        venueRepository.save(venue); // ✅ FIXED

        return "redirect:/admin/venues";
    }

    // ✅ Delete venue
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueRepository.deleteById(id);
        return "redirect:/admin/venues";
    }
}
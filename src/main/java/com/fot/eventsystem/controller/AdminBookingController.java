package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminBookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // show all bookings
    @GetMapping("/admin/request")
    public String requestPage(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "admin/request";
    }

    // approve
    @PostMapping("/admin/approve/{id}")
    public String approve(@PathVariable int id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking != null) {
            booking.setStatus("APPROVED");
            bookingRepository.save(booking);
        }

        return "redirect:/admin/booking-request";
    }

    //rejct
    @PostMapping("/admin/reject/{id}")
    public String reject(@PathVariable int id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking != null) {
            booking.setStatus("REJECTED");
            bookingRepository.save(booking);
        }

        return "redirect:/admin/booking-request";
    }
}
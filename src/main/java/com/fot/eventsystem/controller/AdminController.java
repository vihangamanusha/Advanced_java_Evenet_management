package com.fot.eventsystem.controller;

import org.springframework.ui.Model;
import com.fot.eventsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        int total = (int) bookingRepository.count();
        int approved = bookingRepository.countByStatus("APPROVED");
        int pending = bookingRepository.countByStatus("PENDING");
        int rejected = bookingRepository.countByStatus("REJECTED");

        model.addAttribute("total", total);
        model.addAttribute("approved", approved);
        model.addAttribute("pending", pending);
        model.addAttribute("rejected", rejected);

        model.addAttribute("bookings", bookingRepository.findAll());

        return "admin/dashboard";
    }

    @GetMapping("/booking-request")
    public String bookingRequestPage(Model model) {

        model.addAttribute("bookings", bookingRepository.findAll());

        return "admin/booking-request";
    }
}
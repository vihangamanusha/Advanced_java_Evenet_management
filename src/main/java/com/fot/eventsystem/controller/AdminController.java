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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @Autowired
    private BookingRepository bookingRepository;


    public String adminDashboard(Model model) {

        int total = (int) bookingRepository.count();
        int approved = (int) bookingRepository.countByStatus("APPROVED");
        int pending = (int) bookingRepository.countByStatus("PENDING");
        int rejected = (int) bookingRepository.countByStatus("REJECTED");

        model.addAttribute("total", total);
        model.addAttribute("approved", approved);
        model.addAttribute("pending", pending);
        model.addAttribute("rejected", rejected);

        model.addAttribute("bookings", bookingRepository.findAll());

        return "admin/dashboard";
    }

    @GetMapping("/booking-request")
    public String bookingRequestPage() {
        return "admin/booking-request"; // optional page
    }
}
package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        try {
            int total = (int) bookingRepository.count();
            int approved = bookingRepository.countByStatus("APPROVED");
            int pending = bookingRepository.countByStatus("PENDING");
            int rejected = bookingRepository.countByStatus("REJECTED");

            model.addAttribute("total", total);
            model.addAttribute("approved", approved);
            model.addAttribute("pending", pending);
            model.addAttribute("rejected", rejected);

            model.addAttribute("bookings", bookingRepository.findAll());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load dashboard data");
        }

        return "admin/dashboard";
    }

    @GetMapping("/booking-request")
    public String bookingRequestPage(Model model) {

        try {
            model.addAttribute("bookings", bookingRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load booking requests");
        }

        return "admin/booking-request";
    }

    @GetMapping("/approved-events")
    public String approvedEvents(Model model) {

        try {
            List<Booking> approvedList =
                    bookingRepository.findByStatusIgnoreCase("APPROVED");

            model.addAttribute("bookings", approvedList);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load approved events");
        }

        return "admin/approved-events";
    }

    @GetMapping("/calendar")
    public String calendarPage() {
        return "admin/calendar";
    }

    @GetMapping("/calendar-events")
    @ResponseBody
    public List<Map<String, Object>> getCalendarEvents() {

        List<Map<String, Object>> events = new ArrayList<>();

        try {
            List<Booking> approved =
                    bookingRepository.findByStatusIgnoreCase("APPROVED");

            for (Booking b : approved) {
                Map<String, Object> event = new HashMap<>();

                event.put("title", b.getEventName());
                event.put("start", b.getEventDate()); // YYYY-MM-DD

                events.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
}
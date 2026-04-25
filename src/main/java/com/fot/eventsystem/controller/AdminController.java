package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.ui.Model;
import com.fot.eventsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/approved-events")
    public String approvedEvents(Model model) {

        List<Booking> approvedList =
                bookingRepository.findByStatusIgnoreCase("APPROVED");

        model.addAttribute("bookings", approvedList);

        return "admin/approved-events";
    }

    @GetMapping("/calendar")
    public String calendarPage() {
        return "admin/calendar";
    }

    @GetMapping("/calendar-events")
    @ResponseBody
    public List<Map<String, Object>> getCalendarEvents() {

        List<Booking> approved = bookingRepository.findByStatusIgnoreCase("APPROVED");

        List<Map<String, Object>> events = new ArrayList<>();

        for (Booking b : approved) {
            Map<String, Object> event = new HashMap<>();

            event.put("title", b.getEventName());
            event.put("start", b.getEventDate()); // must be YYYY-MM-DD

            events.add(event);
        }

        return events;
    }

}
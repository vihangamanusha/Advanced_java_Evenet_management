package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.repository.BookingRepository;
import com.fot.eventsystem.repository.NewsRepository;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/")
    public String home(Model model) {

        try {
            model.addAttribute("newsList", newsRepository.findAll());
            model.addAttribute("approvedEvents",
                    bookingRepository.findByStatus("APPROVED"));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("newsList", new ArrayList<>());
            model.addAttribute("approvedEvents", new ArrayList<>());
        }

        return "home";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {

        try {
            model.addAttribute("venues", venueRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("venues", new ArrayList<>());
        }

        return "about";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }

    @GetMapping("/calendar-events")
    @ResponseBody
    public List<Map<String, Object>> getPublicCalendarEvents() {

        List<Map<String, Object>> events = new ArrayList<>();

        try {
            List<Booking> approved =
                    bookingRepository.findByStatusIgnoreCase("APPROVED");

            for (Booking b : approved) {
                Map<String, Object> event = new HashMap<>();
                event.put("title", b.getEventName());
                event.put("start", b.getEventDate());
                events.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
}
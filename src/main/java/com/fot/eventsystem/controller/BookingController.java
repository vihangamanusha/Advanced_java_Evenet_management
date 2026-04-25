package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public String saveBooking(
            @RequestParam String eventName,
            @RequestParam String eventDate,
            @RequestParam String eventTimeStart,
            @RequestParam String eventTimeend,
            @RequestParam int peopleCount,
            @RequestParam String venue,
            @RequestParam String description,
            @RequestParam(required = false) String price,
            @RequestParam(value = "eventImage", required = false) MultipartFile eventImage,
            @RequestParam(value = "bankSlip", required = false) MultipartFile bankSlip,
            HttpSession session
    ) throws Exception {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/?loginError=true";
        }

        if (price == null || price.isEmpty()) {
            price = "0";
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // IMAGE
        String eventImageName = null;
        if (eventImage != null && !eventImage.isEmpty()) {
            eventImageName = System.currentTimeMillis() + "_" + eventImage.getOriginalFilename();
            eventImage.transferTo(new File(uploadDir + eventImageName));
        }

        // BANK SLIP
        String bankSlipName = null;
        if (bankSlip != null && !bankSlip.isEmpty()) {
            bankSlipName = System.currentTimeMillis() + "_" + bankSlip.getOriginalFilename();
            bankSlip.transferTo(new File(uploadDir + bankSlipName));
        }

        // CREATE MODEL
        Booking booking = new Booking();
        booking.setEventName(eventName);
        booking.setEventDate(eventDate);
        booking.setEventTimeStart(eventTimeStart);
        booking.setEventTimeend(eventTimeend);
        booking.setPeopleCount(peopleCount);
        booking.setVenue(venue);
        booking.setDescription(description);
        booking.setPrice(price);
        booking.setEventImage(eventImageName);
        booking.setBankSlip(bankSlipName);
        booking.setUserEmail(user.getEmail());

        // SAVE USING SERVICE
        bookingService.saveBooking(booking);

        return "redirect:/member/home?success=true";
    }

    @GetMapping("/my-bookings")
    public String myBookings(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        List<Booking> bookings =
                bookingService.getBookingsByUserEmail(user.getEmail());

        model.addAttribute("bookings", bookings);

        return "my-bookings";
    }
}
package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.BookingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/book")
    public String saveBooking(
            @RequestParam String eventName,
            @RequestParam String eventDate,
            @RequestParam String eventTimeStart,
            @RequestParam String eventTimeend,
            @RequestParam int peopleCount,
            @RequestParam String venue,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam("eventImage") MultipartFile eventImage,
            @RequestParam("bankSlip") MultipartFile bankSlip,
            HttpSession session
    ) throws Exception {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/?loginError=true";
        }

        String uploadDir = "src/main/resources/static/images/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // save event image
        String eventImageName = System.currentTimeMillis() + "_" + eventImage.getOriginalFilename();
        eventImage.transferTo(new File(uploadDir + eventImageName));

        // save bank slip
        String bankSlipName = System.currentTimeMillis() + "_" + bankSlip.getOriginalFilename();
        bankSlip.transferTo(new File(uploadDir + bankSlipName));

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

        booking.setUserEmail(user.getEmail()); // 🔥 link to user

        bookingRepository.save(booking);

        return "redirect:/member/home?success=true";
    }
}
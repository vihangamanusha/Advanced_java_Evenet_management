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
            @RequestParam(required = false) String price,
            @RequestParam(value = "eventImage", required = false) MultipartFile eventImage,
            @RequestParam(value = "bankSlip", required = false) MultipartFile bankSlip,
            HttpSession session
    ) throws Exception {

        System.out.println("🔥 Booking started");

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            System.out.println("❌ No user in session");
            return "redirect:/?loginError=true";
        }

        System.out.println("✅ User: " + user.getEmail());

        if (price == null || price.isEmpty()) {
            price = "0";
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // ================= IMAGE =================
        String eventImageName = null;

        if (eventImage != null && !eventImage.isEmpty()) {
            eventImageName = System.currentTimeMillis() + "_" + eventImage.getOriginalFilename();
            File imageFile = new File(uploadDir + eventImageName);
            eventImage.transferTo(imageFile);
        }

        // ================= BANK SLIP =================
        String bankSlipName = null;

        if (bankSlip != null && !bankSlip.isEmpty()) {
            bankSlipName = System.currentTimeMillis() + "_" + bankSlip.getOriginalFilename();
            File slipFile = new File(uploadDir + bankSlipName);
            bankSlip.transferTo(slipFile);
        }

        // ================= SAVE BOOKING =================
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

        System.out.println("💾 Saving to DB...");
        bookingRepository.save(booking);

        System.out.println("✅ Saved successfully!");

        return "redirect:/member/home?success=true";
    }
}
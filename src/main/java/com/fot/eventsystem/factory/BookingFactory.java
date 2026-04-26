package com.fot.eventsystem.factory;

import com.fot.eventsystem.model.Booking;

public class BookingFactory {

    public static Booking createBooking(
            String eventName,
            String eventDate,
            String eventTimeStart,
            String eventTimeend,
            int peopleCount,
            String venue,
            String description,
            String price,
            String eventImage,
            String bankSlip,
            String userEmail
    ) {

        Booking booking = new Booking();

        booking.setEventName(eventName);
        booking.setEventDate(eventDate);
        booking.setEventTimeStart(eventTimeStart);
        booking.setEventTimeend(eventTimeend);
        booking.setPeopleCount(peopleCount);
        booking.setVenue(venue);
        booking.setDescription(description);
        booking.setPrice(price);
        booking.setEventImage(eventImage);
        booking.setBankSlip(bankSlip);
        booking.setUserEmail(userEmail);

        return booking;
    }
}
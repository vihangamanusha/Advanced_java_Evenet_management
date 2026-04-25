package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    long count();
    long countByStatus(String status);
}

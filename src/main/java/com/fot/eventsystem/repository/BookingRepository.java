package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

        int countByStatus(String status);
        List<Booking> findByStatusIgnoreCase(String status);

}

package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    // GET ALL
    public List<Venues> getAllVenues() {
        return venueRepository.findAll();
    }

    // SAVE
    public void saveVenue(Venues venue) {
        venueRepository.save(venue);
    }

    // FIND BY ID
    public Venues getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    // DELETE
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
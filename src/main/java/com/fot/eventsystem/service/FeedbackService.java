package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Feedback;
import com.fot.eventsystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Save feedback
    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    // Get all feedbacks
    public Iterable<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}
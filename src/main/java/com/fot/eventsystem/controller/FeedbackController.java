package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Feedback;
import com.fot.eventsystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Show contact page
    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    // Save feedback
    @PostMapping("/contact/save")
    public String saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
        return "redirect:/contact?success";
    }

    // Admin view feedback
    @GetMapping("/admin/feedback")
    public String viewFeedback(Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "admin/feedback-list";
    }
}
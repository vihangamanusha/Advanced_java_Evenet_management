package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Feedback;
import com.fot.eventsystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Show contact page
    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    // Save feedback
    @PostMapping("/contact/save")
    public String saveFeedback(Feedback feedback) {

        try {

            // basic validation (prevents blank submit issues)
            if (feedback.getName() == null || feedback.getName().trim().isEmpty() ||
                    feedback.getEmail() == null || feedback.getEmail().trim().isEmpty() ||
                    feedback.getMessage() == null || feedback.getMessage().trim().isEmpty()) {

                return "redirect:/contact?error=empty";
            }

            feedbackService.saveFeedback(feedback);
            return "redirect:/contact?success";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/contact?error=true";
        }
    }

    // Admin view feedback
    @GetMapping("/admin/feedback")
    public String viewFeedback(Model model) {

        try {
            model.addAttribute("feedbacks", feedbackService.getAllFeedback());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to load feedback");
        }

        return "admin/feedback-list";
    }
}
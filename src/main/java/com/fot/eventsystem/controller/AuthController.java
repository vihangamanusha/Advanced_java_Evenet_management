package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // SIGNUP
    @PostMapping("/signup")
    public String signup(User user, Model model) {

        if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {

            model.addAttribute("error", "All fields are required!");
            return "index";
        }

        userRepository.save(user);
        model.addAttribute("success", "Account created successfully!");
        return "index";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index";
        }
    }
}
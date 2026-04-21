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

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {

            // ✅ SUCCESS
            model.addAttribute("success", "Login successful!");

            // 🔥 Redirect based on user type
            if (user.getUsertype().equals("MEMBER")) {
                return "redirect:/booking/member";
            } else {
                return "redirect:/booking/public";
            }

        } else {
            // ❌ ERROR
            return "redirect:/?error=true";
        }
    }
    // SIGNUP
    @PostMapping("/signup")
    public String signup(User user, Model model) {

        // 1. Check empty fields
        if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {

            model.addAttribute("error", "Please fill all required fields!");
            return "redirect:/admin/dashboard";
        }

        // 2. Check if user already exists
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            model.addAttribute("error", "User already exists!");
            return "redirect:/?success";
        }

        // 3. Save user
        userRepository.save(user);

        model.addAttribute("success", "Account created successfully!");
        return "redirect:/?success";
    }
}
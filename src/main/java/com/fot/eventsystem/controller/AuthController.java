package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {

            // 🔥 SAVE USER IN SESSION
            session.setAttribute("loggedUser", user);

            // 🔥 ROLE BASED REDIRECT
            if ("ADMIN".equals(user.getUsertype())) {
                return "redirect:/admin/dashboard";
            } else if ("MEMBER".equals(user.getUsertype())) {
                return "redirect:/member/home";
            } else {
                return "redirect:/public/home";
            }

        } else {
            return "redirect:/?loginError=true";
        }
    }
    // SIGNUP
    @PostMapping("/signup")
    public String signup(User user) {

        // check existing user
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "redirect:/?exists=true";
        }

        userRepository.save(user);

        return "redirect:/?registered=true";
    }
}
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

            if ("MEMBER".equals(user.getUsertype())) {
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
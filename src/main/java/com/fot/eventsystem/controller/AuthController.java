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

            // 🔥 SAVE USER IN SESSION (ONLY ONCE)
            session.setAttribute("loggedUser", user);

            // 🔥 ROLE BASED REDIRECT
            if ("ADMIN".equalsIgnoreCase(user.getUsertype())) {
                return "redirect:/admin/dashboard";
            }

            if ("MEMBER".equalsIgnoreCase(user.getUsertype())) {
                return "redirect:/member/home";
            }

            return "redirect:/public/home";

        } else {
            return "redirect:/?loginError=true";
        }
    }
    @PostMapping("/signup")
    public String signup(
            @RequestParam String email,
            @RequestParam String pwd,
            @RequestParam String confirmPwd,
            @RequestParam String usertype,
            @RequestParam(required = false) String registerno,
            @RequestParam(required = false) String phoneno,
            @RequestParam(required = false) String orgname
    ) {

        // 🔥 PASSWORD CHECK
        if (!pwd.equals(confirmPwd)) {
            return "redirect:/?passwordError=true";
        }

        // 🔥 EMAIL EXISTS CHECK (IMPORTANT)
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            return "redirect:/?exists=true";
        }

        // 🔥 SAVE NEW USER
        User user = new User();
        user.setEmail(email);
        user.setPassword(pwd);
        user.setUsertype(usertype);
        user.setRegisterno(registerno);
        user.setPhoneno(phoneno);
        user.setOrgname(orgname);

        userRepository.save(user);

        return "redirect:/?registered=true";
    }
}
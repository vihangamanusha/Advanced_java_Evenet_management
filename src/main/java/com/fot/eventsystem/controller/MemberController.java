package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class MemberController {

    @GetMapping("/member/home")
    public String memberHome(HttpSession session, Model model) {

        Object obj = session.getAttribute("loggedUser");

        if (obj == null) {
            return "redirect:/"; // not logged in
        }

        User user = (User) obj;

        // 🔥 send user to HTML
        model.addAttribute("user", user);

        return "member/home";
    }
}
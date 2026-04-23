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

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/?loginError=true";
        }

        //  restrict access
        if (!"MEMBER".equals(user.getUsertype())) {
            return "redirect:/"; // not allowed
        }

        model.addAttribute("user", user);

        return "member/home";
    }
}
package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {

            session.setAttribute("loggedUser", user);

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

        if (!pwd.equals(confirmPwd)) {
            return "redirect:/?passwordError=true";
        }

        if (userService.findByEmail(email) != null) {
            return "redirect:/?exists=true";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(pwd);
        user.setUsertype(usertype);
        user.setRegisterno(registerno);
        user.setPhoneno(phoneno);
        user.setOrgname(orgname);

        userService.saveUser(user);

        return "redirect:/?registered=true";
    }
}
package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showUsers(Model model) {

        try {
            model.addAttribute("users", userService.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load users");
        }

        return "admin/manage-users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {

        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {

        try {
            model.addAttribute("user", userService.getUserById(id));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "User not found");
        }

        return "admin/edit-user";
    }

    @PostMapping("/update")
    public String updateUser(User user) {

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/users";
    }
}
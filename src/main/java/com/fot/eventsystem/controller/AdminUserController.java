package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    // 🔹 Show all users
    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/manage-users";
    }

    // 🔹 Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    // 🔹 Load edit page
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    // 🔹 Update user
    @PostMapping("/update")
    public String updateUser(User user) {
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}

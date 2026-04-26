package com.fot.eventsystem.service;

import com.fot.eventsystem.model.User;
import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // LOGIN check
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    // FIND BY EMAIL
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    // SAVE / UPDATE USER
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // DELETE USER
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
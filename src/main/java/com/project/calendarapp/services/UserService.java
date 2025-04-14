package com.project.calendarapp.services;

import com.project.calendarapp.models.User;
import com.project.calendarapp.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (emailExists(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (usernameExists(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean validateLoginWithUsername(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    // does the email exist already
    public boolean emailExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    // is the username already taken
    public boolean usernameExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }



}

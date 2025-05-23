package com.cts.elearn.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.elearn.dao.UserRepository;
import com.cts.elearn.dto.ForgotPasswordRequest;
import com.cts.elearn.dto.LoginRequest;
import com.cts.elearn.dto.LoginResponse;
import com.cts.elearn.dto.UserResponse;
import com.cts.elearn.entity.User;
import com.cts.elearn.exception.UserNotFoundException;

@Service
@Transactional	
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public User registerUser(User user) {
        System.out.println("🔹 Original Password Before Any Processing: " + user.getPassword());

        // ❌ Fix: Check if password is already hashed (to prevent double hashing)
        if (user.getPassword().startsWith("$2a$10$")) {
            System.out.println("❌ ERROR: Password is already hashed! Skipping hashing.");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        System.out.println("🔹 Hashed Password to Store in DB: " + user.getPassword());

        return userRepository.save(user);
    }
    
    public UserResponse getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Ensure this returns contactNumber correctly
        return new UserResponse(user.getUserId(), user.getName(), user.getContactNumber(), user.getEmail());
    }


    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return users.getContent();
    }
 
    
    @Transactional
    public String resetPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByContactNumber(request.getContactNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newPassword = request.getNewPassword().trim();

        // Fetch current password from DB
        System.out.println("🔹 Current Password in DB (Before Update): " + user.getPassword());

        //Prevent Double Hashing
        if (user.getPassword().startsWith("$2a$10$")) {
            System.out.println("❌ ERROR: Password is already hashed! Preventing rehash.");
        }

        // Encode password correctly
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        System.out.println("✅ Password successfully updated! Stored Hash: " + hashedPassword);

        // Fetch from DB immediately after save
        User updatedUser = userRepository.findByContactNumber(request.getContactNumber())
                .orElseThrow(() -> new RuntimeException("User not found after update!"));

        System.out.println("🔹 Password from DB after Update: " + updatedUser.getPassword());

        return "Password reset successful!";
    }


    
    public LoginResponse loginUser(LoginRequest loginRequest) {
        System.out.println("🔹 Received Request Body: " + loginRequest);
        
        User user = userRepository.findByContactNumber(loginRequest.getContactNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String enteredPassword = loginRequest.getPassword(); // ✅ No trim()
        
        System.out.println("🔹 Entered Password: [" + enteredPassword + "]");
        System.out.println("🔹 Stored Hashed Password: [" + user.getPassword() + "]");

        boolean passwordMatches = passwordEncoder.matches(enteredPassword, user.getPassword());
        System.out.println("🔹 Password Match Result: " + passwordMatches);

        if (!passwordMatches) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse("Login Successful", user.getRole().name(), user.getStatus().name());
    }



}

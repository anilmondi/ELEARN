package com.cts.elearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.elearn.dto.ForgotPasswordRequest;
import com.cts.elearn.dto.LoginRequest;
import com.cts.elearn.dto.LoginResponse;
import com.cts.elearn.dto.UserResponse;
import com.cts.elearn.entity.User;
import com.cts.elearn.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for user operations")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/test")
    public String test() {
        return "User Service is working!";
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
    	if (user.getStatus() == null) {  // Ensure status is set
            user.setStatus(User.Status.Active);
        }
        return ResponseEntity.status(201).body(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
    
    @GetMapping("/{id}")   // This should match Feign Client path
    public UserResponse getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR', 'LEARNER')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getUsers")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequest request) {
        String response = userService.resetPassword(request);
        return ResponseEntity.ok(response);
    }
    
    
}

package com.service;
import com.dao.UserDAO;
import com.dao.RoleDAO;
import com.entity.User;
import com.entity.Role;
import com.utils.PasswordUtil;
import com.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

public class AuthService {
    
    private UserDAO userDAO = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();
    
    public String registerUser(String firstName, String lastName, String username, 
                              String email, String password, boolean newsletterSubscribed) {
        
        // Check if user already exists
        if (userDAO.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        if (userDAO.usernameExists(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        // Hash password
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        // Create user
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .passwordHash(hashedPassword)
                .newsletterSubscribed(newsletterSubscribed)
                .build();
        
        // Assign default USER role
        Role userRole = roleDAO.getOrCreateRole("USER", "Regular user role");
        user.addUserRole(userRole);
        
        // Save user
        userDAO.saveUser(user);
        
        // Generate JWT token
        return JwtUtil.generateToken(user.getEmail(), user.getUserId());
    }
    
    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userDAO.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        
        User user = userOpt.get();
        
        if (!user.canLogin()) {
            throw new RuntimeException("Account is locked or inactive");
        }
        
        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            user.incrementLoginAttempts();
            userDAO.updateUser(user);
            throw new RuntimeException("Invalid credentials");
        }
        
        // Reset login attempts and update last login
        user.resetLoginAttempts();
        user.setLastLoginAt(LocalDateTime.now());
        userDAO.updateUser(user);
        
        // Generate JWT token
        return JwtUtil.generateToken(user.getEmail(), user.getUserId());
    }
}

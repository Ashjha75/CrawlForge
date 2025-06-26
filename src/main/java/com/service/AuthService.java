package com.service;
package com.dao.userDao;
import com.crawlforge.dao.UserDAO;
import com.crawlforge.dao.RoleDAO;
import com.crawlforge.model.User;
import com.crawlforge.model.Role;
import com.crawlforge.util.PasswordUtil;
import com.crawlforge.util.JwtUtil;

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
                .termsAcceptedAt(LocalDateTime.now())
                .privacyAcceptedAt(LocalDateTime.now())
                .build();
        
        // Assign default USER role
        Role userRole = roleDAO.getOrCreateRole("USER", "Regular user role");
        user.addRole(userRole);
        
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

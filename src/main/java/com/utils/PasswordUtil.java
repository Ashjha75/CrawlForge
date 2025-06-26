package com.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    private static final int ROUNDS = 12;
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(ROUNDS));
    }
    
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

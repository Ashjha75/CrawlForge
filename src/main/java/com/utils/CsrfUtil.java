package com.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class CsrfUtil {
    public static String generateCSRFToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
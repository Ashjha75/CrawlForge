package com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT Utility class for WebCrawlerAnalytics
 * Handles JWT token generation, verification, and extraction
 */
public class JwtUtil {

    // Secret key for JWT signing (In production, use environment variable)
    private static final String SECRET_KEY = "CrawlForge-WebCrawlerAnalytics-JWT-Secret-Key-2025-Secure";

    // Token expiration time (24 hours)
    private static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(24);

    // Token issuer
    private static final String ISSUER = "CrawlForge";

    // Algorithm for signing
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Generate JWT token for authenticated user
     *
     * @param email  User's email address
     * @param userId User's ID
     * @return JWT token string
     */
    public static String generateToken(String email, Long userId) {
        try {
            Date now = new Date();
            Date expiresAt = new Date(now.getTime() + EXPIRATION_TIME);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(email)
                    .withClaim("userId", userId)
                    .withClaim("email", email)
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error creating JWT token", e);
        }
    }

    /**
     * Generate JWT token with additional user information
     *
     * @param email    User's email
     * @param userId   User's ID
     * @param username User's username
     * @param roles    User's roles (comma-separated)
     * @return JWT token string
     */
    public static String generateTokenWithRoles(String email, Long userId, String username, String roles) {
        try {
            Date now = new Date();
            Date expiresAt = new Date(now.getTime() + EXPIRATION_TIME);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(email)
                    .withClaim("userId", userId)
                    .withClaim("email", email)
                    .withClaim("username", username)
                    .withClaim("roles", roles)
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error creating JWT token with roles", e);
        }
    }

    /**
     * Verify and decode JWT token
     *
     * @param token JWT token string
     * @return DecodedJWT object
     * @throws RuntimeException if token is invalid
     */
    public static DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            return verifier.verify(token);

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

    /**
     * Extract user email from JWT token
     *
     * @param token JWT token string
     * @return User's email address
     */
    public static String getEmailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting email from token", e);
        }
    }

    /**
     * Extract user ID from JWT token
     *
     * @param token JWT token string
     * @return User's ID
     */
    public static Long getUserIdFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getClaim("userId").asLong();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting user ID from token", e);
        }
    }

    /**
     * Extract username from JWT token
     *
     * @param token JWT token string
     * @return User's username
     */
    public static String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getClaim("username").asString();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting username from token", e);
        }
    }

    /**
     * Extract user roles from JWT token
     *
     * @param token JWT token string
     * @return User's roles (comma-separated string)
     */
    public static String getRolesFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getClaim("roles").asString();
        } catch (Exception e) {
            return "USER"; // Default role if not found
        }
    }

    /**
     * Check if token is expired
     *
     * @param token JWT token string
     * @return true if token is expired, false otherwise
     */
    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return true; // Consider invalid tokens as expired
        }
    }

    /**
     * Check if user has specific role
     *
     * @param token JWT token string
     * @param role  Role to check
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(String token, String role) {
        try {
            String roles = getRolesFromToken(token);
            return roles != null && roles.contains(role);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if user is admin
     *
     * @param token JWT token string
     * @return true if user is admin, false otherwise
     */
    public static boolean isAdmin(String token) {
        return hasRole(token, "ADMIN");
    }

    /**
     * Get token expiration time in milliseconds
     *
     * @param token JWT token string
     * @return Expiration time as timestamp
     */
    public static long getExpirationTime(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt().getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get time until token expires in minutes
     *
     * @param token JWT token string
     * @return Minutes until expiration
     */
    public static long getMinutesUntilExpiration(String token) {
        try {
            long expirationTime = getExpirationTime(token);
            long currentTime = System.currentTimeMillis();
            long timeDiff = expirationTime - currentTime;
            return TimeUnit.MILLISECONDS.toMinutes(timeDiff);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Refresh token (generate new token with same claims but extended expiration)
     *
     * @param token Current JWT token
     * @return New JWT token with extended expiration
     */
    public static String refreshToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            String email = decodedJWT.getSubject();
            Long userId = decodedJWT.getClaim("userId").asLong();
            String username = decodedJWT.getClaim("username").asString();
            String roles = decodedJWT.getClaim("roles").asString();

            if (username != null && roles != null) {
                return generateTokenWithRoles(email, userId, username, roles);
            } else {
                return generateToken(email, userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error refreshing token", e);
        }
    }

    /**
     * Extract all user information from token
     *
     * @param token JWT token string
     * @return UserInfo object with all claims
     */
    public static UserInfo getUserInfoFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);

            return new UserInfo(
                    decodedJWT.getClaim("userId").asLong(),
                    decodedJWT.getSubject(),
                    decodedJWT.getClaim("username").asString(),
                    decodedJWT.getClaim("roles").asString(),
                    decodedJWT.getIssuedAt(),
                    decodedJWT.getExpiresAt()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error extracting user info from token", e);
        }
    }

    /**
     * Inner class to hold user information from JWT token
     */
    public static class UserInfo {
        private final Long userId;
        private final String email;
        private final String username;
        private final String roles;
        private final Date issuedAt;
        private final Date expiresAt;

        public UserInfo(Long userId, String email, String username, String roles, Date issuedAt, Date expiresAt) {
            this.userId = userId;
            this.email = email;
            this.username = username;
            this.roles = roles;
            this.issuedAt = issuedAt;
            this.expiresAt = expiresAt;
        }

        // Getters
        public Long getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getRoles() {
            return roles;
        }

        public Date getIssuedAt() {
            return issuedAt;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }

        public boolean hasRole(String role) {
            return roles != null && roles.contains(role);
        }

        public boolean isAdmin() {
            return hasRole("ADMIN");
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "userId=" + userId +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", roles='" + roles + '\'' +
                    ", issuedAt=" + issuedAt +
                    ", expiresAt=" + expiresAt +
                    '}';
        }
    }
}

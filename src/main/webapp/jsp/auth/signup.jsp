<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/signup.css">
<!-- Animated Background -->
<div class="signup-background"></div>

<!-- Main Container -->
<div class="signup-container">
    <div class="signup-card" style="margin-top:7rem">
        <!-- Header -->
        <div class="signup-header">
            <div class="signup-logo">
                <i class="bi bi-robot"></i>
                <h1 class="signup-brand">CrawlForge</h1>
            </div>
            <p class="signup-subtitle">Create your account to start web scraping</p>
        </div>

        <!-- Error/Success Messages will be inserted here by JavaScript -->

        <!-- Sign Up Form -->
        <form class="signup-form" id="signupForm" method="POST" action="${pageContext.request.contextPath}/signup" enctype="multipart/form-data">
            <!-- Name Fields -->
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="firstName">
                        <i class="bi bi-person"></i>
                        First Name
                    </label>
                    <input type="text"
                           class="form-input"
                           id="firstName"
                           name="firstName"
                           placeholder="Enter your first name"
                           required
                           autocomplete="given-name">
                </div>
                <div class="form-group">
                    <label class="form-label" for="lastName">
                        <i class="bi bi-person"></i>
                        Last Name
                    </label>
                    <input type="text"
                           class="form-input"
                           id="lastName"
                           name="lastName"
                           placeholder="Enter your last name"
                           required
                           autocomplete="family-name">
                </div>
            </div>

            <!-- Username Field -->
            <div class="form-group">
                <label class="form-label" for="username">
                    <i class="bi bi-at"></i>
                    Username
                </label>
                <input type="text"
                       class="form-input"
                       id="username"
                       name="username"
                       placeholder="Choose a unique username"
                       required
                       autocomplete="username">
            </div>

            <!-- Email Field -->
            <div class="form-group">
                <label class="form-label" for="email">
                    <i class="bi bi-envelope"></i>
                    Email Address
                </label>
                <input type="email"
                       class="form-input"
                       id="email"
                       name="email"
                       placeholder="Enter your email address"
                       required
                       autocomplete="email">
            </div>
<%--            https://api.dicebear.com/6.x/pixel-art/svg?seed=$jha&background=%23000000&radius=50&colorful=1--%>
            <!-- Profile Picture Upload -->
            <div class="form-group">
                <label class="form-label" for="profilePicture">
                    <i class="bi bi-image"></i>
                    Profile Picture (optional)
                </label>
                <input type="file"
                       class="form-input"
                       id="profilePicture"
                       name="profilePicture"
                       accept="image/*">
                <div id="profilePicturePreview" class="profile-picture-preview"></div>
            </div>

            <!-- Password Field with Eye Button -->
            <div class="form-group">
                <label class="form-label" for="password">
                    <i class="bi bi-lock"></i>
                    Password
                </label>
                <div class="password-input-wrapper">
                    <input type="password"
                           class="form-input password-input"
                           id="password"
                           name="password"
                           placeholder="Create a strong password"
                           required
                           autocomplete="new-password">
                    <button type="button" class="password-toggle-btn" onclick="togglePasswordVisibility('password')">
                        <i class="bi bi-eye" id="password-eye"></i>
                    </button>
                </div>
                <div class="password-strength" id="passwordStrength">
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                </div>
                <div class="strength-text" id="strengthText">Password strength: Weak</div>
            </div>

            <!-- Confirm Password Field with Eye Button -->
            <div class="form-group">
                <label class="form-label" for="confirmPassword">
                    <i class="bi bi-lock-fill"></i>
                    Confirm Password
                </label>
                <div class="password-input-wrapper">
                    <input type="password"
                           class="form-input password-input"
                           id="confirmPassword"
                           name="confirmPassword"
                           placeholder="Confirm your password"
                           required
                           autocomplete="new-password">
                    <button type="button" class="password-toggle-btn"
                            onclick="togglePasswordVisibility('confirmPassword')">
                        <i class="bi bi-eye" id="confirmPassword-eye"></i>
                    </button>
                </div>
            </div>

            <!-- Terms and Conditions -->
            <div class="terms-group">
                <input type="checkbox" class="terms-checkbox" id="agreeTerms" name="agreeTerms" required>
                <div class="terms-text">
                    I agree to the <a href="${pageContext.request.contextPath}/terms" target="_blank">Terms of
                    Service</a>
                    and <a href="${pageContext.request.contextPath}/privacy" target="_blank">Privacy Policy</a>
                </div>
            </div>

            <!-- Newsletter Subscription -->
            <div class="terms-group">
                <input type="checkbox" class="terms-checkbox" id="newsletter" name="newsletter">
                <div class="terms-text">
                    Subscribe to our newsletter for updates and tips
                </div>
            </div>

            <!-- Sign Up Button -->
            <button type="submit" class="signup-btn" id="signupBtn">
                <div class="btn-spinner" id="btnSpinner"></div>
                <span class="btn-text">Create Account</span>
            </button>
        </form>

        <!-- Divider -->
        <div class="signup-divider">
            <span>or</span>
        </div>

        <!-- Sign In Link -->
        <div class="signin-link">
            Already have an account?
            <a href="${pageContext.request.contextPath}/signin">Sign in here</a>
        </div>
    </div>
</div>

<script src="/js/signup.js"></script>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../../css/signup.css"%>

<!-- Animated Background -->
<div class="signup-background"></div>

<!-- Main Container -->
<div class="signup-container">
    <div class="signup-card">
        <!-- Header -->
        <div class="signup-header">
            <div class="signup-logo">
                <i class="bi bi-robot"></i>
                <h1 class="signup-brand">CrawlForge</h1>
            </div>
            <p class="signup-subtitle">Create your account to start web scraping</p>
        </div>

        <!-- Error/Success Messages -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <i class="bi bi-exclamation-triangle"></i>
                <span>${error}</span>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="success-message">
                <i class="bi bi-check-circle"></i>
                <span>${success}</span>
            </div>
        </c:if>

        <!-- Sign Up Form -->
        <form class="signup-form" id="signupForm" method="POST" action="${pageContext.request.contextPath}/register">
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

            <!-- Password Field -->
            <div class="form-group">
                <label class="form-label" for="password">
                    <i class="bi bi-lock"></i>
                    Password
                </label>
                <input type="password"
                       class="form-input"
                       id="password"
                       name="password"
                       placeholder="Create a strong password"
                       required
                       autocomplete="new-password">
                <div class="password-strength" id="passwordStrength">
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                    <div class="strength-bar"></div>
                </div>
                <div class="strength-text" id="strengthText">Password strength: Weak</div>
            </div>

            <!-- Confirm Password Field -->
            <div class="form-group">
                <label class="form-label" for="confirmPassword">
                    <i class="bi bi-lock-fill"></i>
                    Confirm Password
                </label>
                <input type="password"
                       class="form-input"
                       id="confirmPassword"
                       name="confirmPassword"
                       placeholder="Confirm your password"
                       required
                       autocomplete="new-password">
            </div>

            <!-- Terms and Conditions -->
            <div class="terms-group">
                <input type="checkbox" class="terms-checkbox" id="agreeTerms" name="agreeTerms" required>
                <div class="terms-text">
                    I agree to the <a href="${pageContext.request.contextPath}/terms" target="_blank">Terms of Service</a>
                    and <a href="${pageContext.request.contextPath}/privacy" target="_blank">Privacy Policy</a>
                </div>
            </div>

            <!-- Sign Up Button -->
            <button type="submit" class="signup-btn" id="signupBtn">
                <div class="btn-spinner" id="btnSpinner"></div>
                <span class="btn-text">Create Account</span>
            </button>

            <!-- CSRF Token -->
            <input type="hidden" name="csrfToken" value="${csrfToken}">
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

<!-- JavaScript -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('signupForm');
        const submitBtn = document.getElementById('signupBtn');
        const spinner = document.getElementById('btnSpinner');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirmPassword');

        // Password strength checker
        passwordInput.addEventListener('input', function() {
            checkPasswordStrength(this.value);
        });

        // Form submission
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            // Show loading state
            submitBtn.disabled = true;
            spinner.style.display = 'inline-block';
            submitBtn.querySelector('.btn-text').textContent = 'Creating Account...';

            // Validate form
            if (validateForm()) {
                // Submit form
                this.submit();
            } else {
                resetButton();
            }
        });

        function validateForm() {
            const firstName = document.getElementById('firstName').value.trim();
            const lastName = document.getElementById('lastName').value.trim();
            const username = document.getElementById('username').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = passwordInput.value;
            const confirmPassword = confirmPasswordInput.value;
            const agreeTerms = document.getElementById('agreeTerms').checked;

            if (!firstName || !lastName) {
                showError('Please enter your full name');
                return false;
            }

            if (!username || username.length < 3) {
                showError('Username must be at least 3 characters long');
                return false;
            }

            if (!email || !isValidEmail(email)) {
                showError('Please enter a valid email address');
                return false;
            }

            if (!password || password.length < 8) {
                showError('Password must be at least 8 characters long');
                return false;
            }

            if (password !== confirmPassword) {
                showError('Passwords do not match');
                return false;
            }

            if (!agreeTerms) {
                showError('Please agree to the Terms of Service and Privacy Policy');
                return false;
            }

            return true;
        }

        function checkPasswordStrength(password) {
            const strengthBars = document.querySelectorAll('.strength-bar');
            const strengthText = document.getElementById('strengthText');

            let strength = 0;
            let strengthLabel = 'Weak';

            if (password.length >= 8) strength++;
            if (/[a-z]/.test(password)) strength++;
            if (/[A-Z]/.test(password)) strength++;
            if (/[0-9]/.test(password)) strength++;
            if (/[^A-Za-z0-9]/.test(password)) strength++;

            // Reset all bars
            strengthBars.forEach(bar => {
                bar.className = 'strength-bar';
            });

            // Set strength
            if (strength >= 1) {
                strengthBars[0].classList.add('weak');
                strengthLabel = 'Weak';
            }
            if (strength >= 2) {
                strengthBars[1].classList.add('weak');
            }
            if (strength >= 3) {
                strengthBars[0].className = 'strength-bar medium';
                strengthBars[1].classList.add('medium');
                strengthBars[2].classList.add('medium');
                strengthLabel = 'Medium';
            }
            if (strength >= 4) {
                strengthBars.forEach((bar, index) => {
                    if (index < 3) bar.className = 'strength-bar strong';
                });
                strengthBars[3].classList.add('strong');
                strengthLabel = 'Strong';
            }
            if (strength >= 5) {
                strengthBars.forEach(bar => {
                    bar.className = 'strength-bar very-strong';
                });
                strengthLabel = 'Very Strong';
            }

            strengthText.textContent = `Password strength: ${strengthLabel}`;
        }

        function isValidEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(email);
        }

        function resetButton() {
            submitBtn.disabled = false;
            spinner.style.display = 'none';
            submitBtn.querySelector('.btn-text').textContent = 'Create Account';
        }

        function showError(message) {
            removeMessages();
            const errorDiv = document.createElement('div');
            errorDiv.className = 'error-message';
            errorDiv.innerHTML = `
                <i class="bi bi-exclamation-triangle"></i>
                <span>${message}</span>
            `;
            form.insertBefore(errorDiv, form.firstChild);
        }

        function removeMessages() {
            const existingMessages = form.querySelectorAll('.error-message, .success-message');
            existingMessages.forEach(msg => msg.remove());
        }

        // Auto-focus first input
        document.getElementById('firstName').focus();
    });
</script>

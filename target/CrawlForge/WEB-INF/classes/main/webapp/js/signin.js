document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('signinForm');
    const submitBtn = document.getElementById('signinBtn');
    const spinner = document.getElementById('btnSpinner');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    // Form submission - send as JSON (matching your signup pattern)
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // Show loading state
        submitBtn.disabled = true;
        spinner.style.display = 'inline-block';
        submitBtn.querySelector('.btn-text').textContent = 'Signing In...';

        // Validate form
        if (validateForm()) {
            // Collect form data as JSON (FIXED - was FormData before)
            const data = {
                email: document.getElementById('email').value.trim(),
                password: document.getElementById('password').value,
                rememberMe: document.getElementById('rememberMe').checked
            };

            // Send JSON data (FIXED - added proper headers)
            fetch(form.action, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Login Response:', data);
                if (data.success) {
                    // Use toast instead of inline messages
                    showSuccess(data.message);
                    setTimeout(() => {
                        window.location.href = data.redirectUrl || '/dashboard';
                    }, 1500);
                } else {
                    // Use toast for errors
                    showError(data.message);
                    resetButton();
                }
            })
            .catch(error => {
                console.error('Login Error:', error);
                // Use toast for network errors
                showError('Login failed. Please check your connection and try again.');
                resetButton();
            });
        } else {
            resetButton();
        }
    });

    // Auto-focus email input
    emailInput.focus();
});

// Password visibility toggle function
function togglePasswordVisibility(inputId) {
    const passwordInput = document.getElementById(inputId);
    const eyeIcon = document.getElementById(inputId + '-eye');
    const toggleBtn = eyeIcon.closest('.password-toggle-btn');

    if (passwordInput.type === 'password') {
        // Show password
        passwordInput.type = 'text';
        eyeIcon.classList.remove('bi-eye');
        eyeIcon.classList.add('bi-eye-slash');
        toggleBtn.classList.add('active');
        toggleBtn.setAttribute('title', 'Hide password');
    } else {
        // Hide password
        passwordInput.type = 'password';
        eyeIcon.classList.remove('bi-eye-slash');
        eyeIcon.classList.add('bi-eye');
        toggleBtn.classList.remove('active');
        toggleBtn.setAttribute('title', 'Show password');
    }
}

function validateForm() {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if (!email) {
        showError('Please enter your email address');
        return false;
    }

    if (!isValidEmail(email)) {
        showError('Please enter a valid email address');
        return false;
    }

    if (!password) {
        showError('Please enter your password');
        return false;
    }

    return true;
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function resetButton() {
    const submitBtn = document.getElementById('signinBtn');
    const spinner = document.getElementById('btnSpinner');

    submitBtn.disabled = false;
    spinner.style.display = 'none';
    submitBtn.querySelector('.btn-text').textContent = 'Sign In';
}

// REMOVED - These functions will be handled by global toast system
// function showError(message) { ... }
// function showSuccess(message) { ... }
// function removeMessages() { ... }

// Toast functions are now global - defined in toast.js
// showError() and showSuccess() are available globally

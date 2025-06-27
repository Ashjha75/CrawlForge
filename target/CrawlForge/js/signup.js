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

        // Form submission - send as JSON
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            // Show loading state
            submitBtn.disabled = true;
            spinner.style.display = 'inline-block';
            submitBtn.querySelector('.btn-text').textContent = 'Creating Account...';

            // Validate form
            if (validateForm()) {
                // Collect form data as JSON
                const data = {
                    firstName: document.getElementById('firstName').value.trim(),
                    lastName: document.getElementById('lastName').value.trim(),
                    username: document.getElementById('username').value.trim(),
                    email: document.getElementById('email').value.trim(),
                    password: document.getElementById('password').value,
                    confirmPassword: document.getElementById('confirmPassword').value,
                    agreeTerms: document.getElementById('agreeTerms').checked,
                    newsletter: document.getElementById('newsletter').checked
                };

                fetch(form.action, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showSuccess(data.message);
                        setTimeout(() => {
                            window.location.href = data.redirectUrl || '/signin';
                        }, 1500);
                    } else {
                        showError(data.message);
                        resetButton();
                    }
                })
                .catch(error => {
                    console.error('Registration Error:', error);
                    showError('Registration failed. Please try again.');
                    resetButton();
                });
            } else {
                resetButton();
            }
        });

        // Auto-focus first input
        document.getElementById('firstName').focus();
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
        const firstName = document.getElementById('firstName').value.trim();
        const lastName = document.getElementById('lastName').value.trim();
        const username = document.getElementById('username').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
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
        const submitBtn = document.getElementById('signupBtn');
        const spinner = document.getElementById('btnSpinner');

        submitBtn.disabled = false;
        spinner.style.display = 'none';
        submitBtn.querySelector('.btn-text').textContent = 'Create Account';
    }

    function showError(message) {
        removeMessages();
        const form = document.getElementById('signupForm');
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.innerHTML = `
            <i class="bi bi-exclamation-triangle"></i>
            <span>${message}</span>
        `;
        form.insertBefore(errorDiv, form.firstChild);

        // Auto-remove after 5 seconds
        setTimeout(() => errorDiv.remove(), 5000);
    }

    function showSuccess(message) {
        removeMessages();
        const form = document.getElementById('signupForm');
        const successDiv = document.createElement('div');
        successDiv.className = 'success-message';
        successDiv.innerHTML = `
            <i class="bi bi-check-circle"></i>
            <span>${message}</span>
        `;
        form.insertBefore(successDiv, form.firstChild);
    }

    function removeMessages() {
        const form = document.getElementById('signupForm');
        const existingMessages = form.querySelectorAll('.error-message, .success-message');
        existingMessages.forEach(msg => msg.remove());
    }
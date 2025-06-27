document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('signinForm');
    const submitBtn = document.getElementById('signinBtn');
    const spinner = document.getElementById('btnSpinner');

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        submitBtn.disabled = true;
        spinner.style.display = 'inline-block';
        submitBtn.querySelector('.btn-text').textContent = 'Signing In...';

        const data = {
            email: document.getElementById('username').value.trim(),
            password: document.getElementById('password').value,
            rememberMe: document.querySelector('input[name="rememberMe"]').checked,
            csrfToken: document.querySelector('[name="csrfToken"]').value
        };

        fetch(form.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(res => res.json())
        .then(response => {
            if (response.success) {
                showSuccess(response.message || 'Sign in successful');
                setTimeout(() => {
                    window.location.href = response.redirectUrl || form.dataset.dashboardUrl;
                }, 1500);
            } else {
                showError(response.message || 'Sign in failed');
            }
            resetButton();
        })
        .catch(err => {
            console.error(err);
            showError('Sign in failed');
            resetButton();
        });
    });

    function resetButton() {
        submitBtn.disabled = false;
        spinner.style.display = 'none';
        submitBtn.querySelector('.btn-text').textContent = 'Sign In';
    }

    function showError(msg) {
        removeMessages();
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.innerHTML = `<span>${msg}</span>`;
        form.insertBefore(errorDiv, form.firstChild);
    }

    function showSuccess(msg) {
        removeMessages();
        const successDiv = document.createElement('div');
        successDiv.className = 'success-message';
        successDiv.innerHTML = `<span>${msg}</span>`;
        form.insertBefore(successDiv, form.firstChild);
    }

    function removeMessages() {
        const existingMessages = form.querySelectorAll('.error-message, .success-message');
        existingMessages.forEach(msg => msg.remove());
    }
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

// REMOVED - These functions are now handled by global toast system
// function showError(message) { ... }
// function showSuccess(message) { ... }
// function removeMessages() { ... }

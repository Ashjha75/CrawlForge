<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Enhanced Header with Authentication -->
<nav class="navbar navbar-expand-lg navbar-custom" id="mainNavbar">
    <div class="container-fluid px-4">
        <!-- Brand on Left -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-robot brand-icon logo-animate"></i>
            CrawlForge
        </a>

        <!-- Mobile Toggle -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navigation on Right -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <!-- Empty div to push everything to the right -->
            <div class="navbar-nav ms-auto d-flex align-items-center">
                <!-- Main Navigation Links -->
                <a class="nav-link" href="${pageContext.request.contextPath}/">
                    <i class="bi bi-house-door"></i>Home
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                    <i class="bi bi-speedometer2"></i>Dashboard
                </a>
                <div class="nav-item position-relative">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reports">
                        <i class="bi bi-bar-chart"></i>Reports
                        <span class="notification-badge">3</span>
                    </a>
                </div>

                <!-- Divider -->
                <div class="nav-divider"></div>

                <!-- Authentication Section -->
                <!-- Sign In Button (Show when not logged in) -->
                <div class="auth-signin" id="authSignIn">
                    <a class="nav-link signin-btn" href="${pageContext.request.contextPath}/signin">
                        <i class="bi bi-box-arrow-in-right"></i>Sign In
                    </a>
                </div>

                <!-- Account Dropdown (Show when logged in) -->
                <div class="dropdown auth-account" id="authAccount" style="display: none;">
                    <a class="nav-link dropdown-toggle account-btn" href="#" id="accountDropdown" role="button" data-bs-toggle="dropdown">
                        <div class="user-avatar">
                            <i class="bi bi-person-circle"></i>
                        </div>
                        <span class="user-name">John Doe</span>
                    </a>
                    <ul class="dropdown-menu account-dropdown">
                        <li class="dropdown-header">
                            <div class="user-info">
                                <div class="user-avatar-large">
                                    <i class="bi bi-person-circle"></i>
                                </div>
                                <div class="user-details">
                                    <div class="user-name-large">John Doe</div>
                                    <div class="user-email">john.doe@example.com</div>
                                </div>
                            </div>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                                <i class="bi bi-person"></i>Profile
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/settings">
                                <i class="bi bi-gear"></i>Settings
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/billing">
                                <i class="bi bi-credit-card"></i>Billing
                            </a>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item logout-btn" href="#" onclick="handleLogout()">
                                <i class="bi bi-box-arrow-right"></i>Logout
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<script>
    // Header scroll effect and authentication handling
    document.addEventListener('DOMContentLoaded', function() {
        const navbar = document.getElementById('mainNavbar');

        // Scroll effect
        window.addEventListener('scroll', function() {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // Set active nav link
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.nav-link');

        navLinks.forEach(link => {
            const href = link.getAttribute('href');
            if (href && currentPath.includes(href.split('/').pop())) {
                link.classList.add('active');
            }
        });

        // Authentication state management (placeholder for JWT implementation)
        checkAuthState();
    });

    // Check authentication state (placeholder for JWT)
    function checkAuthState() {
        // TODO: Replace with actual JWT validation
        const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

        const signInElement = document.getElementById('authSignIn');
        const accountElement = document.getElementById('authAccount');

        if (isLoggedIn) {
            signInElement.style.display = 'none';
            accountElement.style.display = 'block';

            // TODO: Load actual user data from JWT
            loadUserData();
        } else {
            signInElement.style.display = 'block';
            accountElement.style.display = 'none';
        }
    }

    // Load user data (placeholder for JWT implementation)
    function loadUserData() {
        // TODO: Extract user data from JWT token
        const userData = {
            name: localStorage.getItem('userName') || 'John Doe',
            email: localStorage.getItem('userEmail') || 'john.doe@example.com'
        };

        document.querySelector('.user-name').textContent = userData.name;
        document.querySelector('.user-name-large').textContent = userData.name;
        document.querySelector('.user-email').textContent = userData.email;
    }

    // Handle logout
    function handleLogout() {
        // TODO: Implement proper JWT logout
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('userName');
        localStorage.removeItem('userEmail');
        localStorage.removeItem('authToken');

        // Redirect to home page
        window.location.href = '${pageContext.request.contextPath}/';
}

// Temporary login function for testing (remove when JWT is implemented)
function tempLogin() {
    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('userName', 'John Doe');
    localStorage.setItem('userEmail', 'john.doe@example.com');
    checkAuthState();
}
</script>

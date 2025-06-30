<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-custom" id="mainNavbar">
    <div class="container-fluid px-4">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-robot brand-icon logo-animate"></i>
            CrawlForge
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="navbar-nav ms-auto d-flex align-items-center">
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
                <a class="nav-link" href="https://docs-crawlforge-onrender.gitbook.io/crawlforge-docs" target="_blank">
                    <i class="bi bi-book"></i>Docs
                    <i class="bi bi-box-arrow-up-right ms-1" style="font-size: 0.8em;"></i>
                </a>
                <div class="nav-divider"></div>

                <!-- Auth Account Dropdown -->
                <div class="dropdown auth-account" id="authAccount" style="display:none;">
                    <a class="nav-link account-btn" href="#" id="accountDropdown"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle user-avatar"></i>
                        <span class="user-name">User</span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end account-dropdown" aria-labelledby="accountDropdown">
                        <li class="dropdown-header">
                            <div class="user-info">
                                <div class="user-avatar-large">
                                    <i class="bi bi-person-circle"></i>
                                </div>
                                <div class="user-details">
                                    <div class="user-name-large"></div>
                                    <div class="user-email"></div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <a class="dropdown-item logout-btn" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </div>

                <!-- Sign In Button -->
                <div class="auth-signin" id="authSignIn" style="display:none;">
                    <a class="nav-link signin-btn" href="${pageContext.request.contextPath}/signin">
                        <i class="bi bi-box-arrow-in-right"></i>Sign In
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Navbar scroll effect
        const navbar = document.getElementById('mainNavbar');
        window.addEventListener('scroll', function() {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // Active link logic (only one active at a time) - exclude external links
        const contextPath = '${pageContext.request.contextPath}';
    const currentPath = window.location.pathname.replace(contextPath, '') || '/';
    const navLinks = document.querySelectorAll('.nav-link:not([target="_blank"])');
    navLinks.forEach(link => {
        let href = link.getAttribute('href');
        if (href && !href.startsWith('http')) {
            href = href.replace(contextPath, '') || '/';
            if (href === currentPath) {
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        }
    });

    // User check logic
    fetch(contextPath + '/user-check', {
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.status === 401) {
            document.getElementById('authAccount').style.display = 'none';
            document.getElementById('authSignIn').style.display = 'block';
            return;
        }
        return response.json();
    })
    .then(data => {
        if (data && data.isLoggedIn) {
            document.getElementById('authAccount').style.display = 'block';
            document.getElementById('authSignIn').style.display = 'none';

            const userName = data.userName || 'User';
            const userEmail = data.userEmail || '';

            document.querySelector('.user-name').textContent = userName;
            document.querySelector('.user-name-large').textContent = userName;
            document.querySelector('.user-email').textContent = userEmail;
        } else {
            document.getElementById('authAccount').style.display = 'none';
            document.getElementById('authSignIn').style.display = 'block';
        }
    })
    .catch(() => {
        document.getElementById('authAccount').style.display = 'none';
        document.getElementById('authSignIn').style.display = 'block';
    });
});
</script>

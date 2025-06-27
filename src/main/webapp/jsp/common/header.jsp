<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Enhanced Header with Authentication -->
<style>
    /* Custom Dropdown Styles */
    .auth-account .dropdown-menu {
        min-width: 260px;
        padding: 1.2rem 1.2rem 1rem 1.2rem;
        border-radius: 14px;
        box-shadow: 0 8px 32px rgba(60, 60, 90, 0.18);
        border: none;
        right: 0;
        left: auto;
        transform: translateY(10px);
        transition: opacity 0.2s, transform 0.2s;
    }

    .auth-account .dropdown-header {
        padding-bottom: 0.7rem;
        border-bottom: 1px solid #e9ecef;
        margin-bottom: 0.7rem;
        text-align: center;
    }

    .user-avatar-large {
        font-size: 2.8rem;
        color: #6c63ff;
        margin-bottom: 0.3rem;
    }

    .user-name-large {
        font-weight: 600;
        font-size: 1.1rem;
        margin-bottom: 0.1rem;
        color: #22223b;
    }

    .user-email {
        font-size: 0.97rem;
        color: #6c757d;
        word-break: break-all;
    }

    .dropdown-item.logout-btn {
        color: #e63946;
        font-weight: 500;
        text-align: center;
        border-radius: 8px;
        margin-top: 0.5rem;
        transition: background 0.15s;
    }

    .dropdown-item.logout-btn:hover {
        background: #f8d7da;
        color: #b71c1c;
    }

    /* Remove default arrow */
    .dropdown-toggle::after {
        display: none;
    }

    /* Smooth dropdown animation */
    .dropdown-menu {
        opacity: 0;
        pointer-events: none;
        transform: translateY(20px);
        transition: opacity 0.2s, transform 0.2s;
    }

    .dropdown.show .dropdown-menu {
        opacity: 1;
        pointer-events: auto;
        transform: translateY(0);
    }
</style>
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
                <div class="nav-divider"></div>
                <!-- Authentication Section -->
                <c:choose>
                    <c:when test="${isLoggedIn}">
                        <!-- Account Dropdown (Show when logged in) -->
                        <div class="dropdown auth-account" id="authAccount">
                            <a class="nav-link dropdown-toggle account-btn" href="#" id="accountDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <div class="user-avatar">
                                    <i class="bi bi-person-circle" style="font-size:1.7rem;color:#6c63ff;"></i>
                                </div>
                                <span class="user-name" style="font-weight:500;">${userName}</span>
                            </a>
                            <ul class="dropdown-menu account-dropdown" aria-labelledby="accountDropdown">
                                <li class="dropdown-header">
                                    <div class="user-avatar-large">
                                        <i class="bi bi-person-circle"></i>
                                    </div>
                                    <div class="user-name-large">${userName}</div>
                                    <div class="user-email">${userEmail}</div>
                                </li>
                                <li>
                                    <a class="dropdown-item logout-btn"
                                       href="${pageContext.request.contextPath}/logout">
                                        <i class="bi bi-box-arrow-right"></i> Logout
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- Sign In Button (Show when not logged in) -->
                        <div class="auth-signin" id="authSignIn">
                            <a class="nav-link signin-btn" href="${pageContext.request.contextPath}/signin">
                                <i class="bi bi-box-arrow-in-right"></i>Sign In
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
<script>
    // Header scroll effect and active nav link
    document.addEventListener('DOMContentLoaded', function() {
        const navbar = document.getElementById('mainNavbar');
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
    });
</script>
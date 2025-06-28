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
                            <div class="nav-divider"></div>
                            <!-- Auth sections, hidden by default -->
                            <div class="dropdown auth-account" id="authAccount" style="display:none;">
                                <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" id="accountDropdown"
                                   role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-circle account-animated-icon"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end account-dropdown" aria-labelledby="accountDropdown">
                                    <li>
                                        <div class="user-avatar-large">
                                            <i class="bi bi-person-circle"></i>
                                        </div>
                                        <div class="user-name-large"></div>
                                        <div class="user-email"></div>
                                    </li>
                                    <li>
                                        <a class="dropdown-item logout-btn"
                                           href="${pageContext.request.contextPath}/logout">
                                            <i class="bi bi-box-arrow-right"></i> Logout
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="auth-signin" id="authSignIn" style="display:none;">
                                <a class="nav-link signin-btn" href="${pageContext.request.contextPath}/signin">
                                    <i class="bi bi-box-arrow-in-right"></i>Sign In
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <style>
                .account-animated-icon {
                    font-size: 1.7rem;
                    color: #10a37f !important;
                    animation: accountPulse 1.5s infinite alternate;
                    filter: drop-shadow(0 0 6px #10a37f55);
                    transition: transform 0.3s;
                }
                .account-animated-icon:hover {
                    transform: scale(1.12) rotate(8deg);
                    filter: drop-shadow(0 0 12px #10a37f99);
                }
                @keyframes accountPulse {
                    0% { filter: drop-shadow(0 0 6px #10a37f55); }
                    100% { filter: drop-shadow(0 0 16px #10a37faa); }
                }
            </style>
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    // Navbar scroll effect and active link
                    const navbar = document.getElementById('mainNavbar');
                    window.addEventListener('scroll', function() {
                        if (window.scrollY > 50) {
                            navbar.classList.add('scrolled');
                        } else {
                            navbar.classList.remove('scrolled');
                        }
                    });
                    const currentPath = window.location.pathname;
                    const navLinks = document.querySelectorAll('.nav-link');
                    navLinks.forEach(link => {
                        const href = link.getAttribute('href');
                        if (href && currentPath.includes(href.split('/').pop())) {
                            link.classList.add('active');
                        }
                    });

                    // User check logic
                    fetch('${pageContext.request.contextPath}/user-check', {
                        credentials: 'same-origin'
                    })
                    .then(response => {
                        if (response.status === 401) {
                            window.location.href = '${pageContext.request.contextPath}/signin';
                            return;
                        }
                        return response.json();
                    })
                    .then(data => {
                        if (data && data.isLoggedIn) {
                            document.getElementById('authAccount').style.display = '';
                            document.getElementById('authSignIn').style.display = 'none';
                            document.querySelectorAll('.user-name-large').forEach(el => el.textContent = data.userName);
                            document.querySelectorAll('.user-email').forEach(el => el.textContent = data.userEmail);
                        } else {
                            document.getElementById('authAccount').style.display = 'none';
                            document.getElementById('authSignIn').style.display = '';
                        }
                    })
                    .catch(() => {
                        window.location.href = '${pageContext.request.contextPath}/signin';
                    });
                });
            </script>
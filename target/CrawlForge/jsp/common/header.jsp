<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <style>
            .auth-account .dropdown-menu {
                min-width: 240px;
                border-radius: 12px;
                box-shadow: 0 8px 32px rgba(60,60,90,0.13);
                border: none;
                padding: 1.1rem 1.2rem 1rem 1.2rem;
                margin-top: 0.7rem;
                animation: fadeInDown 0.18s;
            }
            @keyframes fadeInDown {
                from { opacity: 0; transform: translateY(16px);}
                to { opacity: 1; transform: translateY(0);}
            }
            .user-avatar-large {
                font-size: 2.5rem;
                color: #6c63ff;
                margin-bottom: 0.3rem;
                text-align: center;
            }
            .user-name-large {
                font-weight: 600;
                font-size: 1.08rem;
                margin-bottom: 0.1rem;
                color: #22223b;
                text-align: center;
            }
            .user-email {
                font-size: 0.97rem;
                color: #6c757d;
                word-break: break-all;
                text-align: center;
            }
            .dropdown-item.logout-btn {
                color: #e63946;
                font-weight: 500;
                text-align: center;
                border-radius: 8px;
                margin-top: 0.7rem;
                transition: background 0.15s;
            }
            .dropdown-item.logout-btn:hover {
                background: #f8d7da;
                color: #b71c1c;
            }
            .dropdown-toggle::after {
                display: none;
            }
        </style>
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
                        <c:choose>
                            <c:when test="${isLoggedIn}">
                                <div class="dropdown auth-account" id="authAccount">
                                    <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" id="accountDropdown"
                                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="bi bi-person-circle" style="font-size:1.7rem;color:#6c63ff;"></i>
                                        <span class="user-name" style="font-weight:500;">${userName}</span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end account-dropdown" aria-labelledby="accountDropdown">
                                        <li>
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
            document.addEventListener('DOMContentLoaded', function() {
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
            });
        </script>
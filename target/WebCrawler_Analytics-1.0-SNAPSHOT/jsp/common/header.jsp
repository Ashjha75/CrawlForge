<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">


<!-- Enhanced Animated Navbar with Glass Effect -->
<nav class="navbar navbar-expand-lg navbar-custom " id="mainNavbar">
    <div class="container-fluid px-4">
        <!-- Brand with Glass Effect -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <%--            <i class="bi bi-globe-americas brand-icon"></i>--%>
            <img src="favicon.ico" alt="logo" height="30" width="30" class="logo-animate">
            <span>CrawlForge</span>
        </a>

        <!-- Mobile Toggle Button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navigation Menu -->
        <div class="collapse navbar-collapse" id="navbarNav" style="padding: 2vh 1vw;">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/">
                        <i class="bi bi-house-door"></i>Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                        <i class="bi bi-speedometer2"></i>Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reports">
                        <i class="bi bi-bar-chart"></i>Reports
                        <span class="notification-badge">3</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/analytics">
                        <i class="bi bi-graph-up"></i>Analytics
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/settings">
                        <i class="bi bi-gear"></i>Settings
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- JavaScript for Header Animations -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const navbar = document.getElementById('mainNavbar');
        const navLinks = document.querySelectorAll('.nav-link');

        // Scroll effect for navbar
        window.addEventListener('scroll', function() {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // Active link management
        const currentPath = window.location.pathname;
        navLinks.forEach(link => {
            const linkPath = new URL(link.href).pathname;
            if (linkPath === currentPath || (currentPath === '/' && linkPath.endsWith('/'))) {
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        });

        // Mobile menu close on link click
        navLinks.forEach(link => {
            link.addEventListener('click', function() {
                const navbarCollapse = document.getElementById('navbarNav');
                if (navbarCollapse.classList.contains('show')) {
                    const bsCollapse = new bootstrap.Collapse(navbarCollapse);
                    bsCollapse.hide();
                }
            });
        });

        // Smooth scroll for anchor links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
    });
</script>

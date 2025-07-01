<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- SEO Meta Tags -->
    <title>${pageTitle != null ? pageTitle : 'CrawlForge - Advanced Web Scraping Platform'}</title>
    <link rel="icon" href="/favicon.ico" type="image/x-icon">

    <meta name="description"
          content="CrawlForge is an advanced AI-powered web scraping platform that extracts data from any website with precision, scale, and intelligence.">
    <meta name="keywords"
          content="web scraping, data extraction, web crawler, API scraping, automated data collection, CrawlForge">
    <meta name="author" content="CrawlForge">
    <meta name="robots" content="index, follow">

    <!-- Open Graph Meta Tags -->
    <meta property="og:title"
          content="${pageTitle != null ? pageTitle : 'CrawlForge - Advanced Web Scraping Platform'}">
    <meta property="og:description"
          content="AI-powered web scraping platform that extracts data from any website with precision and scale.">
    <meta property="og:type" content="website">
    <meta property="og:site_name" content="CrawlForge">

    <!-- Theme Colors -->
    <meta name="theme-color" content="#10a37f">
    <meta name="msapplication-navbutton-color" content="#10a37f">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-title" content="CrawlForge">

    <!-- Preconnect for Performance -->
    <link rel="preconnect" href="https://cdn.jsdelivr.net">
    <link rel="preconnect" href="https://cdnjs.cloudflare.com">

    <!-- All Common Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Your Custom CSS Files -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <!-- Page-specific CSS -->
    <c:if test="${pageCssFiles != null}">
        <c:forEach var="cssFile" items="${pageCssFiles}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${cssFile}">
        </c:forEach>
    </c:if>

    <!-- Backward compatibility for single CSS -->
    <c:if test="${pageCss != null}">
        <c:forTokens var="cssFile" items="${pageCss}" delims=",">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${cssFile}">
        </c:forTokens>
    </c:if>


</head>
<body>
<!-- Enhanced Loading Screen -->
<div class="page-loading" id="pageLoading">
    <div class="loader-container">
        <!-- Advanced Spinner -->
        <div class="advanced-spinner">
            <div class="spinner-ring spinner-ring-1"></div>
            <div class="spinner-ring spinner-ring-2"></div>
            <div class="spinner-ring spinner-ring-3"></div>
            <div class="spinner-center">
                <i class="bi bi-robot"></i>
            </div>
        </div>

        <!-- Floating Particles -->
        <div class="loader-particles">
            <div class="particle"></div>
            <div class="particle"></div>
            <div class="particle"></div>
            <div class="particle"></div>
            <div class="particle"></div>
            <div class="particle"></div>
        </div>

        <!-- Loading Text -->
        <div class="loading-text">
            <div class="loading-brand">CrawlForge</div>
            <div class="loading-subtitle">Initializing web scraping platform...</div>

            <!-- Progress Bar -->
            <div class="loading-progress">
                <div class="progress-fill"></div>
            </div>
        </div>
    </div>
</div>

<!-- Include Header -->
<%@ include file="/jsp/common/header.jsp" %>

<!-- Main Content Area -->
<main class="main-content">
    <c:choose>
        <c:when test="${contentPage != null}">
            <jsp:include page="${contentPage}"/>
        </c:when>
        <c:otherwise>
            <%@ include file="/jsp/JspUi/heroSection.jsp" %>
            <%@ include file="/jsp/JspUi/socialProof.jsp" %>
            <%@ include file="/jsp/JspUi/featuresOVerview.jsp" %>
        </c:otherwise>
    </c:choose>
</main>

<!-- Include Footer -->
<%@ include file="/jsp/common/footer.jsp" %>

<!-- JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js"></script>

<!-- Page-specific JavaScript -->
<c:if test="${pageJsFiles != null}">
    <c:forEach var="jsFile" items="${pageJsFiles}">
        <script src="${pageContext.request.contextPath}/js/${jsFile}"></script>
    </c:forEach>
</c:if>

<c:if test="${pageJs != null}">
    <c:forTokens var="jsFile" items="${pageJs}" delims=",">
        <script src="${pageContext.request.contextPath}/js/${jsFile}"></script>
    </c:forTokens>
</c:if>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Enhanced loading screen with better text updates
        const loadingScreen = document.getElementById('pageLoading');
        const progressFill = document.querySelector('.progress-fill');
        const loadingSubtitle = document.querySelector('.loading-subtitle');

        if (loadingScreen && loadingSubtitle) {
            // Loading steps with proper timing
            const loadingSteps = [
                "Initializing web scraping platform...",
                "Loading crawl engines...",
                "Connecting to data sources...",
                "Preparing analytics dashboard...",
                "Ready to crawl!"
            ];

            let currentStep = 0;
            let progress = 0;

            // Force initial text
            loadingSubtitle.textContent = loadingSteps[0];

            const progressInterval = setInterval(() => {
                progress += Math.random() * 8 + 3; // Slower, more controlled progress

                if (progress >= 100) {
                    progress = 100;
                    loadingSubtitle.textContent = loadingSteps[loadingSteps.length - 1];
                    clearInterval(progressInterval);

                    setTimeout(() => {
                        loadingScreen.classList.add('hidden');
                        setTimeout(() => {
                            loadingScreen.remove();
                        }, 400);
                    }, 400); // Longer delay to show "Ready to crawl!"
                }

                // Update progress bar
                if (progressFill) {
                    progressFill.style.width = progress + '%';
                }

                // Better text update logic
                const stepThreshold = 100 / loadingSteps.length;
                const targetStep = Math.floor(progress / stepThreshold);

                if (targetStep !== currentStep && targetStep < loadingSteps.length) {
                    currentStep = targetStep;

                    // Smooth text transition
                    loadingSubtitle.style.opacity = '0.4';
                    setTimeout(() => {
                        loadingSubtitle.textContent = loadingSteps[currentStep];
                        loadingSubtitle.style.opacity = '0.8';
                    }, 150);
                }
            }, 300); // Slower interval for better visibility
        }

        // GSAP ScrollTrigger registration
        if (typeof gsap !== 'undefined') {
            gsap.registerPlugin(ScrollTrigger);
        }

        // Smooth scrolling for anchor links
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

        // Page transition effect
        const mainContent = document.querySelector('.main-content');
        if (mainContent) {
            mainContent.style.opacity = '1';
        }

        // Console branding
        console.log(`
        🚀 CrawlForge - Advanced Web Scraping Platform
        ═══════════════════════════════════════════════

        Current Page: ${pageTitle != null ? pageTitle : 'Home'}
    Build: Production
    Version: 1.0.0

    Happy Crawling! 🕷️
    `);
});
</script>
</body>
</html>

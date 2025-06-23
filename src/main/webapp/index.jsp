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
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">

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

    <style>
        /* Enhanced Loading Screen */
        .page-loading {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg,
            #1f1f2b 0%,
            #2c2c3a 25%,
            #40414f 50%,
            #2c2c3a 75%,
            #1f1f2b 100%);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            z-index: 9999;
            opacity: 1;
            transition: opacity 0.8s ease;
            overflow: hidden;
        }

        .page-loading::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: radial-gradient(circle at 20% 30%, rgba(16, 163, 127, 0.1) 0%, transparent 50%),
            radial-gradient(circle at 80% 70%, rgba(16, 163, 127, 0.08) 0%, transparent 50%);
            animation: float-bg 8s ease-in-out infinite;
        }

        @keyframes float-bg {
            0%, 100% {
                transform: translateY(0px) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(2deg);
            }
        }

        .page-loading.hidden {
            opacity: 0;
            pointer-events: none;
        }

        /* Loader Container */
        .loader-container {
            position: relative;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 2rem;
            z-index: 2;
        }

        /* Advanced Spinner */
        .advanced-spinner {
            position: relative;
            width: 120px;
            height: 120px;
        }

        /* Multiple Spinning Rings */
        .spinner-ring {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            border: 3px solid transparent;
            border-radius: 50%;
            animation: spin 2s linear infinite;
        }

        .spinner-ring-1 {
            border-top: 3px solid #10a37f;
            border-right: 3px solid rgba(16, 163, 127, 0.3);
            animation-duration: 2s;
            filter: drop-shadow(0 0 10px rgba(16, 163, 127, 0.5));
        }

        .spinner-ring-2 {
            border-bottom: 3px solid #0d8465;
            border-left: 3px solid rgba(13, 132, 101, 0.3);
            animation-duration: 1.5s;
            animation-direction: reverse;
            width: 90%;
            height: 90%;
            top: 5%;
            left: 5%;
            filter: drop-shadow(0 0 8px rgba(13, 132, 101, 0.4));
        }

        .spinner-ring-3 {
            border-top: 2px solid rgba(16, 163, 127, 0.6);
            border-right: 2px solid transparent;
            animation-duration: 1s;
            width: 70%;
            height: 70%;
            top: 15%;
            left: 15%;
            filter: drop-shadow(0 0 6px rgba(16, 163, 127, 0.3));
        }

        /* Center Logo with Pulse */
        .spinner-center {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 50px;
            height: 50px;
            background: linear-gradient(135deg,
            rgba(16, 163, 127, 0.2) 0%,
            rgba(64, 65, 79, 0.9) 30%,
            rgba(44, 44, 58, 0.95) 70%,
            rgba(31, 31, 43, 0.9) 100%);
            backdrop-filter: blur(20px);
            border: 2px solid rgba(16, 163, 127, 0.3);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: #10a37f;
            animation: pulse-center 2s ease-in-out infinite;
            box-shadow: 0 8px 32px rgba(16, 163, 127, 0.2),
            inset 0 1px 0 rgba(255, 255, 255, 0.1);
        }

        @keyframes pulse-center {
            0%, 100% {
                transform: translate(-50%, -50%) scale(1);
                box-shadow: 0 8px 32px rgba(16, 163, 127, 0.2),
                inset 0 1px 0 rgba(255, 255, 255, 0.1);
            }
            50% {
                transform: translate(-50%, -50%) scale(1.1);
                box-shadow: 0 12px 40px rgba(16, 163, 127, 0.4),
                inset 0 1px 0 rgba(255, 255, 255, 0.15);
            }
        }

        /* Floating Particles */
        .loader-particles {
            position: absolute;
            width: 200px;
            height: 200px;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            pointer-events: none;
        }

        .particle {
            position: absolute;
            width: 4px;
            height: 4px;
            background: #10a37f;
            border-radius: 50%;
            opacity: 0.7;
            box-shadow: 0 0 10px rgba(16, 163, 127, 0.5);
        }

        .particle:nth-child(1) {
            top: 20%;
            left: 20%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 0s;
        }

        .particle:nth-child(2) {
            top: 20%;
            right: 20%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 0.5s;
        }

        .particle:nth-child(3) {
            bottom: 20%;
            left: 20%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 1s;
        }

        .particle:nth-child(4) {
            bottom: 20%;
            right: 20%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 1.5s;
        }

        .particle:nth-child(5) {
            top: 50%;
            left: 10%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 2s;
        }

        .particle:nth-child(6) {
            top: 50%;
            right: 10%;
            animation: float-particle 3s ease-in-out infinite;
            animation-delay: 2.5s;
        }

        @keyframes float-particle {
            0%, 100% {
                transform: translateY(0px) scale(1);
                opacity: 0.7;
            }
            50% {
                transform: translateY(-20px) scale(1.2);
                opacity: 1;
            }
        }

        /* Loading Text */
        .loading-text {
            color: #ffffff;
            font-size: 1.2rem;
            font-weight: 600;
            text-align: center;
            position: relative;
            z-index: 2;
        }

        .loading-brand {
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 1.8rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
            animation: brand-glow 2s ease-in-out infinite;
            filter: drop-shadow(0 0 10px rgba(16, 163, 127, 0.3));
        }

        @keyframes brand-glow {
            0%, 100% {
                filter: drop-shadow(0 0 10px rgba(16, 163, 127, 0.3));
                transform: scale(1);
            }
            50% {
                filter: drop-shadow(0 0 20px rgba(16, 163, 127, 0.6));
                transform: scale(1.02);
            }
        }

        .loading-subtitle {
            color: #c5c5d2;
            opacity: 0.8;
            font-size: 0.9rem;
            animation: fade-pulse 2s ease-in-out infinite;
        }

        @keyframes fade-pulse {
            0%, 100% {
                opacity: 0.8;
            }
            50% {
                opacity: 0.4;
            }
        }

        /* Progress Bar */
        .loading-progress {
            width: 200px;
            height: 4px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 2px;
            overflow: hidden;
            margin-top: 1rem;
            position: relative;
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.3);
        }

        .progress-fill {
            height: 100%;
            background: linear-gradient(90deg, #10a37f, #0d8465);
            border-radius: 2px;
            width: 0%;
            animation: progress-fill 3s ease-in-out infinite;
            position: relative;
            box-shadow: 0 0 10px rgba(16, 163, 127, 0.5);
        }

        .progress-fill::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(255, 255, 255, 0.4) 50%,
            transparent 100%);
            animation: progress-shine 1.5s ease-in-out infinite;
        }

        @keyframes progress-fill {
            0% {
                width: 0%;
            }
            70% {
                width: 100%;
            }
            100% {
                width: 100%;
            }
        }

        @keyframes progress-shine {
            0% {
                transform: translateX(-100%);
            }
            100% {
                transform: translateX(100%);
            }
        }

        /* Spinning Animation */
        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        /* Your existing scrollbar CSS */
        ::-webkit-scrollbar {
            width: 8px;
            height: 8px;
        }

        ::-webkit-scrollbar-track {
            background: linear-gradient(135deg,
            rgba(31, 31, 43, 0.8) 0%,
            rgba(44, 44, 58, 0.9) 100%);
            border-radius: 10px;
            box-shadow: inset 0 2px 6px rgba(0, 0, 0, 0.3);
        }

        ::-webkit-scrollbar-thumb {
            background: linear-gradient(135deg,
            rgba(16, 163, 127, 0.8) 0%,
            rgba(13, 132, 101, 0.9) 50%,
            rgba(16, 163, 127, 0.8) 100%);
            border-radius: 10px;
            border: 1px solid rgba(16, 163, 127, 0.3);
            box-shadow: 0 2px 6px rgba(16, 163, 127, 0.2),
            inset 0 1px 0 rgba(255, 255, 255, 0.1);
            transition: all 0.3s ease;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: linear-gradient(135deg,
            rgba(16, 163, 127, 1) 0%,
            rgba(13, 132, 101, 1) 50%,
            rgba(16, 163, 127, 1) 100%);
            box-shadow: 0 4px 12px rgba(16, 163, 127, 0.4),
            inset 0 1px 0 rgba(255, 255, 255, 0.2);
            transform: scale(1.1);
        }

        /* Smooth scrolling */
        html {
            scroll-behavior: smooth;
            scrollbar-width: thin;
            scrollbar-color: rgba(16, 163, 127, 0.8) rgba(31, 31, 43, 0.8);
        }

        .main-content {
            opacity: 0;
            animation: fadeInContent 0.8s ease-out forwards;
        }

        @keyframes fadeInContent {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .advanced-spinner {
                width: 80px;
                height: 80px;
            }

            .spinner-center {
                width: 35px;
                height: 35px;
                font-size: 1rem;
            }

            .loading-brand {
                font-size: 1.5rem;
            }

            .loading-progress {
                width: 150px;
            }
        }
    </style>
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
        // Enhanced loading screen with progress simulation
        const loadingScreen = document.getElementById('pageLoading');
        const progressFill = document.querySelector('.progress-fill');
        const loadingSubtitle = document.querySelector('.loading-subtitle');

        if (loadingScreen) {
            // Simulate loading steps
            const loadingSteps = [
                "Initializing web scraping platform...",
                "Loading crawl engines...",
                "Connecting to data sources...",
                "Preparing analytics dashboard...",
                "Ready to crawl!"
            ];

            let currentStep = 0;
            let progress = 0;

            const progressInterval = setInterval(() => {
                progress += Math.random() * 15 + 5;

                if (progress >= 100) {
                    progress = 100;
                    clearInterval(progressInterval);

                    setTimeout(() => {
                        loadingScreen.classList.add('hidden');
                        setTimeout(() => {
                            loadingScreen.remove();
                        }, 800);
                    }, 500);
                }

                // Update progress bar
                if (progressFill) {
                    progressFill.style.width = progress + '%';
                }

                // Update loading text
                if (loadingSubtitle && currentStep < loadingSteps.length - 1) {
                    if (progress > (currentStep + 1) * 20) {
                        currentStep++;
                        loadingSubtitle.textContent = loadingSteps[currentStep];
                    }
                }
            }, 200);
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

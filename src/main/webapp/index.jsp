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

    <!-- Only add favicon if you have the file -->
    <!-- <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"> -->

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

    <!-- Your existing custom scrollbar CSS stays the same -->
    <style>
        /* Loading Screen */
        .page-loading {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #1f1f2b 0%, #2c2c3a 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 9999;
            opacity: 1;
            transition: opacity 0.5s ease;
        }

        .page-loading.hidden {
            opacity: 0;
            pointer-events: none;
        }

        .loading-spinner {
            width: 50px;
            height: 50px;
            border: 3px solid rgba(16, 163, 127, 0.3);
            border-top: 3px solid #10a37f;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }

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

        /* ... rest of your existing styles ... */

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
    </style>
</head>
<body>
<!-- Loading Screen -->
<div class="page-loading" id="pageLoading">
    <div class="loading-spinner"></div>
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
        // Hide loading screen
        const loadingScreen = document.getElementById('pageLoading');
        if (loadingScreen) {
            setTimeout(() => {
                loadingScreen.classList.add('hidden');
                setTimeout(() => {
                    loadingScreen.remove();
                }, 500);
            }, 1000);
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


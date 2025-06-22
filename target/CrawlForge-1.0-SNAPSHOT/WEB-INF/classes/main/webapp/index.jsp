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
    <meta name="description" content="CrawlForge is an advanced AI-powered web scraping platform that extracts data from any website with precision, scale, and intelligence. Start your free trial today.">
    <meta name="keywords" content="web scraping, data extraction, web crawler, API scraping, automated data collection, CrawlForge">
    <meta name="author" content="CrawlForge">
    <meta name="robots" content="index, follow">
    <meta name="language" content="English">
    <meta name="revisit-after" content="7 days">

    <!-- Open Graph Meta Tags -->
    <meta property="og:title" content="${pageTitle != null ? pageTitle : 'CrawlForge - Advanced Web Scraping Platform'}">
    <meta property="og:description" content="AI-powered web scraping platform that extracts data from any website with precision and scale.">
    <meta property="og:image" content="${pageContext.request.contextPath}/images/crawlforge-og-image.png">
    <meta property="og:url" content="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}">
    <meta property="og:type" content="website">
    <meta property="og:site_name" content="CrawlForge">

    <!-- Twitter Card Meta Tags -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${pageTitle != null ? pageTitle : 'CrawlForge - Advanced Web Scraping Platform'}">
    <meta name="twitter:description" content="AI-powered web scraping platform that extracts data from any website with precision and scale.">
    <meta name="twitter:image" content="${pageContext.request.contextPath}/images/crawlforge-twitter-card.png">
    <meta name="twitter:site" content="@crawlforge">
    <meta name="twitter:creator" content="@crawlforge">

    <!-- Favicon and App Icons -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/favicon-16x16.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="${pageContext.request.contextPath}/images/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="192x192" href="${pageContext.request.contextPath}/images/android-icon-192x192.png">

    <!-- Apple Touch Icons -->
    <link rel="apple-touch-icon" sizes="57x57" href="${pageContext.request.contextPath}/images/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="${pageContext.request.contextPath}/images/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${pageContext.request.contextPath}/images/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/images/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="${pageContext.request.contextPath}/images/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="${pageContext.request.contextPath}/images/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="${pageContext.request.contextPath}/images/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="${pageContext.request.contextPath}/images/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/apple-icon-180x180.png">

    <!-- Microsoft Tiles -->
    <meta name="msapplication-TileColor" content="#10a37f">
    <meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/images/ms-icon-144x144.png">
    <meta name="msapplication-config" content="${pageContext.request.contextPath}/browserconfig.xml">

    <!-- PWA Manifest -->
    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">

    <!-- Theme Colors -->
    <meta name="theme-color" content="#10a37f">
    <meta name="msapplication-navbutton-color" content="#10a37f">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-title" content="CrawlForge">

    <!-- Security Headers -->
    <meta http-equiv="Content-Security-Policy" content="default-src 'self' 'unsafe-inline' 'unsafe-eval' https: data:; img-src 'self' https: data:;">
    <meta http-equiv="X-Content-Type-Options" content="nosniff">
    <meta http-equiv="X-Frame-Options" content="SAMEORIGIN">
    <meta http-equiv="X-XSS-Protection" content="1; mode=block">
    <meta http-equiv="Referrer-Policy" content="strict-origin-when-cross-origin">

    <!-- Preconnect for Performance -->
    <link rel="preconnect" href="https://cdn.jsdelivr.net">
    <link rel="preconnect" href="https://cdnjs.cloudflare.com">
    <link rel="dns-prefetch" href="https://fonts.googleapis.com">

    <!-- Canonical URL -->
    <link rel="canonical" href="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}${pageContext.request.servletPath}">

    <!-- All Common Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" integrity="sha384-Ay26V2wLhFZVe4pjvMoZaQZsulfQhiQFd4vQk4B4Y8YqS1Zs8vQhiQFd4vQk4B4Y" crossorigin="anonymous">

    <!-- Preload Critical Resources -->
    <link rel="preload" href="${pageContext.request.contextPath}/css/style.css" as="style">
    <link rel="preload" href="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js" as="script">

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

    <!-- Custom Scrollbar and Theme CSS -->
    <style>
        /* Your scrollbar CSS from previous implementation */
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
            box-shadow:
                    0 2px 6px rgba(16, 163, 127, 0.2),
                    inset 0 1px 0 rgba(255, 255, 255, 0.1);
            transition: all 0.3s ease;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: linear-gradient(135deg,
            rgba(16, 163, 127, 1) 0%,
            rgba(13, 132, 101, 1) 50%,
            rgba(16, 163, 127, 1) 100%);
            box-shadow:
                    0 4px 12px rgba(16, 163, 127, 0.4),
                    inset 0 1px 0 rgba(255, 255, 255, 0.2);
            transform: scale(1.1);
        }

        /* Smooth scrolling */
        html {
            scroll-behavior: smooth;
            scrollbar-width: thin;
            scrollbar-color: rgba(16, 163, 127, 0.8) rgba(31, 31, 43, 0.8);
        }

        /* Page transition */
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

        /* Loading state */
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
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>

    <!-- Structured Data (JSON-LD) -->
    <script type="application/ld+json">
        {
            "@context": "https://schema.org",
            "@type": "SoftwareApplication",
            "name": "CrawlForge",
            "description": "Advanced AI-powered web scraping platform for data extraction",
            "url": "${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}",
        "applicationCategory": "BusinessApplication",
        "operatingSystem": "Web Browser",
        "offers": {
            "@type": "Offer",
            "price": "0",
            "priceCurrency": "USD",
            "description": "Free trial available"
        },
        "creator": {
            "@type": "Organization",
            "name": "CrawlForge",
            "url": "${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}"
        }
    }
    </script>
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
            <!-- Include dynamic content page -->
            <jsp:include page="${contentPage}" />
        </c:when>
        <c:otherwise>
            <!-- Default home content -->
            <%@ include file="/jsp/JspUi/heroSection.jsp" %>
            <%@ include file="/jsp/JspUi/socialProof.jsp" %>
            <%@ include file="/jsp/JspUi/featuresOVerview.jsp" %>
        </c:otherwise>
    </c:choose>
</main>

<!-- Include Footer -->
<%@ include file="/jsp/common/footer.jsp" %>

<!-- All Common JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js" integrity="sha512-16esztaSRplJROstbIIdwX3N97V1+pZvV33ABoG1H2OyTttBxEGkTsoIVsiP1iaTtM8b3+hu2kB6pQ4Clr5yug==" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js" integrity="sha512-Ic9xkERjyZ1xgJ5svx3y0u3xrvfT/uPkV99LBwe68xjy/mGtO+4eURHZBW2xW4SZbFrF1Tf090XqB+EVgXnVjw==" crossorigin="anonymous"></script>

<!-- Page-specific JavaScript -->
<c:if test="${pageJsFiles != null}">
    <c:forEach var="jsFile" items="${pageJsFiles}">
        <script src="${pageContext.request.contextPath}/js/${jsFile}"></script>
    </c:forEach>
</c:if>

<!-- Backward compatibility for single JS -->
<c:if test="${pageJs != null}">
    <c:forTokens var="jsFile" items="${pageJs}" delims=",">
        <script src="${pageContext.request.contextPath}/js/${jsFile}"></script>
    </c:forTokens>
</c:if>

<!-- Common JavaScript for all pages -->
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

        // Performance monitoring
        if ('performance' in window) {
            window.addEventListener('load', function() {
                const loadTime = performance.timing.loadEventEnd - performance.timing.navigationStart;
                console.log('Page load time:', loadTime + 'ms');
            });
        }

        // Service Worker registration for PWA
        if ('serviceWorker' in navigator) {
            navigator.serviceWorker.register('${pageContext.request.contextPath}/sw.js')
                .then(registration => console.log('SW registered'))
                .catch(error => console.log('SW registration failed'));
        }

        // Console branding
        console.log(`
        🚀 CrawlForge - Advanced Web Scraping Platform
        ═══════════════════════════════════════════════

        Current Page: ${pageTitle != null ? pageTitle : 'Home'}
    Build: Production
    Version: 1.0.0
    Environment: ${pageContext.request.scheme}://${pageContext.request.serverName}

    Happy Crawling! 🕷️
    `);
});

// Error handling
window.addEventListener('error', function(e) {
    console.error('JavaScript error:', e.error);
    // You can send error reports to your analytics service here
});

// Unhandled promise rejection handling
window.addEventListener('unhandledrejection', function(e) {
    console.error('Unhandled promise rejection:', e.reason);
    e.preventDefault();
});
</script>

<!-- Google Analytics (replace with your tracking ID) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=GA_TRACKING_ID"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'GA_TRACKING_ID');
</script>
</body>
</html>

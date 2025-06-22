<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle != null ? pageTitle : 'Home - Advanced Web Scraping Platform'} | CrawlForge</title>

    <!-- All Common Dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js" rel="preload" as="script">

    <!-- Your Custom CSS Files -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/heroSection.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/social-proof.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/features-overview.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/scrollbar.css">

    <!-- Page-specific CSS -->
    <c:if test="${pageCss != null}">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${pageCss}">
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
    </style>
</head>
<body>
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
            <%@ include file="/jsp/sections/hero-section.jsp" %>
            <%@ include file="/jsp/sections/social-proof.jsp" %>
            <%@ include file="/jsp/sections/features-overview.jsp" %>
        </c:otherwise>
    </c:choose>
</main>

<!-- Include Footer -->
<%@ include file="/jsp/common/footer.jsp" %>

<!-- All Common JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js"></script>

<!-- Page-specific JavaScript -->
<c:if test="${pageJs != null}">
    <script src="${pageContext.request.contextPath}/js/${pageJs}"></script>
</c:if>

<!-- Common JavaScript for all pages -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
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

        Current Page: ${pageTitle || 'Home'}
    Build: Production
    Version: 1.0.0

    Happy Crawling! 🕷️
    `);
});
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CrawlForge </title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/scroll.css">

    <!-- Required meta tags for SEO -->
    <meta name="description" content="CrawlForge - Your web crawling and scraping solution.">
    <meta name="keywords" content="web crawler, web scraping, Java, Maven, CrawlForge">
    <meta name="author" content="Your Name or Company">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Open Graph for social sharing -->
    <meta property="og:title" content="CrawlForge">
    <meta property="og:description" content="CrawlForge - Your web crawling and scraping solution.">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://yourdomain.com/">
    <meta property="og:image" content="<meta property="og:image" content="${pageContext.request.contextPath}/img/home.png">">

    <!-- Twitter Card -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="CrawlForge">
    <meta name="twitter:description" content="CrawlForge - Your web crawling and scraping solution.">
    <meta name="twitter:image" content="${pageContext.request.contextPath}/img/home.png">


</head>
<body>

<!-- Navbar -->
<%@ include file="/jsp/common/header.jsp" %>

<!-- Hero Section -->
<%@ include file="/jsp/JspUi/heroSection.jsp" %>

<%-- Crawl Sections--%>
<%--<%@ include file="/jsp/JspUi/linktaker.jsp" %>--%>

<!-- Footer -->
<%@ include file="/jsp/common/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scroll.js"></script>
</body>
</html>
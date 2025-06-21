<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebCrawler Analytics</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/scroll.css">

    <style>
        body {
            background-color: #1f1f2b;
            color: #ffffff;
            font-family: 'Inter', sans-serif;
        }

        .hero {
            background: linear-gradient(to right, #1f1f2b, #2c2c3a);
            padding: 80px 0;
            text-align: center;
        }

        .hero-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: #10a37f;
        }

        .hero-subtitle {
            font-size: 1.25rem;
            color: #c5c5d2;
        }

        .btn-primary-custom {
            background-color: #10a37f;
            color: #fff;
            border: none;
            padding: 0.6rem 1.4rem;
            border-radius: 8px;
            font-weight: 500;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .btn-primary-custom:hover {
            background-color: #0d8465;
        }

        .card-glass {
            background-color: rgba(64, 65, 79, 0.92);
            backdrop-filter: blur(6px);
            border: 1px solid #5c5f6e;
            border-radius: 12px;
            padding: 1.5rem;
        }

        footer {
            background-color: #40414f;
            color: #c5c5d2;
            padding: 1rem 0;
            text-align: center;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<%@ include file="/jsp/common/header.jsp" %>

<!-- Hero Section -->
<%@ include file="/jsp/JspUi/heroSection.jsp" %>

<%-- Crawl Sections--%>
<%@ include file="/jsp/JspUi/linktaker.jsp" %>

<!-- Footer -->
<%@ include file="/jsp/common/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scroll.js"></script>
</body>
</html>
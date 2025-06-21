<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebCrawler Analytics</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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

<!-- Crawl Form Section -->
<%--<section id="crawlerForm" class="container my-5">--%>
<%--    <div class="row justify-content-center">--%>
<%--        <div class="col-lg-7">--%>
<%--            <div class="card-glass">--%>
<%--                <h3 class="mb-4 text-primary"><i class="bi bi-search me-2"></i>New Crawl Task</h3>--%>
<%--                <form action="crawl" method="post">--%>
<%--                    <div class="form-floating mb-3">--%>
<%--                        <input type="url" class="form-control bg-dark text-white" id="url" name="url"--%>
<%--                               placeholder="https://example.com" required>--%>
<%--                        <label for="url">Target URL</label>--%>
<%--                    </div>--%>
<%--                    <div class="row g-3">--%>
<%--                        <div class="col-md-6 form-floating">--%>
<%--                            <input type="number" class="form-control bg-dark text-white" id="depth" name="depth"--%>
<%--                                   value="2" min="1" max="10">--%>
<%--                            <label for="depth">Crawl Depth</label>--%>
<%--                        </div>--%>
<%--                        <div class="col-md-6 form-floating">--%>
<%--                            <input type="number" class="form-control bg-dark text-white" id="threads" name="threads"--%>
<%--                                   value="4" min="1" max="10">--%>
<%--                            <label for="threads">Threads</label>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="form-check form-switch mt-4 mb-3">--%>
<%--                        <input class="form-check-input" type="checkbox" id="externalLinks" name="externalLinks">--%>
<%--                        <label class="form-check-label text-muted" for="externalLinks">Follow external links</label>--%>
<%--                    </div>--%>
<%--                    <div class="d-flex justify-content-between">--%>
<%--                        <button type="submit" class="btn btn-primary-custom px-4">Start</button>--%>
<%--                        <button type="reset" class="btn btn-outline-light px-4">Reset</button>--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>

<!-- Footer -->
<footer>
    <small>&copy; 2025 WebCrawler Analytics. Built with ❤️ and Java.</small>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
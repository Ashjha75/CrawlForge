<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Success - Feedback Submitted</title>
    <%@include file="../components/links.jsp" %>
    <style>
        body {
            background-color: #1e1f22;
        }

        .check-icon {
            font-size: 5rem;
            color: #28a745;
        }
    </style>
</head>
<body>
<%@include file="../components/headers.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card" style="background-color: #2b2d30;">
                <div class="card-body p-4 text-center text-white">
                    <i class="bi bi-check-circle-fill check-icon mb-3"></i>
                    <h2 class="mb-4">Feedback Submitted Successfully!</h2>
                    <div class="text-start mb-4">
                        <div class="mb-3">
                            <label class="text-primary">Email address</label>
                            <p class="h5">${email}</p>
                        </div>
                        <div class="mb-3">
                            <label class="text-primary">Mobile Number</label>
                            <p class="h5">${mobile}</p>
                        </div>
                        <div class="mb-3">
                            <label class="text-primary">Your Feedback</label>
                            <p class="h5">${feedback}</p>
                        </div>
                    </div>
                    <a href="../servlet_tut_war_exploded/
" class="btn btn-primary px-4">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../components/script.jsp" %>
</body>
</html>
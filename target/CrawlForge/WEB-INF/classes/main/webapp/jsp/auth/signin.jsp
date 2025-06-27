<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In - CrawlForge</title>

    <!-- Your existing dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Sign In Page Styles - Matching Your Theme */
        body {
            background: linear-gradient(135deg,
            #1f1f2b 0%,
            #2c2c3a 25%,
            #40414f 50%,
            #2c2c3a 75%,
            #1f1f2b 100%);
            min-height: 100vh;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            overflow-x: hidden;
        }

        /* Animated Background */
        .signin-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: radial-gradient(circle at 20% 30%, rgba(16, 163, 127, 0.1) 0%, transparent 50%),
            radial-gradient(circle at 80% 70%, rgba(16, 163, 127, 0.08) 0%, transparent 50%);
            animation: float-bg 8s ease-in-out infinite;
            z-index: 0;
        }

        @keyframes float-bg {
            0%, 100% {
                transform: translateY(0px) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(2deg);
            }
        }

        /* Main Container */
        .signin-container {
            position: relative;
            z-index: 2;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem 1rem;
        }

        /* Sign In Card */
        .signin-card {
            background: linear-gradient(135deg,
            rgba(64, 65, 79, 0.6) 0%,
            rgba(44, 44, 58, 0.8) 100%);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 24px;
            padding: 3rem;
            width: 100%;
            max-width: 450px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3),
            inset 0 1px 0 rgba(255, 255, 255, 0.1);
            position: relative;
            overflow: hidden;
            animation: fadeInUp 0.8s ease-out;
        }

        .signin-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(16, 163, 127, 0.05) 50%,
            transparent 100%);
            animation: card-shine 3s ease-in-out infinite;
        }

        @keyframes card-shine {
            0% {
                left: -100%;
            }
            100% {
                left: 100%;
            }
        }

        /* Header */
        .signin-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .signin-logo {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            margin-bottom: 1rem;
        }

        .signin-logo i {
            font-size: 2rem;
            color: #10a37f;
            animation: logo-pulse 2s ease-in-out infinite;
        }

        @keyframes logo-pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
        }

        .signin-brand {
            font-size: 1.8rem;
            font-weight: 700;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            margin: 0;
        }

        .signin-subtitle {
            color: #c5c5d2;
            opacity: 0.8;
            margin: 0;
            font-size: 0.95rem;
        }

        /* Form Styles */
        .signin-form {
            position: relative;
            z-index: 2;
        }

        .form-group {
            margin-bottom: 1.5rem;
            position: relative;
        }

        .form-label {
            color: #ffffff;
            font-weight: 600;
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-label i {
            color: #10a37f;
            font-size: 0.9rem;
        }

        .form-input {
            width: 100%;
            background: linear-gradient(135deg,
            rgba(31, 31, 43, 0.8) 0%,
            rgba(44, 44, 58, 0.9) 100%);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            padding: 1rem 1.2rem;
            color: #ffffff;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        .form-input:focus {
            outline: none;
            border-color: rgba(16, 163, 127, 0.5);
            box-shadow: 0 0 0 4px rgba(16, 163, 127, 0.1),
            inset 0 2px 4px rgba(0, 0, 0, 0.2);
            background: linear-gradient(135deg,
            rgba(31, 31, 43, 0.9) 0%,
            rgba(44, 44, 58, 0.95) 100%);
        }

        .form-input::placeholder {
            color: #c5c5d2;
            opacity: 0.6;
        }

        /* Password Toggle */
        .password-toggle {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #10a37f;
            cursor: pointer;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            z-index: 3;
        }

        .password-toggle:hover {
            color: #0d8465;
            transform: translateY(-50%) scale(1.1);
        }

        /* Remember Me */
        .remember-forgot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: #c5c5d2;
            font-size: 0.9rem;
        }

        .remember-checkbox {
            width: 18px;
            height: 18px;
            background: transparent;
            border: 2px solid rgba(16, 163, 127, 0.5);
            border-radius: 4px;
            cursor: pointer;
            position: relative;
            transition: all 0.3s ease;
        }

        .remember-checkbox:checked {
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            border-color: #10a37f;
        }

        .remember-checkbox:checked::after {
            content: '✓';
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 12px;
            font-weight: bold;
        }

        .forgot-link {
            color: #10a37f;
            text-decoration: none;
            font-size: 0.9rem;
            transition: all 0.3s ease;
        }

        .forgot-link:hover {
            color: #0d8465;
            text-decoration: underline;
        }

        /* Sign In Button */
        .signin-btn {
            width: 100%;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            color: white;
            border: none;
            border-radius: 12px;
            padding: 1rem 2rem;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3);
        }

        .signin-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(255, 255, 255, 0.2) 50%,
            transparent 100%);
            transition: left 0.6s ease;
        }

        .signin-btn:hover::before {
            left: 100%;
        }

        .signin-btn:hover {
            background: linear-gradient(135deg, #0d8465 0%, #0a6b52 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(16, 163, 127, 0.4);
        }

        .signin-btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }

        .signin-btn .btn-text {
            position: relative;
            z-index: 2;
        }

        /* Loading Spinner */
        .btn-spinner {
            display: none;
            width: 20px;
            height: 20px;
            border: 2px solid rgba(255, 255, 255, 0.3);
            border-top: 2px solid white;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-right: 0.5rem;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        /* Divider */
        .signin-divider {
            display: flex;
            align-items: center;
            margin: 1.5rem 0;
            color: #c5c5d2;
            opacity: 0.6;
            font-size: 0.9rem;
        }

        .signin-divider::before,
        .signin-divider::after {
            content: '';
            flex: 1;
            height: 1px;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(255, 255, 255, 0.2) 50%,
            transparent 100%);
        }

        .signin-divider span {
            padding: 0 1rem;
        }

        /* Sign Up Link */
        .signup-link {
            text-align: center;
            color: #c5c5d2;
            font-size: 0.9rem;
        }

        .signup-link a {
            color: #10a37f;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .signup-link a:hover {
            color: #0d8465;
            text-decoration: underline;
        }

        /* Error Messages */
        .error-message {
            background: linear-gradient(135deg,
            rgba(220, 53, 69, 0.15) 0%,
            rgba(220, 53, 69, 0.1) 100%);
            border: 1px solid rgba(220, 53, 69, 0.3);
            border-radius: 8px;
            padding: 0.8rem 1rem;
            margin-bottom: 1rem;
            color: #ff6b7a;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            animation: shake 0.5s ease-in-out;
        }

        @keyframes shake {
            0%, 100% {
                transform: translateX(0);
            }
            25% {
                transform: translateX(-5px);
            }
            75% {
                transform: translateX(5px);
            }
        }

        .error-message i {
            color: #dc3545;
        }

        /* Success Messages */
        .success-message {
            background: linear-gradient(135deg,
            rgba(40, 167, 69, 0.15) 0%,
            rgba(40, 167, 69, 0.1) 100%);
            border: 1px solid rgba(40, 167, 69, 0.3);
            border-radius: 8px;
            padding: 0.8rem 1rem;
            margin-bottom: 1rem;
            color: #28a745;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .success-message i {
            color: #28a745;
        }

        /* Responsive Design */
        @media (max-width: 576px) {
            .signin-card {
                padding: 2rem 1.5rem;
                margin: 1rem;
                border-radius: 16px;
            }

            .signin-brand {
                font-size: 1.5rem;
            }
        }

        /* Animation Keyframes */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In - CrawlForge</title>

    <!-- Your existing dependencies -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Sign In Page Styles - Matching Your Theme */
        body {
            background: linear-gradient(135deg,
            #1f1f2b 0%,
            #2c2c3a 25%,
            #40414f 50%,
            #2c2c3a 75%,
            #1f1f2b 100%);
            min-height: 100vh;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            overflow-x: hidden;
        }

        /* Animated Background */
        .signin-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: radial-gradient(circle at 20% 30%, rgba(16, 163, 127, 0.1) 0%, transparent 50%),
            radial-gradient(circle at 80% 70%, rgba(16, 163, 127, 0.08) 0%, transparent 50%);
            animation: float-bg 8s ease-in-out infinite;
            z-index: 0;
        }

        @keyframes float-bg {
            0%, 100% {
                transform: translateY(0px) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(2deg);
            }
        }

        /* Main Container */
        .signin-container {
            position: relative;
            z-index: 2;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem 1rem;
        }

        /* Sign In Card */
        .signin-card {
            background: linear-gradient(135deg,
            rgba(64, 65, 79, 0.6) 0%,
            rgba(44, 44, 58, 0.8) 100%);
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 24px;
            padding: 3rem;
            width: 100%;
            max-width: 450px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3),
            inset 0 1px 0 rgba(255, 255, 255, 0.1);
            position: relative;
            overflow: hidden;
            animation: fadeInUp 0.8s ease-out;
        }

        .signin-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(16, 163, 127, 0.05) 50%,
            transparent 100%);
            animation: card-shine 3s ease-in-out infinite;
        }

        @keyframes card-shine {
            0% {
                left: -100%;
            }
            100% {
                left: 100%;
            }
        }

        /* Header */
        .signin-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .signin-logo {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            margin-bottom: 1rem;
        }

        .signin-logo i {
            font-size: 2rem;
            color: #10a37f;
            animation: logo-pulse 2s ease-in-out infinite;
        }

        @keyframes logo-pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
        }

        .signin-brand {
            font-size: 1.8rem;
            font-weight: 700;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            margin: 0;
        }

        .signin-subtitle {
            color: #c5c5d2;
            opacity: 0.8;
            margin: 0;
            font-size: 0.95rem;
        }

        /* Form Styles */
        .signin-form {
            position: relative;
            z-index: 2;
        }

        .form-group {
            margin-bottom: 1.5rem;
            position: relative;
        }

        .form-label {
            color: #ffffff;
            font-weight: 600;
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-label i {
            color: #10a37f;
            font-size: 0.9rem;
        }

        .form-input {
            width: 100%;
            background: linear-gradient(135deg,
            rgba(31, 31, 43, 0.8) 0%,
            rgba(44, 44, 58, 0.9) 100%);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            padding: 1rem 1.2rem;
            color: #ffffff;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        .form-input:focus {
            outline: none;
            border-color: rgba(16, 163, 127, 0.5);
            box-shadow: 0 0 0 4px rgba(16, 163, 127, 0.1),
            inset 0 2px 4px rgba(0, 0, 0, 0.2);
            background: linear-gradient(135deg,
            rgba(31, 31, 43, 0.9) 0%,
            rgba(44, 44, 58, 0.95) 100%);
        }

        .form-input::placeholder {
            color: #c5c5d2;
            opacity: 0.6;
        }

        /* Password Toggle */
        .password-toggle {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #10a37f;
            cursor: pointer;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            z-index: 3;
        }

        .password-toggle:hover {
            color: #0d8465;
            transform: translateY(-50%) scale(1.1);
        }

        /* Remember Me */
        .remember-forgot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: #c5c5d2;
            font-size: 0.9rem;
        }

        .remember-checkbox {
            width: 18px;
            height: 18px;
            background: transparent;
            border: 2px solid rgba(16, 163, 127, 0.5);
            border-radius: 4px;
            cursor: pointer;
            position: relative;
            transition: all 0.3s ease;
        }

        .remember-checkbox:checked {
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            border-color: #10a37f;
        }

        .remember-checkbox:checked::after {
            content: '✓';
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 12px;
            font-weight: bold;
        }

        .forgot-link {
            color: #10a37f;
            text-decoration: none;
            font-size: 0.9rem;
            transition: all 0.3s ease;
        }

        .forgot-link:hover {
            color: #0d8465;
            text-decoration: underline;
        }

        /* Sign In Button */
        .signin-btn {
            width: 100%;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            color: white;
            border: none;
            border-radius: 12px;
            padding: 1rem 2rem;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3);
        }

        .signin-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(255, 255, 255, 0.2) 50%,
            transparent 100%);
            transition: left 0.6s ease;
        }

        .signin-btn:hover::before {
            left: 100%;
        }

        .signin-btn:hover {
            background: linear-gradient(135deg, #0d8465 0%, #0a6b52 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(16, 163, 127, 0.4);
        }

        .signin-btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }

        .signin-btn .btn-text {
            position: relative;
            z-index: 2;
        }

        /* Loading Spinner */
        .btn-spinner {
            display: none;
            width: 20px;
            height: 20px;
            border: 2px solid rgba(255, 255, 255, 0.3);
            border-top: 2px solid white;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-right: 0.5rem;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        /* Divider */
        .signin-divider {
            display: flex;
            align-items: center;
            margin: 1.5rem 0;
            color: #c5c5d2;
            opacity: 0.6;
            font-size: 0.9rem;
        }

        .signin-divider::before,
        .signin-divider::after {
            content: '';
            flex: 1;
            height: 1px;
            background: linear-gradient(90deg,
            transparent 0%,
            rgba(255, 255, 255, 0.2) 50%,
            transparent 100%);
        }

        .signin-divider span {
            padding: 0 1rem;
        }

        /* Sign Up Link */
        .signup-link {
            text-align: center;
            color: #c5c5d2;
            font-size: 0.9rem;
        }

        .signup-link a {
            color: #10a37f;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .signup-link a:hover {
            color: #0d8465;
            text-decoration: underline;
        }

        /* Error Messages */
        .error-message {
            background: linear-gradient(135deg,
            rgba(220, 53, 69, 0.15) 0%,
            rgba(220, 53, 69, 0.1) 100%);
            border: 1px solid rgba(220, 53, 69, 0.3);
            border-radius: 8px;
            padding: 0.8rem 1rem;
            margin-bottom: 1rem;
            color: #ff6b7a;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            animation: shake 0.5s ease-in-out;
        }

        @keyframes shake {
            0%, 100% {
                transform: translateX(0);
            }
            25% {
                transform: translateX(-5px);
            }
            75% {
                transform: translateX(5px);
            }
        }

        .error-message i {
            color: #dc3545;
        }

        /* Success Messages */
        .success-message {
            background: linear-gradient(135deg,
            rgba(40, 167, 69, 0.15) 0%,
            rgba(40, 167, 69, 0.1) 100%);
            border: 1px solid rgba(40, 167, 69, 0.3);
            border-radius: 8px;
            padding: 0.8rem 1rem;
            margin-bottom: 1rem;
            color: #28a745;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .success-message i {
            color: #28a745;
        }

        /* Responsive Design */
        @media (max-width: 576px) {
            .signin-card {
                padding: 2rem 1.5rem;
                margin: 1rem;
                border-radius: 16px;
            }

            .signin-brand {
                font-size: 1.5rem;
            }
        }

        /* Animation Keyframes */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
<div class="signin-background"></div>
<div class="signin-container">
    <div class="signin-card">
        <div class="signin-header">
            <div class="signin-logo">
                <i class="bi bi-robot"></i>
                <h1 class="signin-brand">CrawlForge</h1>
            </div>
            <p class="signin-subtitle">Welcome back! Sign in to your account</p>
        </div>
        <c:if test="${not empty error}">
<%--            <div class="error-message">--%>
<%--                <i class="bi bi-exclamation-triangle"></i>--%>
<%--                <span>${error}</span>--%>
<%--            </div>--%>
        </c:if>
        <c:if test="${not empty success}">
            <div class="success-message">
                <i class="bi bi-check-circle"></i>
                <span>${success}</span>
            </div>
        </c:if>
        <form class="signin-form" id="signinForm" method="POST" action="${pageContext.request.contextPath}/authenticate"
              data-dashboard-url="${pageContext.request.contextPath}/dashboard">
            <div class="form-group">
                <label class="form-label" for="username">
                    <i class="bi bi-person"></i>
                    Username or Email
                </label>
                <input type="text"
                       class="form-input"
                       id="username"
                       name="username"
                       placeholder="Enter your username or email"
                       required
                       autocomplete="username">
            </div>
            <div class="form-group">
                <label class="form-label" for="password">
                    <i class="bi bi-lock"></i>
                    Password
                </label>
                <div style="position: relative;">
                    <input type="password"
                           class="form-input"
                           id="password"
                           name="password"
                           placeholder="Enter your password"
                           required
                           autocomplete="current-password">
                    <button type="button" class="password-toggle" onclick="togglePassword()">
                        <i class="bi bi-eye" id="passwordToggleIcon"></i>
                    </button>
                </div>
            </div>
            <div class="remember-forgot">
                <label class="remember-me">
                    <input type="checkbox" class="remember-checkbox" name="rememberMe">
                    <span>Remember me</span>
                </label>
                <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-link">
                    Forgot password?
                </a>
            </div>
            <button type="submit" class="signin-btn" id="signinBtn">
                <div class="btn-spinner" id="btnSpinner"></div>
                <span class="btn-text">Sign In</span>
            </button>
            <input type="hidden" name="csrfToken" value="${csrfToken}">
        </form>
        <div class="signin-divider">
            <span>or</span>
        </div>
        <div class="signup-link">
            Don't have an account?
            <a href="${pageContext.request.contextPath}/signup">Sign up here</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('signinForm');
        const submitBtn = document.getElementById('signinBtn');
        const spinner = document.getElementById('btnSpinner');

        form.addEventListener('submit', function(e) {
            e.preventDefault();
            submitBtn.disabled = true;
            spinner.style.display = 'inline-block';
            submitBtn.querySelector('.btn-text').textContent = 'Signing In...';

            if (!validateForm()) {
                resetButton();
                return;
            }

            const formData = new FormData(form);

            fetch(form.action, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.text())
            .then(text => {
                let data;
                try {
                    data = JSON.parse(text);
                } catch (e) {
                    showError('Server error: Invalid response');
                    resetButton();
                    return;
                }
                if (data.success) {
                    localStorage.setItem('authToken', data.token);
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('userName', data.user.name);
                    localStorage.setItem('userEmail', data.user.email);
                    showSuccess('Sign in successful! Redirecting...');
                    setTimeout(() => {
                        window.location.href = data.redirectUrl || form.dataset.dashboardUrl;
                    }, 1500);
                } else {
                    showError(data.message || 'Sign in failed. Please try again.');
                    resetButton();
                }
            })
            .catch(error => {
                console.error('Sign In Error:', error);
                showError('Sign in failed. Please try again.');
                resetButton();
            });
        });

        function validateForm() {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            if (!username) {
                showError('Please enter your username or email');
                return false;
            }
            if (!password) {
                showError('Please enter your password');
                return false;
            }
            if (password.length < 6) {
                showError('Password must be at least 6 characters long');
                return false;
            }
            return true;
        }

        function resetButton() {
            submitBtn.disabled = false;
            spinner.style.display = 'none';
            submitBtn.querySelector('.btn-text').textContent = 'Sign In';
        }

        function showError(message) {
            removeMessages();
            const errorDiv = document.createElement('div');
            errorDiv.className = 'error-message';
            errorDiv.innerHTML = `
                <i class="bi bi-exclamation-triangle"></i>
                <span>${message}</span>
            `;
            form.insertBefore(errorDiv, form.firstChild);
        }

        function showSuccess(message) {
            removeMessages();
            const successDiv = document.createElement('div');
            successDiv.className = 'success-message';
            successDiv.innerHTML = `
                <i class="bi bi-check-circle"></i>
                <span>${message}</span>
            `;
            form.insertBefore(successDiv, form.firstChild);
        }

        function removeMessages() {
            const existingMessages = form.querySelectorAll('.error-message, .success-message');
            existingMessages.forEach(msg => msg.remove());
        }
    });

    function togglePassword() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.getElementById('passwordToggleIcon');
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.className = 'bi bi-eye-slash';
        } else {
            passwordInput.type = 'password';
            toggleIcon.className = 'bi bi-eye';
        }
    }

    document.getElementById('username').focus();
</script>
</body>
</html>

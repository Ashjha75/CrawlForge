<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found | CrawlForge</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Reset and Base Styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg,
            #1f1f2b 0%,
            #2c2c3a 25%,
            #40414f 50%,
            #2c2c3a 75%,
            #1f1f2b 100%);
            min-height: 100vh;
            overflow: hidden;
            position: relative;
            color: #c5c5d2;
        }

        /* Animated Background */
        .error-background {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 0;
        }

        .floating-particles {
            position: absolute;
            width: 100%;
            height: 100%;
            background:
                    radial-gradient(2px 2px at 20px 30px, rgba(16, 163, 127, 0.3), transparent),
                    radial-gradient(2px 2px at 40px 70px, rgba(16, 163, 127, 0.2), transparent),
                    radial-gradient(1px 1px at 90px 40px, rgba(16, 163, 127, 0.4), transparent),
                    radial-gradient(1px 1px at 130px 80px, rgba(16, 163, 127, 0.2), transparent);
            background-size: 200px 100px;
            animation: float-particles 20s linear infinite;
        }

        @keyframes float-particles {
            0% { transform: translateY(100vh) rotate(0deg); }
            100% { transform: translateY(-100vh) rotate(360deg); }
        }

        .glitch-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background:
                    linear-gradient(90deg, transparent 0%, rgba(16, 163, 127, 0.03) 50%, transparent 100%);
            animation: glitch-sweep 8s ease-in-out infinite;
        }

        @keyframes glitch-sweep {
            0%, 100% { transform: translateX(-100%); }
            50% { transform: translateX(100%); }
        }

        /* Main Container */
        .error-container {
            position: relative;
            z-index: 10;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            padding: 2rem;
            text-align: center;
        }

        /* 404 Number Animation */
        .error-number {
            position: relative;
            margin-bottom: 2rem;
            perspective: 1000px;
        }

        .number-container {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 1rem;
        }

        .digit {
            font-size: clamp(6rem, 15vw, 12rem);
            font-weight: 900;
            line-height: 1;
            position: relative;
            overflow: hidden;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            animation: digit-glow 3s ease-in-out infinite;
        }

        .digit:nth-child(1) {
            animation-delay: 0s;
            transform-origin: center;
            animation: digit-bounce 2s ease-in-out infinite;
        }

        .digit:nth-child(2) {
            animation-delay: 0.5s;
            position: relative;
        }

        .digit:nth-child(3) {
            animation-delay: 1s;
            animation: digit-spin 4s ease-in-out infinite;
        }

        /* Digit Animations */
        @keyframes digit-glow {
            0%, 100% {
                filter: drop-shadow(0 0 10px rgba(16, 163, 127, 0.5));
                transform: scale(1);
            }
            50% {
                filter: drop-shadow(0 0 30px rgba(16, 163, 127, 0.8));
                transform: scale(1.05);
            }
        }

        @keyframes digit-bounce {
            0%, 100% { transform: translateY(0) rotateX(0deg); }
            25% { transform: translateY(-20px) rotateX(10deg); }
            75% { transform: translateY(10px) rotateX(-5deg); }
        }

        @keyframes digit-spin {
            0%, 100% { transform: rotateY(0deg); }
            50% { transform: rotateY(360deg); }
        }

        /* Glitch Effect for Middle Zero */
        .digit.glitch {
            position: relative;
        }

        .digit.glitch::before,
        .digit.glitch::after {
            content: '0';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .digit.glitch::before {
            animation: glitch-1 2s infinite;
            clip-path: polygon(0 0, 100% 0, 100% 45%, 0 45%);
        }

        .digit.glitch::after {
            animation: glitch-2 2s infinite;
            clip-path: polygon(0 55%, 100% 55%, 100% 100%, 0 100%);
        }

        @keyframes glitch-1 {
            0%, 100% { transform: translateX(0); }
            20% { transform: translateX(-2px); }
            40% { transform: translateX(2px); }
            60% { transform: translateX(-1px); }
            80% { transform: translateX(1px); }
        }

        @keyframes glitch-2 {
            0%, 100% { transform: translateX(0); }
            20% { transform: translateX(2px); }
            40% { transform: translateX(-2px); }
            60% { transform: translateX(1px); }
            80% { transform: translateX(-1px); }
        }

        /* Robot Character */
        .robot-container {
            margin: 2rem 0;
            position: relative;
        }

        .robot {
            width: 120px;
            height: 120px;
            margin: 0 auto;
            position: relative;
            animation: robot-float 3s ease-in-out infinite;
        }

        .robot-body {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg,
            rgba(16, 163, 127, 0.2) 0%,
            rgba(64, 65, 79, 0.9) 30%,
            rgba(44, 44, 58, 0.95) 70%,
            rgba(31, 31, 43, 0.9) 100%);
            backdrop-filter: blur(20px);
            border: 2px solid rgba(16, 163, 127, 0.3);
            border-radius: 20px;
            margin: 0 auto;
            position: relative;
            box-shadow:
                    0 8px 32px rgba(0, 0, 0, 0.3),
                    inset 0 1px 0 rgba(255, 255, 255, 0.1);
        }

        .robot-head {
            width: 60px;
            height: 40px;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            border-radius: 15px 15px 8px 8px;
            margin: 0 auto;
            position: relative;
            top: -20px;
            box-shadow: 0 4px 15px rgba(16, 163, 127, 0.4);
        }

        .robot-eyes {
            display: flex;
            justify-content: space-between;
            padding: 8px 12px;
            position: absolute;
            top: 8px;
            width: 100%;
        }

        .robot-eye {
            width: 8px;
            height: 8px;
            background: #ffffff;
            border-radius: 50%;
            animation: robot-blink 4s ease-in-out infinite;
        }

        .robot-antenna {
            position: absolute;
            top: -15px;
            left: 50%;
            transform: translateX(-50%);
            width: 2px;
            height: 15px;
            background: #10a37f;
            border-radius: 1px;
        }

        .robot-antenna::after {
            content: '';
            position: absolute;
            top: -4px;
            left: -2px;
            width: 6px;
            height: 6px;
            background: #10a37f;
            border-radius: 50%;
            animation: antenna-pulse 2s ease-in-out infinite;
        }

        @keyframes robot-float {
            0%, 100% { transform: translateY(0px) rotate(0deg); }
            50% { transform: translateY(-10px) rotate(2deg); }
        }

        @keyframes robot-blink {
            0%, 90%, 100% { transform: scaleY(1); }
            95% { transform: scaleY(0.1); }
        }

        @keyframes antenna-pulse {
            0%, 100% {
                box-shadow: 0 0 5px rgba(16, 163, 127, 0.5);
                transform: scale(1);
            }
            50% {
                box-shadow: 0 0 20px rgba(16, 163, 127, 0.8);
                transform: scale(1.5);
            }
        }

        /* Error Message */
        .error-message {
            margin-bottom: 3rem;
            max-width: 600px;
        }

        .error-title {
            font-size: clamp(2rem, 5vw, 3rem);
            font-weight: 700;
            color: #ffffff;
            margin-bottom: 1rem;
            animation: fadeInUp 1s ease-out 0.5s both;
        }

        .error-subtitle {
            font-size: clamp(1rem, 2.5vw, 1.3rem);
            color: #c5c5d2;
            opacity: 0.9;
            line-height: 1.6;
            margin-bottom: 2rem;
            animation: fadeInUp 1s ease-out 0.7s both;
        }

        /* Interactive Elements */
        .error-actions {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
            animation: fadeInUp 1s ease-out 0.9s both;
        }

        .action-btn {
            padding: 1rem 2rem;
            border-radius: 50px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            min-width: 160px;
            justify-content: center;
            border: none;
            cursor: pointer;
            font-size: 0.95rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3);
        }

        .btn-primary:hover {
            background: linear-gradient(135deg, #0d8465 0%, #0a6b52 100%);
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(16, 163, 127, 0.4);
        }

        .btn-secondary {
            background: transparent;
            color: #c5c5d2;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .btn-secondary:hover {
            background: rgba(255, 255, 255, 0.05);
            color: #ffffff;
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(255, 255, 255, 0.1);
        }

        .btn-glow {
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

        .action-btn:hover .btn-glow {
            left: 100%;
        }

        /* Floating Elements */
        .floating-elements {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            pointer-events: none;
            z-index: 1;
        }

        .floating-icon {
            position: absolute;
            font-size: 2rem;
            color: rgba(16, 163, 127, 0.3);
            animation: float-icon 6s ease-in-out infinite;
        }

        .floating-icon:nth-child(1) {
            top: 20%;
            left: 10%;
            animation-delay: 0s;
        }

        .floating-icon:nth-child(2) {
            top: 60%;
            right: 15%;
            animation-delay: 2s;
        }

        .floating-icon:nth-child(3) {
            bottom: 30%;
            left: 20%;
            animation-delay: 4s;
        }

        .floating-icon:nth-child(4) {
            top: 30%;
            right: 25%;
            animation-delay: 1s;
        }

        @keyframes float-icon {
            0%, 100% {
                transform: translateY(0px) rotate(0deg);
                opacity: 0.3;
            }
            50% {
                transform: translateY(-20px) rotate(180deg);
                opacity: 0.6;
            }
        }

        /* Search Suggestion */
        .search-suggestion {
            margin-top: 2rem;
            animation: fadeInUp 1s ease-out 1.1s both;
        }

        .search-container {
            position: relative;
            max-width: 400px;
            margin: 0 auto;
        }

        .search-input {
            width: 100%;
            background: linear-gradient(135deg,
            rgba(64, 65, 79, 0.6) 0%,
            rgba(44, 44, 58, 0.8) 100%);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(16, 163, 127, 0.2);
            border-radius: 50px;
            padding: 1rem 3rem 1rem 1.5rem;
            color: #ffffff;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        .search-input:focus {
            outline: none;
            border-color: rgba(16, 163, 127, 0.5);
            box-shadow:
                    0 0 0 4px rgba(16, 163, 127, 0.1),
                    0 8px 25px rgba(16, 163, 127, 0.2);
        }

        .search-input::placeholder {
            color: #c5c5d2;
            opacity: 0.6;
        }

        .search-btn {
            position: absolute;
            right: 5px;
            top: 50%;
            transform: translateY(-50%);
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
            border: none;
            border-radius: 50%;
            color: white;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .search-btn:hover {
            transform: translateY(-50%) scale(1.1);
            box-shadow: 0 4px 15px rgba(16, 163, 127, 0.4);
        }

        /* Status Info */
        .status-info {
            position: absolute;
            bottom: 2rem;
            left: 50%;
            transform: translateX(-50%);
            display: flex;
            align-items: center;
            gap: 1rem;
            background: linear-gradient(135deg,
            rgba(64, 65, 79, 0.6) 0%,
            rgba(44, 44, 58, 0.8) 100%);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 50px;
            padding: 0.8rem 1.5rem;
            font-size: 0.9rem;
            color: #c5c5d2;
            animation: fadeInUp 1s ease-out 1.3s both;
        }

        .status-dot {
            width: 8px;
            height: 8px;
            background: #10a37f;
            border-radius: 50%;
            animation: pulse-dot 2s ease-in-out infinite;
        }

        @keyframes pulse-dot {
            0%, 100% {
                transform: scale(1);
                box-shadow: 0 0 5px rgba(16, 163, 127, 0.5);
            }
            50% {
                transform: scale(1.3);
                box-shadow: 0 0 15px rgba(16, 163, 127, 0.8);
            }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .error-container {
                padding: 1rem;
            }

            .number-container {
                gap: 0.5rem;
            }

            .robot {
                width: 80px;
                height: 80px;
            }

            .robot-body {
                width: 60px;
                height: 60px;
            }

            .robot-head {
                width: 45px;
                height: 30px;
            }

            .error-actions {
                flex-direction: column;
                align-items: center;
            }

            .action-btn {
                width: 100%;
                max-width: 250px;
            }

            .status-info {
                bottom: 1rem;
                padding: 0.6rem 1rem;
                font-size: 0.8rem;
            }
        }

        @media (max-width: 480px) {
            .floating-icon {
                font-size: 1.5rem;
            }

            .search-container {
                max-width: 300px;
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

        /* Mouse Interaction */
        .interactive-area {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            pointer-events: none;
            z-index: 5;
        }

        .cursor-glow {
            position: absolute;
            width: 200px;
            height: 200px;
            background: radial-gradient(circle, rgba(16, 163, 127, 0.1) 0%, transparent 70%);
            border-radius: 50%;
            pointer-events: none;
            transform: translate(-50%, -50%);
            transition: all 0.3s ease;
            opacity: 0;
        }
    </style>
</head>
<body>
<!-- Animated Background -->
<div class="error-background">
    <div class="floating-particles"></div>
    <div class="glitch-overlay"></div>
</div>

<!-- Interactive Mouse Area -->
<div class="interactive-area">
    <div class="cursor-glow" id="cursorGlow"></div>
</div>

<!-- Floating Elements -->
<div class="floating-elements">
    <i class="bi bi-robot floating-icon"></i>
    <i class="bi bi-search floating-icon"></i>
    <i class="bi bi-exclamation-triangle floating-icon"></i>
    <i class="bi bi-question-circle floating-icon"></i>
</div>

<!-- Main Error Container -->
<div class="error-container">
    <!-- 404 Number -->
    <div class="error-number">
        <div class="number-container">
            <div class="digit">4</div>
            <div class="digit glitch">0</div>
            <div class="digit">4</div>
        </div>
    </div>

    <!-- Robot Character -->
    <div class="robot-container">
        <div class="robot">
            <div class="robot-head">
                <div class="robot-antenna"></div>
                <div class="robot-eyes">
                    <div class="robot-eye"></div>
                    <div class="robot-eye"></div>
                </div>
            </div>
            <div class="robot-body"></div>
        </div>
    </div>

    <!-- Error Message -->
    <div class="error-message">
        <h1 class="error-title">Oops! Page Not Found</h1>
        <p class="error-subtitle">
            Our crawling robot couldn't find the page you're looking for.
            It might have been moved, deleted, or the URL might be incorrect.
        </p>
    </div>

    <!-- Action Buttons -->
    <div class="error-actions">
        <a href="${pageContext.request.contextPath}/" class="action-btn btn-primary">
            <div class="btn-glow"></div>
            <i class="bi bi-house"></i>
            <span>Go Home</span>
        </a>
        <button onclick="history.back()" class="action-btn btn-secondary">
            <div class="btn-glow"></div>
            <i class="bi bi-arrow-left"></i>
            <span>Go Back</span>
        </button>
    </div>

    <!-- Search Suggestion -->
    <div class="search-suggestion">
        <div class="search-container">
            <input type="text" class="search-input" placeholder="Search for something else..." id="searchInput">
            <button class="search-btn" onclick="performSearch()">
                <i class="bi bi-search"></i>
            </button>
        </div>
    </div>
</div>

<!-- Status Info -->
<div class="status-info">
    <div class="status-dot"></div>
    <span>CrawlForge Systems Operational</span>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Mouse tracking for cursor glow
        const cursorGlow = document.getElementById('cursorGlow');
        let mouseX = 0, mouseY = 0;

        document.addEventListener('mousemove', function(e) {
            mouseX = e.clientX;
            mouseY = e.clientY;

            cursorGlow.style.left = mouseX + 'px';
            cursorGlow.style.top = mouseY + 'px';
            cursorGlow.style.opacity = '1';
        });

        document.addEventListener('mouseleave', function() {
            cursorGlow.style.opacity = '0';
        });

        // Interactive robot
        const robot = document.querySelector('.robot');
        robot.addEventListener('click', function() {
            this.style.animation = 'none';
            this.style.transform = 'scale(1.2) rotate(360deg)';

            setTimeout(() => {
                this.style.animation = 'robot-float 3s ease-in-out infinite';
                this.style.transform = '';
            }, 1000);
        });

        // Glitch effect trigger
        const glitchDigit = document.querySelector('.digit.glitch');
        setInterval(() => {
            glitchDigit.style.animation = 'none';
            setTimeout(() => {
                glitchDigit.style.animation = 'digit-glow 3s ease-in-out infinite';
            }, 100);
        }, 5000);

        // Search functionality
        window.performSearch = function() {
            const searchInput = document.getElementById('searchInput');
            const query = searchInput.value.trim();

            if (query) {
                // Add loading animation
                const searchBtn = document.querySelector('.search-btn');
                searchBtn.innerHTML = '<i class="bi bi-hourglass"></i>';

                setTimeout(() => {
                    // Redirect to search or home with query
                    window.location.href = '${pageContext.request.contextPath}/?search=' + encodeURIComponent(query);
                    }, 1000);
                }
            };

            // Enter key for search
            document.getElementById('searchInput').addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    performSearch();
                }
            });

            // Floating elements interaction
            document.querySelectorAll('.floating-icon').forEach(icon => {
                icon.addEventListener('mouseenter', function() {
                    this.style.transform = 'scale(1.5) rotate(180deg)';
                    this.style.color = 'rgba(16, 163, 127, 0.8)';
                });

                icon.addEventListener('mouseleave', function() {
                    this.style.transform = '';
                    this.style.color = 'rgba(16, 163, 127, 0.3)';
                });
            });

            // Button click effects
            document.querySelectorAll('.action-btn').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    // Create ripple effect
                    const ripple = document.createElement('div');
                    ripple.style.cssText = `
                        position: absolute;
                        border-radius: 50%;
                        background: rgba(255, 255, 255, 0.3);
                        transform: scale(0);
                        animation: ripple 0.6s linear;
                        pointer-events: none;
                    `;

                    const rect = this.getBoundingClientRect();
                    const size = Math.max(rect.width, rect.height);
                    ripple.style.width = ripple.style.height = size + 'px';
                    ripple.style.left = (e.clientX - rect.left - size / 2) + 'px';
                    ripple.style.top = (e.clientY - rect.top - size / 2) + 'px';

                    this.appendChild(ripple);

                    setTimeout(() => {
                        ripple.remove();
                    }, 600);
                });
            });

            // Add ripple animation
            const style = document.createElement('style');
            style.textContent = `
                @keyframes ripple {
                    to {
                        transform: scale(4);
                        opacity: 0;
                    }
                }
            `;
            document.head.appendChild(style);

            // Random glitch effects
            setInterval(() => {
                const elements = document.querySelectorAll('.digit, .robot-head');
                const randomElement = elements[Math.floor(Math.random() * elements.length)];

                randomElement.style.filter = 'hue-rotate(90deg) brightness(1.2)';
                setTimeout(() => {
                    randomElement.style.filter = '';
                }, 200);
            }, 8000);

            // Console easter egg
            console.log(`
            🤖 CrawlForge 404 Error Page
            ═══════════════════════════════

            Looks like our crawler got lost!

            Try these debugging commands:
            • window.location.href = '${pageContext.request.contextPath}/'
            • history.back()
            • location.reload()

            Happy crawling! 🕷️
            `);
        });
</script>
</body>
</html>

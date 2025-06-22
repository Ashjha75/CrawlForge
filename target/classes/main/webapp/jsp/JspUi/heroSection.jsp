<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/heroSection.css">

<!-- Hero Section with Advanced Animations -->
<section class="hero-section" id="heroSection">
    <div class="hero-background">
        <div class="floating-particles"></div>
        <div class="neural-network">
            <canvas id="networkCanvas"></canvas>
        </div>
    </div>

    <div class="container">
        <div class="hero-content">
            <!-- Dynamic Title with Typewriter Effect -->
            <div class="hero-header">
                <h1 class="hero-title">
                    <span class="title-line">
                        <span class="word" data-text="Advanced">Advanced</span>
                        <span class="word" data-text="Web">Web</span>
                    </span>
                    <span class="title-line">
                        <span class="word highlight" data-text="Scraping">Scraping</span>
                        <span class="word" data-text="Platform">Platform</span>
                    </span>
                </h1>
                <div class="subtitle-container">
                    <p class="hero-subtitle" id="typewriterText"></p>
                    <div class="cursor-blink">|</div>
                </div>
            </div>

            <!-- Scrolling Data Cards Stack -->
            <div class="data-cards-container">
                <div class="cards-stack" id="cardsStack">

                    <!-- Real-time Analytics Card -->
                    <div class="data-card active" data-index="0" style="height: fit-content;">
                        <div class="card-header">
                            <div class="card-category">Live Analytics</div>
                            <div class="card-status">
                                <div class="status-dot pulsing"></div>
                                <span>Active</span>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="metric-grid">
                                <div class="metric-item">
                                    <div class="metric-value" data-target="15420">0</div>
                                    <div class="metric-label">Sites Crawled</div>
                                    <div class="metric-trend up">+12.5%</div>
                                </div>
                                <div class="metric-item">
                                    <div class="metric-value" data-target="2.8">0</div>
                                    <div class="metric-suffix">M</div>
                                    <div class="metric-label">Data Points</div>
                                    <div class="metric-trend up">+8.3%</div>
                                </div>
                            </div>
                            <div class="performance-chart">
                                <canvas id="performanceChart" width="300" height="120"></canvas>
                            </div>
                        </div>
                    </div>

                    <!-- AI Processing Card -->
                    <div class="data-card" data-index="1" style="height: fit-content;">
                        <div class="card-header">
                            <div class="card-category">AI Processing</div>
                            <div class="ai-indicator">
                                <div class="ai-brain">🧠</div>
                                <span>Learning</span>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="ai-stats">
                                <div class="ai-accuracy">
                                    <div class="accuracy-ring">
                                        <svg viewBox="0 0 100 100">
                                            <circle cx="50" cy="50" r="45" class="ring-bg"></circle>
                                            <circle cx="50" cy="50" r="45" class="ring-progress"
                                                    data-progress="94.7"></circle>
                                        </svg>
                                        <div class="accuracy-text">
                                            <span class="accuracy-value">94.7%</span>
                                            <span class="accuracy-label">Accuracy</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="ai-features">
                                    <div class="feature-item">
                                        <i class="bi bi-eye"></i>
                                        <span>Smart Detection</span>
                                    </div>
                                    <div class="feature-item">
                                        <i class="bi bi-shield-check"></i>
                                        <span>Anti-Bot Protection</span>
                                    </div>
                                    <div class="feature-item">
                                        <i class="bi bi-lightning"></i>
                                        <span>Auto Optimization</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Global Network Card -->
                    <div class="data-card" data-index="2" style="height: fit-content;">
                        <div class="card-header">
                            <div class="card-category">Global Network</div>
                            <div class="network-status">
                                <div class="signal-bars">
                                    <div class="bar"></div>
                                    <div class="bar"></div>
                                    <div class="bar"></div>
                                    <div class="bar"></div>
                                </div>
                                <span>Strong</span>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="world-map">
                                <div class="map-container">
                                    <div class="connection-point" style="top: 30%; left: 20%;"
                                         data-location="USA"></div>
                                    <div class="connection-point" style="top: 25%; left: 50%;" data-location="EU"></div>
                                    <div class="connection-point" style="top: 45%; left: 75%;"
                                         data-location="ASIA"></div>
                                    <div class="connection-point" style="top: 60%; left: 15%;" data-location="SA"></div>
                                    <svg class="connection-lines">
                                        <line x1="20%" y1="30%" x2="50%" y2="25%" class="connection-line"></line>
                                        <line x1="50%" y1="25%" x2="75%" y2="45%" class="connection-line"></line>
                                        <line x1="20%" y1="30%" x2="15%" y2="60%" class="connection-line"></line>
                                    </svg>
                                </div>
                            </div>
                            <div class="network-stats">
                                <div class="stat">
                                    <span class="stat-value">45</span>
                                    <span class="stat-label">Servers</span>
                                </div>
                                <div class="stat">
                                    <span class="stat-value">12ms</span>
                                    <span class="stat-label">Latency</span>
                                </div>
                                <div class="stat">
                                    <span class="stat-value">99.9%</span>
                                    <span class="stat-label">Uptime</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Live Terminal Card -->
                    <div class="data-card" data-index="3" style="height: fit-content;">
                        <div class="card-header">
                            <div class="card-category">Live Terminal</div>
                            <div class="terminal-controls">
                                <div class="control-dot red"></div>
                                <div class="control-dot yellow"></div>
                                <div class="control-dot green"></div>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="terminal-window">
                                <div class="terminal-output" id="terminalOutput">
                                    <!-- Dynamic logs will be added here -->
                                </div>
                                <div class="terminal-input">
                                    <span class="prompt">crawler@system:~$</span>
                                    <span class="typing-cursor">_</span>
                                </div>
                            </div>
                            <div class="terminal-metrics">
                                <div class="metric">
                                    <i class="bi bi-speedometer2"></i>
                                    <span class="metric-value" data-target="1847">0</span>
                                    <span class="metric-unit">req/min</span>
                                </div>
                                <div class="metric">
                                    <i class="bi bi-cpu"></i>
                                    <span class="metric-value">23%</span>
                                    <span class="metric-unit">CPU</span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- Card Navigation -->
                <div class="card-navigation">
                    <div class="nav-dots">
                        <div class="nav-dot active" data-index="0"></div>
                        <div class="nav-dot" data-index="1"></div>
                        <div class="nav-dot" data-index="2"></div>
                        <div class="nav-dot" data-index="3"></div>
                    </div>
                    <%--                    <div class="scroll-indicator">--%>
                    <%--                        <div class="scroll-text">Scroll to explore</div>--%>
                    <%--                        <div class="scroll-arrow">↓</div>--%>
                    <%--                    </div>--%>
                </div>
            </div>

            <!-- Enhanced CTA Section -->
            <div class="cta-section">
                <button class="cta-button-advanced" id="ctaButton" onclick="window.location.href='/crawler-link';">
                    <div class="button-bg"></div>
                    <div class="button-content">
                        <span class="button-text">Start Crawling</span>
                        <div class="button-icon">
                            <i class="bi bi-rocket-takeoff"></i>
                        </div>
                    </div>
                    <div class="button-particles"></div>
                </button>
                <p class="cta-description">Join 10,000+ developers • No credit card required</p>
            </div>

        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/heroSection.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js"></script>
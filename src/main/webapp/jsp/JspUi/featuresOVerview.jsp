<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/featuresOVerview.css">

<!-- Features Overview Section -->
<section class="features-section" id="featuresOverview">
    <div class="container">
        <!-- Section Header -->
        <div class="section-header">
            <div class="features-badge">
                <i class="bi bi-cpu"></i>
                <span>Powered by Advanced AI</span>
            </div>
            <h2 class="section-title">
                <span class="title-word" data-text="Intelligent">Intelligent</span>
                <span class="title-word highlight" data-text="Web">Web</span>
                <span class="title-word" data-text="Scraping">Scraping</span>
            </h2>
            <p class="section-subtitle">
                Extract data from any website with precision, scale, and intelligence.
                Our AI-powered platform handles the complexity while you focus on insights.
            </p>
        </div>

        <!-- Features Grid -->
        <div class="features-grid">
            <!-- AI-Powered Parsing -->
            <div class="feature-card" data-feature="ai-parsing">
                <div class="feature-glow"></div>
                <div class="feature-content">
                    <div class="feature-header">
                        <div class="feature-icon ai-icon">
                            <i class="bi bi-robot"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-badge">AI-Powered</div>
                    </div>

                    <h3 class="feature-title">Smart Data Parsing</h3>
                    <p class="feature-description">
                        Our AI understands complex website structures and extracts exactly what you need
                        with 99.7% accuracy, even from dynamic content.
                    </p>

                    <div class="feature-demo">
                        <div class="demo-container">
                            <div class="demo-input">
                                <div class="demo-label">Raw HTML</div>
                                <div class="demo-code">
                                    <span class="code-tag">&lt;div class="product"&gt;</span>
                                    <span class="code-content">iPhone 15 Pro - $999</span>
                                    <span class="code-tag">&lt;/div&gt;</span>
                                </div>
                            </div>
                            <div class="demo-arrow">
                                <i class="bi bi-arrow-right"></i>
                            </div>
                            <div class="demo-output">
                                <div class="demo-label">Structured Data</div>
                                <div class="demo-json">
                                    <span class="json-key">"product":</span> <span class="json-string">"iPhone 15 Pro"</span><br>
                                    <span class="json-key">"price":</span> <span class="json-number">999</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="feature-stats">
                        <div class="stat-item">
                            <div class="stat-value">99.7%</div>
                            <div class="stat-label">Accuracy</div>
                        </div>
                        <div class="stat-item">
                            <div class="stat-value">50ms</div>
                            <div class="stat-label">Avg Response</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Real-time Monitoring -->
            <div class="feature-card" data-feature="monitoring">
                <div class="feature-glow"></div>
                <div class="feature-content">
                    <div class="feature-header">
                        <div class="feature-icon monitoring-icon">
                            <i class="bi bi-activity"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-badge">Real-time</div>
                    </div>

                    <h3 class="feature-title">Live Monitoring</h3>
                    <p class="feature-description">
                        Monitor your scraping operations in real-time with detailed analytics,
                        alerts, and performance metrics across all your crawlers.
                    </p>

                    <div class="feature-demo">
                        <div class="monitoring-dashboard">
                            <div class="dashboard-header">
                                <div class="dashboard-title">Live Dashboard</div>
                                <div class="dashboard-status">
                                    <div class="status-dot active"></div>
                                    <span>All Systems Operational</span>
                                </div>
                            </div>

                            <div class="metrics-grid">
                                <div class="metric-card">
                                    <div class="metric-icon"><i class="bi bi-speedometer2"></i></div>
                                    <div class="metric-value" data-target="1847">0</div>
                                    <div class="metric-label">Req/Min</div>
                                    <div class="metric-chart">
                                        <div class="chart-bar" style="height: 60%"></div>
                                        <div class="chart-bar" style="height: 80%"></div>
                                        <div class="chart-bar" style="height: 45%"></div>
                                        <div class="chart-bar" style="height: 90%"></div>
                                        <div class="chart-bar" style="height: 70%"></div>
                                    </div>
                                </div>

                                <div class="metric-card">
                                    <div class="metric-icon"><i class="bi bi-check-circle"></i></div>
                                    <div class="metric-value" data-target="98.9">0</div>
                                    <div class="metric-suffix">%</div>
                                    <div class="metric-label">Success Rate</div>
                                    <div class="success-indicator"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="feature-highlights">
                        <div class="highlight-item">
                            <i class="bi bi-bell"></i>
                            <span>Instant Alerts</span>
                        </div>
                        <div class="highlight-item">
                            <i class="bi bi-graph-up"></i>
                            <span>Performance Analytics</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Scale & Performance -->
            <div class="feature-card" data-feature="scale">
                <div class="feature-glow"></div>
                <div class="feature-content">
                    <div class="feature-header">
                        <div class="feature-icon scale-icon">
                            <i class="bi bi-layers"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-badge">Enterprise</div>
                    </div>

                    <h3 class="feature-title">Massive Scale</h3>
                    <p class="feature-description">
                        From single pages to millions of URLs. Our distributed infrastructure
                        scales automatically to handle any workload with consistent performance.
                    </p>

                    <div class="feature-demo">
                        <div class="scale-visualization">
                            <div class="scale-header">
                                <div class="scale-title">Global Infrastructure</div>
                                <div class="scale-count">
                                    <span class="count-number" data-target="45">0</span>
                                    <span class="count-label">Data Centers</span>
                                </div>
                            </div>

                            <div class="world-network">
                                <div class="network-node" style="top: 20%; left: 15%;" data-region="US-West">
                                    <div class="node-dot"></div>
                                    <div class="node-label">US-West</div>
                                </div>
                                <div class="network-node" style="top: 25%; left: 25%;" data-region="US-East">
                                    <div class="node-dot"></div>
                                    <div class="node-label">US-East</div>
                                </div>
                                <div class="network-node" style="top: 30%; left: 50%;" data-region="EU">
                                    <div class="node-dot"></div>
                                    <div class="node-label">Europe</div>
                                </div>
                                <div class="network-node" style="top: 40%; left: 75%;" data-region="ASIA">
                                    <div class="node-dot"></div>
                                    <div class="node-label">Asia</div>
                                </div>
                                <div class="network-node" style="top: 60%; left: 80%;" data-region="APAC">
                                    <div class="node-dot"></div>
                                    <div class="node-label">APAC</div>
                                </div>

                                <svg class="network-connections">
                                    <line x1="15%" y1="20%" x2="25%" y2="25%" class="connection-line"></line>
                                    <line x1="25%" y1="25%" x2="50%" y2="30%" class="connection-line"></line>
                                    <line x1="50%" y1="30%" x2="75%" y2="40%" class="connection-line"></line>
                                    <line x1="75%" y1="40%" x2="80%" y2="60%" class="connection-line"></line>
                                </svg>
                            </div>

                            <div class="scale-metrics">
                                <div class="scale-metric">
                                    <div class="metric-value" data-target="10">0</div>
                                    <div class="metric-suffix">M+</div>
                                    <div class="metric-label">Pages/Day</div>
                                </div>
                                <div class="scale-metric">
                                    <div class="metric-value" data-target="99.9">0</div>
                                    <div class="metric-suffix">%</div>
                                    <div class="metric-label">Uptime</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- API Integration -->
            <div class="feature-card" data-feature="api">
                <div class="feature-glow"></div>
                <div class="feature-content">
                    <div class="feature-header">
                        <div class="feature-icon api-icon">
                            <i class="bi bi-code-slash"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-badge">Developer-First</div>
                    </div>

                    <h3 class="feature-title">Powerful APIs</h3>
                    <p class="feature-description">
                        RESTful APIs, webhooks, and SDKs in multiple languages.
                        Integrate CrawlForge into your existing workflow in minutes.
                    </p>

                    <div class="feature-demo">
                        <div class="api-demo">
                            <div class="api-tabs">
                                <div class="api-tab active" data-lang="curl">cURL</div>
                                <div class="api-tab" data-lang="python">Python</div>
                                <div class="api-tab" data-lang="javascript">Node.js</div>
                            </div>

                            <div class="api-code-block">
                                <div class="code-header">
                                    <div class="code-title">Start Scraping</div>
                                    <div class="code-copy">
                                        <i class="bi bi-clipboard"></i>
                                    </div>
                                </div>

                                <div class="code-content" id="curlCode">
                                    <span class="code-command">curl</span> <span class="code-flag">-X POST</span> <span class="code-url">"https://api.crawlforge.com/v1/scrape"</span><br>
                                    <span class="code-flag">-H</span> <span class="code-string">"Authorization: Bearer YOUR_API_KEY"</span><br>
                                    <span class="code-flag">-d</span> <span class="code-json">'{"url": "https://example.com", "format": "json"}'</span>
                                </div>

                                <div class="code-content hidden" id="pythonCode">
                                    <span class="code-keyword">import</span> <span class="code-module">crawlforge</span><br><br>
                                    <span class="code-variable">client</span> = <span class="code-module">crawlforge</span>.<span class="code-function">Client</span>(<span class="code-string">"YOUR_API_KEY"</span>)<br>
                                    <span class="code-variable">result</span> = <span class="code-variable">client</span>.<span class="code-function">scrape</span>(<span class="code-string">"https://example.com"</span>)
                                </div>

                                <div class="code-content hidden" id="javascriptCode">
                                    <span class="code-keyword">const</span> <span class="code-variable">crawlforge</span> = <span class="code-function">require</span>(<span class="code-string">'crawlforge'</span>);<br><br>
                                    <span class="code-keyword">const</span> <span class="code-variable">client</span> = <span class="code-keyword">new</span> <span class="code-function">CrawlForge</span>(<span class="code-string">'YOUR_API_KEY'</span>);<br>
                                    <span class="code-keyword">const</span> <span class="code-variable">data</span> = <span class="code-keyword">await</span> <span class="code-variable">client</span>.<span class="code-function">scrape</span>(<span class="code-string">'https://example.com'</span>);
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="feature-highlights">
                        <div class="highlight-item">
                            <i class="bi bi-shield-check"></i>
                            <span>Rate Limiting</span>
                        </div>
                        <div class="highlight-item">
                            <i class="bi bi-webhook"></i>
                            <span>Webhooks</span>
                        </div>
                        <div class="highlight-item">
                            <i class="bi bi-file-earmark-code"></i>
                            <span>SDKs Available</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Feature Comparison -->
        <div class="feature-comparison">
            <div class="comparison-header">
                <h3 class="comparison-title">Why Choose CrawlForge?</h3>
                <p class="comparison-subtitle">See how we compare to traditional scraping solutions</p>
            </div>

            <div class="comparison-table">
                <div class="comparison-row header-row">
                    <div class="comparison-cell feature-name">Feature</div>
                    <div class="comparison-cell traditional">Traditional Tools</div>
                    <div class="comparison-cell crawlforge">CrawlForge</div>
                </div>

                <div class="comparison-row">
                    <div class="comparison-cell feature-name">AI-Powered Parsing</div>
                    <div class="comparison-cell traditional">
                        <i class="bi bi-x-circle text-red"></i>
                        <span>Manual Setup</span>
                    </div>
                    <div class="comparison-cell crawlforge">
                        <i class="bi bi-check-circle text-green"></i>
                        <span>Automatic</span>
                    </div>
                </div>

                <div class="comparison-row">
                    <div class="comparison-cell feature-name">Scale Handling</div>
                    <div class="comparison-cell traditional">
                        <i class="bi bi-x-circle text-red"></i>
                        <span>Limited</span>
                    </div>
                    <div class="comparison-cell crawlforge">
                        <i class="bi bi-check-circle text-green"></i>
                        <span>Unlimited</span>
                    </div>
                </div>

                <div class="comparison-row">
                    <div class="comparison-cell feature-name">Anti-Bot Detection</div>
                    <div class="comparison-cell traditional">
                        <i class="bi bi-exclamation-triangle text-yellow"></i>
                        <span>Basic</span>
                    </div>
                    <div class="comparison-cell crawlforge">
                        <i class="bi bi-check-circle text-green"></i>
                        <span>Advanced AI</span>
                    </div>
                </div>

                <div class="comparison-row">
                    <div class="comparison-cell feature-name">Real-time Monitoring</div>
                    <div class="comparison-cell traditional">
                        <i class="bi bi-x-circle text-red"></i>
                        <span>None</span>
                    </div>
                    <div class="comparison-cell crawlforge">
                        <i class="bi bi-check-circle text-green"></i>
                        <span>Full Dashboard</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- CTA Section -->
        <div class="features-cta">
            <div class="cta-content">
                <h3 class="cta-title">Ready to Experience the Difference?</h3>
                <p class="cta-description">Start your free trial today and see why 10,000+ developers trust CrawlForge</p>

                <div class="cta-buttons">
                    <a href="${pageContext.request.contextPath}/signup" class="cta-btn primary">
                        <div class="btn-glow"></div>
                        <span class="btn-content">
                            <i class="bi bi-rocket-takeoff"></i>
                            Start Free Trial
                        </span>
                    </a>

                    <a href="${pageContext.request.contextPath}/demo" class="cta-btn secondary">
                        <span class="btn-content">
                            <i class="bi bi-play-circle"></i>
                            Watch Demo
                        </span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>

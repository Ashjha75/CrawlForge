<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Reports Section -->
<section class="reports-section">
    <div class="container">
        <!-- Reports Header -->
        <div class="reports-header">
            <div class="header-content">
                <div class="header-info">
                    <h2 class="reports-title">
                        <i class="bi bi-bar-chart-line"></i>
                        Analytics & Reports
                    </h2>
                    <p class="reports-subtitle">Comprehensive insights into your web scraping operations</p>
                </div>
                <div class="header-actions">
                    <div class="date-range-picker">
                        <button class="date-btn active" data-range="7">7 Days</button>
                        <button class="date-btn" data-range="30">30 Days</button>
                        <button class="date-btn" data-range="90">90 Days</button>
                        <button class="date-btn" data-range="custom">Custom</button>
                    </div>
                    <button class="export-report-btn">
                        <i class="bi bi-download"></i>
                        <span>Export Report</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- KPI Cards -->
        <div class="kpi-grid">
            <div class="kpi-card">
                <div class="kpi-icon success">
                    <i class="bi bi-check-circle"></i>
                </div>
                <div class="kpi-content">
                    <div class="kpi-value" data-target="1247">0</div>
                    <div class="kpi-label">Crawls Completed</div>
                    <div class="kpi-trend up">
                        <i class="bi bi-arrow-up"></i>
                        <span>+12.5%</span>
                    </div>
                </div>
                <div class="kpi-chart">
                    <canvas id="crawlsChart" width="60" height="30"></canvas>
                </div>
            </div>

            <div class="kpi-card">
                <div class="kpi-icon primary">
                    <i class="bi bi-graph-up"></i>
                </div>
                <div class="kpi-content">
                    <div class="kpi-value" data-target="98.7">0</div>
                    <div class="kpi-suffix">%</div>
                    <div class="kpi-label">Success Rate</div>
                    <div class="kpi-trend up">
                        <i class="bi bi-arrow-up"></i>
                        <span>+2.1%</span>
                    </div>
                </div>
                <div class="kpi-chart">
                    <canvas id="successChart" width="60" height="30"></canvas>
                </div>
            </div>

            <div class="kpi-card">
                <div class="kpi-icon data">
                    <i class="bi bi-database"></i>
                </div>
                <div class="kpi-content">
                    <div class="kpi-value" data-target="2.8">0</div>
                    <div class="kpi-suffix">M</div>
                    <div class="kpi-label">Data Points</div>
                    <div class="kpi-trend up">
                        <i class="bi bi-arrow-up"></i>
                        <span>+18.3%</span>
                    </div>
                </div>
                <div class="kpi-chart">
                    <canvas id="dataChart" width="60" height="30"></canvas>
                </div>
            </div>

            <div class="kpi-card">
                <div class="kpi-icon performance">
                    <i class="bi bi-speedometer2"></i>
                </div>
                <div class="kpi-content">
                    <div class="kpi-value" data-target="1847">0</div>
                    <div class="kpi-label">Pages/Min</div>
                    <div class="kpi-trend down">
                        <i class="bi bi-arrow-down"></i>
                        <span>-3.2%</span>
                    </div>
                </div>
                <div class="kpi-chart">
                    <canvas id="performanceChart" width="60" height="30"></canvas>
                </div>
            </div>
        </div>

        <!-- Main Charts Grid -->
        <div class="charts-grid">
            <!-- Crawl Activity Chart -->
            <div class="chart-card large">
                <div class="chart-header">
                    <h3 class="chart-title">
                        <i class="bi bi-activity"></i>
                        Crawl Activity
                    </h3>
                    <div class="chart-controls">
                        <button class="chart-toggle active" data-metric="crawls">Crawls</button>
                        <button class="chart-toggle" data-metric="pages">Pages</button>
                        <button class="chart-toggle" data-metric="data">Data</button>
                    </div>
                </div>
                <div class="chart-container">
                    <canvas id="activityChart"></canvas>
                </div>
            </div>

            <!-- Success Rate Breakdown -->
            <div class="chart-card">
                <div class="chart-header">
                    <h3 class="chart-title">
                        <i class="bi bi-pie-chart"></i>
                        Success Breakdown
                    </h3>
                </div>
                <div class="chart-container">
                    <canvas id="successBreakdownChart"></canvas>
                </div>
                <div class="chart-legend">
                    <div class="legend-item">
                        <div class="legend-color success"></div>
                        <span>Completed (87%)</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color warning"></div>
                        <span>Partial (8%)</span>
                    </div>
                    <div class="legend-item">
                        <div class="legend-color error"></div>
                        <span>Failed (5%)</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Performance & Analytics Grid -->
        <div class="analytics-grid">
            <!-- Top Domains -->
            <div class="analytics-card">
                <div class="card-header">
                    <h3 class="card-title">
                        <i class="bi bi-globe"></i>
                        Top Crawled Domains
                    </h3>
                </div>
                <div class="domains-list">
                    <div class="domain-item">
                        <div class="domain-info">
                            <div class="domain-name">example.com</div>
                            <div class="domain-stats">1,247 crawls • 98.5% success</div>
                        </div>
                        <div class="domain-progress">
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: 85%"></div>
                            </div>
                            <span class="progress-value">85%</span>
                        </div>
                    </div>
                    <div class="domain-item">
                        <div class="domain-info">
                            <div class="domain-name">news-site.com</div>
                            <div class="domain-stats">892 crawls • 96.2% success</div>
                        </div>
                        <div class="domain-progress">
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: 72%"></div>
                            </div>
                            <span class="progress-value">72%</span>
                        </div>
                    </div>
                    <div class="domain-item">
                        <div class="domain-info">
                            <div class="domain-name">ecommerce.com</div>
                            <div class="domain-stats">634 crawls • 94.8% success</div>
                        </div>
                        <div class="domain-progress">
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: 58%"></div>
                            </div>
                            <span class="progress-value">58%</span>
                        </div>
                    </div>
                    <div class="domain-item">
                        <div class="domain-info">
                            <div class="domain-name">blog-platform.com</div>
                            <div class="domain-stats">421 crawls • 92.1% success</div>
                        </div>
                        <div class="domain-progress">
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: 45%"></div>
                            </div>
                            <span class="progress-value">45%</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Error Analysis -->
            <div class="analytics-card">
                <div class="card-header">
                    <h3 class="card-title">
                        <i class="bi bi-exclamation-triangle"></i>
                        Error Analysis
                    </h3>
                </div>
                <div class="error-list">
                    <div class="error-item">
                        <div class="error-type">
                            <i class="bi bi-clock"></i>
                            <span>Timeout Errors</span>
                        </div>
                        <div class="error-count">23</div>
                        <div class="error-percentage">45%</div>
                    </div>
                    <div class="error-item">
                        <div class="error-type">
                            <i class="bi bi-shield-x"></i>
                            <span>Bot Detection</span>
                        </div>
                        <div class="error-count">18</div>
                        <div class="error-percentage">35%</div>
                    </div>
                    <div class="error-item">
                        <div class="error-type">
                            <i class="bi bi-wifi-off"></i>
                            <span>Connection Failed</span>
                        </div>
                        <div class="error-count">7</div>
                        <div class="error-percentage">14%</div>
                    </div>
                    <div class="error-item">
                        <div class="error-type">
                            <i class="bi bi-file-x"></i>
                            <span>Parse Errors</span>
                        </div>
                        <div class="error-count">3</div>
                        <div class="error-percentage">6%</div>
                    </div>
                </div>
            </div>

            <!-- Resource Usage -->
            <div class="analytics-card">
                <div class="card-header">
                    <h3 class="card-title">
                        <i class="bi bi-cpu"></i>
                        Resource Usage
                    </h3>
                </div>
                <div class="resource-metrics">
                    <div class="resource-item">
                        <div class="resource-label">CPU Usage</div>
                        <div class="resource-gauge">
                            <div class="gauge-bg">
                                <div class="gauge-fill" data-percentage="67"></div>
                            </div>
                            <span class="gauge-value">67%</span>
                        </div>
                    </div>
                    <div class="resource-item">
                        <div class="resource-label">Memory</div>
                        <div class="resource-gauge">
                            <div class="gauge-bg">
                                <div class="gauge-fill" data-percentage="45"></div>
                            </div>
                            <span class="gauge-value">45%</span>
                        </div>
                    </div>
                    <div class="resource-item">
                        <div class="resource-label">Storage</div>
                        <div class="resource-gauge">
                            <div class="gauge-bg">
                                <div class="gauge-fill" data-percentage="82"></div>
                            </div>
                            <span class="gauge-value">82%</span>
                        </div>
                    </div>
                    <div class="resource-item">
                        <div class="resource-label">Bandwidth</div>
                        <div class="resource-gauge">
                            <div class="gauge-bg">
                                <div class="gauge-fill" data-percentage="34"></div>
                            </div>
                            <span class="gauge-value">34%</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Geographic Distribution -->
            <div class="analytics-card">
                <div class="card-header">
                    <h3 class="card-title">
                        <i class="bi bi-geo-alt"></i>
                        Geographic Distribution
                    </h3>
                </div>
                <div class="geo-stats">
                    <div class="geo-item">
                        <div class="geo-region">
                            <i class="bi bi-flag"></i>
                            <span>North America</span>
                        </div>
                        <div class="geo-metrics">
                            <div class="geo-count">1,247</div>
                            <div class="geo-latency">12ms avg</div>
                        </div>
                    </div>
                    <div class="geo-item">
                        <div class="geo-region">
                            <i class="bi bi-flag"></i>
                            <span>Europe</span>
                        </div>
                        <div class="geo-metrics">
                            <div class="geo-count">892</div>
                            <div class="geo-latency">18ms avg</div>
                        </div>
                    </div>
                    <div class="geo-item">
                        <div class="geo-region">
                            <i class="bi bi-flag"></i>
                            <span>Asia Pacific</span>
                        </div>
                        <div class="geo-metrics">
                            <div class="geo-count">634</div>
                            <div class="geo-latency">25ms avg</div>
                        </div>
                    </div>
                    <div class="geo-item">
                        <div class="geo-region">
                            <i class="bi bi-flag"></i>
                            <span>Other</span>
                        </div>
                        <div class="geo-metrics">
                            <div class="geo-count">189</div>
                            <div class="geo-latency">31ms avg</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Real-time Activity Feed -->
        <div class="activity-feed-card">
            <div class="feed-header">
                <h3 class="feed-title">
                    <i class="bi bi-activity"></i>
                    Real-time Activity
                </h3>
                <div class="feed-status">
                    <div class="status-dot active"></div>
                    <span>Live</span>
                </div>
            </div>
            <div class="activity-feed" id="activityFeed">
                <div class="activity-item">
                    <div class="activity-icon success">
                        <i class="bi bi-check-circle"></i>
                    </div>
                    <div class="activity-content">
                        <div class="activity-text">
                            <strong>example.com</strong> crawl completed successfully
                        </div>
                        <div class="activity-meta">1,247 pages • 2m 34s • Just now</div>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon processing">
                        <i class="bi bi-arrow-clockwise"></i>
                    </div>
                    <div class="activity-content">
                        <div class="activity-text">
                            <strong>news-site.com</strong> crawl in progress
                        </div>
                        <div class="activity-meta">89 pages • 45s elapsed • 2 min ago</div>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon warning">
                        <i class="bi bi-exclamation-triangle"></i>
                    </div>
                    <div class="activity-content">
                        <div class="activity-text">
                            <strong>ecommerce.com</strong> rate limit detected
                        </div>
                        <div class="activity-meta">Adjusting crawl speed • 5 min ago</div>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon error">
                        <i class="bi bi-x-circle"></i>
                    </div>
                    <div class="activity-content">
                        <div class="activity-text">
                            <strong>blog-platform.com</strong> crawl failed
                        </div>
                        <div class="activity-meta">Connection timeout • 8 min ago</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

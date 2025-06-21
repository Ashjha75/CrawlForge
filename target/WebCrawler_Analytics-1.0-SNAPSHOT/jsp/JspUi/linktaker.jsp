<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/linkTaker.css">

<!-- Enhanced Crawl Form Section -->
<section id="crawlerForm" class="crawl-form-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-8 col-lg-10">
                <!-- Section Header -->
                <div class="section-header">
                    <div class="section-icon">
                        <i class="bi bi-robot"></i>
                    </div>
                    <h2 class="section-title">Start Web Crawling</h2>
                    <p class="section-subtitle">Configure your crawling parameters and extract valuable data from any
                        website</p>
                </div>

                <!-- Main Form Card -->
                <div class="crawl-form-card">
                    <div class="form-header">
                        <div class="form-header-icon">
                            <i class="bi bi-search"></i>
                        </div>
                        <h3 class="form-title">New Crawl Task</h3>
                        <p class="form-description">Set up your web crawling configuration</p>
                    </div>

                    <form action="crawl" method="post" class="crawl-form" id="crawlForm">
                        <!-- URL Input Section -->
                        <div class="input-group-enhanced">
                            <div class="input-label">
                                <i class="bi bi-link-45deg"></i>
                                <span>Target URL</span>
                                <span class="required">*</span>
                            </div>
                            <div class="input-wrapper">
                                <input type="url" class="form-input" id="url" name="url"
                                       placeholder="https://example.com" required>
                                <div class="input-border"></div>
                                <div class="input-focus-effect"></div>
                            </div>
                            <div class="input-help">Enter the website URL you want to crawl</div>
                        </div>

                        <!-- Configuration Grid -->
                        <div class="config-grid">
                            <!-- Crawl Depth -->
                            <div class="config-item">
                                <div class="config-header">
                                    <div class="config-icon depth-icon">
                                        <i class="bi bi-layers"></i>
                                    </div>
                                    <div class="config-info">
                                        <label class="config-label" for="depth">Crawl Depth</label>
                                        <span class="config-description">How deep to crawl</span>
                                    </div>
                                </div>
                                <div class="range-slider-container">
                                    <input type="range" class="range-slider" id="depth" name="depth"
                                           min="1" max="10" value="2">
                                    <div class="range-value">
                                        <span id="depthValue">2</span>
                                        <small>levels</small>
                                    </div>
                                </div>
                                <div class="range-labels">
                                    <span>1</span>
                                    <span>5</span>
                                    <span>10</span>
                                </div>
                            </div>

                            <!-- Thread Count -->
                            <div class="config-item">
                                <div class="config-header">
                                    <div class="config-icon threads-icon">
                                        <i class="bi bi-cpu"></i>
                                    </div>
                                    <div class="config-info">
                                        <label class="config-label" for="threads">Threads</label>
                                        <span class="config-description">Concurrent requests</span>
                                    </div>
                                </div>
                                <div class="range-slider-container">
                                    <input type="range" class="range-slider" id="threads" name="threads"
                                           min="1" max="10" value="4">
                                    <div class="range-value">
                                        <span id="threadsValue">4</span>
                                        <small>threads</small>
                                    </div>
                                </div>
                                <div class="range-labels">
                                    <span>1</span>
                                    <span>5</span>
                                    <span>10</span>
                                </div>
                            </div>
                        </div>

                        <!-- Advanced Options -->
                        <div class="advanced-options">
                            <div class="options-header">
                                <h4 class="options-title">
                                    <i class="bi bi-gear me-2"></i>
                                    Advanced Options
                                </h4>
                                <button type="button" class="options-toggle" id="advancedToggle">
                                    <i class="bi bi-chevron-down"></i>
                                </button>
                            </div>

                            <div class="options-content" id="advancedOptions">
                                <!-- External Links Toggle -->
                                <div class="option-item">
                                    <div class="option-info">
                                        <div class="option-icon">
                                            <i class="bi bi-box-arrow-up-right"></i>
                                        </div>
                                        <div class="option-details">
                                            <label class="option-label" for="externalLinks">Follow External
                                                Links</label>
                                            <span class="option-description">Crawl links outside the target domain</span>
                                        </div>
                                    </div>
                                    <div class="toggle-switch">
                                        <input type="checkbox" id="externalLinks" name="externalLinks"
                                               class="toggle-input">
                                        <label for="externalLinks" class="toggle-label">
                                            <span class="toggle-slider"></span>
                                        </label>
                                    </div>
                                </div>

                                <!-- Delay Option -->
                                <div class="option-item">
                                    <div class="option-info">
                                        <div class="option-icon">
                                            <i class="bi bi-clock"></i>
                                        </div>
                                        <div class="option-details">
                                            <label class="option-label" for="delay">Request Delay</label>
                                            <span class="option-description">Delay between requests (seconds)</span>
                                        </div>
                                    </div>
                                    <div class="number-input-wrapper">
                                        <button type="button" class="number-btn minus" data-target="delay">-</button>
                                        <input type="number" id="delay" name="delay" class="number-input"
                                               value="1" min="0" max="10" step="0.5">
                                        <button type="button" class="number-btn plus" data-target="delay">+</button>
                                    </div>
                                </div>

                                <!-- User Agent -->
                                <div class="option-item full-width">
                                    <div class="option-info">
                                        <div class="option-icon">
                                            <i class="bi bi-browser-chrome"></i>
                                        </div>
                                        <div class="option-details">
                                            <label class="option-label" for="userAgent">User Agent</label>
                                            <span class="option-description">Browser identification string</span>
                                        </div>
                                    </div>
                                    <select id="userAgent" name="userAgent" class="select-input">
                                        <option value="default">Default WebCrawler</option>
                                        <option value="chrome">Chrome Browser</option>
                                        <option value="firefox">Firefox Browser</option>
                                        <option value="safari">Safari Browser</option>
                                        <option value="custom">Custom User Agent</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="form-actions">
                            <button type="button" class="btn-secondary" id="resetBtn">
                                <i class="bi bi-arrow-clockwise"></i>
                                <span>Reset</span>
                            </button>
                            <button type="submit" class="btn-primary" id="submitBtn">
                                <div class="btn-content">
                                    <i class="bi bi-play-fill"></i>
                                    <span>Start Crawling</span>
                                </div>
                                <div class="btn-loading">
                                    <div class="spinner"></div>
                                    <span>Processing...</span>
                                </div>
                            </button>
                        </div>

                        <!-- Progress Indicator -->
                        <div class="form-progress" id="formProgress">
                            <div class="progress-bar">
                                <div class="progress-fill"></div>
                            </div>
                            <div class="progress-text">Initializing crawl task...</div>
                        </div>
                    </form>
                </div>

                <!-- Quick Start Templates -->
                <div class="quick-templates">
                    <h4 class="templates-title">
                        <i class="bi bi-lightning me-2"></i>
                        Quick Start Templates
                    </h4>
                    <div class="templates-grid">
                        <div class="template-card" data-template="ecommerce">
                            <div class="template-icon">
                                <i class="bi bi-cart"></i>
                            </div>
                            <div class="template-info">
                                <h5>E-commerce</h5>
                                <p>Product pages & pricing</p>
                            </div>
                        </div>
                        <div class="template-card" data-template="news">
                            <div class="template-icon">
                                <i class="bi bi-newspaper"></i>
                            </div>
                            <div class="template-info">
                                <h5>News Sites</h5>
                                <p>Articles & headlines</p>
                            </div>
                        </div>
                        <div class="template-card" data-template="social">
                            <div class="template-icon">
                                <i class="bi bi-share"></i>
                            </div>
                            <div class="template-info">
                                <h5>Social Media</h5>
                                <p>Posts & user data</p>
                            </div>
                        </div>
                        <div class="template-card" data-template="research">
                            <div class="template-icon">
                                <i class="bi bi-search"></i>
                            </div>
                            <div class="template-info">
                                <h5>Research</h5>
                                <p>Academic & papers</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<Script src="${pageContext.request.contextPath}/js/linktaker.js"></Script>

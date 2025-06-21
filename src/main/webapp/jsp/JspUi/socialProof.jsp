<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/socialProof.css">

<!-- Social Proof Section -->
<section class="social-proof-section" id="socialProof">
    <div class="container">
        <!-- Section Header -->
        <div class="section-header">
            <div class="trust-badge">
                <i class="bi bi-shield-check"></i>
                <span>Trusted by Industry Leaders</span>
            </div>
            <h2 class="section-title">
                <span class="title-word" data-text="Join">Join</span>
                <span class="title-word highlight" data-text="10,000+">10,000+</span>
                <span class="title-word" data-text="Developers">Developers</span>
            </h2>
            <p class="section-subtitle">
                Companies worldwide trust CrawlForge for their web scraping and data extraction needs
            </p>
        </div>

        <!-- Live Stats Bar -->
        <div class="live-stats-bar">
            <div class="stats-container">
                <div class="stat-item">
                    <div class="stat-icon">
                        <i class="bi bi-globe2"></i>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number" data-target="2847">0</div>
                        <div class="stat-label">Websites Crawled Today</div>
                    </div>
                    <div class="stat-pulse"></div>
                </div>

                <div class="stat-divider"></div>

                <div class="stat-item">
                    <div class="stat-icon">
                        <i class="bi bi-database"></i>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number" data-target="15.7">0</div>
                        <div class="stat-suffix">M</div>
                        <div class="stat-label">Data Points Extracted</div>
                    </div>
                    <div class="stat-pulse"></div>
                </div>

                <div class="stat-divider"></div>

                <div class="stat-item">
                    <div class="stat-icon">
                        <i class="bi bi-lightning"></i>
                    </div>
                    <div class="stat-content">
                        <div class="stat-number" data-target="99.8">0</div>
                        <div class="stat-suffix">%</div>
                        <div class="stat-label">Uptime Guarantee</div>
                    </div>
                    <div class="stat-pulse"></div>
                </div>
            </div>
        </div>

        <!-- Company Logos Grid -->
        <div class="companies-grid">
            <div class="companies-header">
                <h3 class="companies-title">Powering innovation at</h3>
            </div>

            <div class="logos-container">
                <div class="logo-item" data-company="TechCorp">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-building"></i>
                        </div>
                        <div class="logo-name">TechCorp</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>

                <div class="logo-item" data-company="DataFlow">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-diagram-3"></i>
                        </div>
                        <div class="logo-name">DataFlow</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>

                <div class="logo-item" data-company="CloudSync">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-cloud-arrow-up"></i>
                        </div>
                        <div class="logo-name">CloudSync</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>

                <div class="logo-item" data-company="InnovateLab">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-lightbulb"></i>
                        </div>
                        <div class="logo-name">InnovateLab</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>

                <div class="logo-item" data-company="ScaleUp">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-graph-up-arrow"></i>
                        </div>
                        <div class="logo-name">ScaleUp</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>

                <div class="logo-item" data-company="NextGen">
                    <div class="logo-card">
                        <div class="logo-icon">
                            <i class="bi bi-rocket-takeoff"></i>
                        </div>
                        <div class="logo-name">NextGen</div>
                        <div class="logo-glow"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Testimonials Carousel -->
        <div class="testimonials-section">
            <div class="testimonials-header">
                <h3 class="testimonials-title">What our customers say</h3>
            </div>

            <div class="testimonials-carousel" id="testimonialsCarousel">
                <div class="testimonial-card active" data-index="0">
                    <div class="testimonial-content">
                        <div class="quote-icon">
                            <i class="bi bi-quote"></i>
                        </div>
                        <p class="testimonial-text">
                            "CrawlForge transformed our data collection process. We went from manual scraping
                            to automated insights in just days. The accuracy and speed are incredible."
                        </p>
                        <div class="testimonial-author">
                            <div class="author-avatar">
                                <i class="bi bi-person-circle"></i>
                            </div>
                            <div class="author-info">
                                <div class="author-name">Sarah Chen</div>
                                <div class="author-role">Data Scientist, TechCorp</div>
                            </div>
                        </div>
                        <div class="testimonial-rating">
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                        </div>
                    </div>
                </div>

                <div class="testimonial-card" data-index="1">
                    <div class="testimonial-content">
                        <div class="quote-icon">
                            <i class="bi bi-quote"></i>
                        </div>
                        <p class="testimonial-text">
                            "The AI-powered parsing is a game-changer. It understands complex website structures
                            and extracts exactly what we need with 99% accuracy."
                        </p>
                        <div class="testimonial-author">
                            <div class="author-avatar">
                                <i class="bi bi-person-circle"></i>
                            </div>
                            <div class="author-info">
                                <div class="author-name">Marcus Rodriguez</div>
                                <div class="author-role">CTO, DataFlow</div>
                            </div>
                        </div>
                        <div class="testimonial-rating">
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                        </div>
                    </div>
                </div>

                <div class="testimonial-card" data-index="2">
                    <div class="testimonial-content">
                        <div class="quote-icon">
                            <i class="bi bi-quote"></i>
                        </div>
                        <p class="testimonial-text">
                            "Scaling from 100 to 10,000 websites was seamless. The platform handles everything
                            while we focus on analyzing the insights."
                        </p>
                        <div class="testimonial-author">
                            <div class="author-avatar">
                                <i class="bi bi-person-circle"></i>
                            </div>
                            <div class="author-info">
                                <div class="author-name">Emily Watson</div>
                                <div class="author-role">Head of Analytics, CloudSync</div>
                            </div>
                        </div>
                        <div class="testimonial-rating">
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="carousel-controls">
                <button class="carousel-btn prev" id="prevBtn">
                    <i class="bi bi-chevron-left"></i>
                </button>
                <div class="carousel-dots">
                    <div class="dot active" data-index="0"></div>
                    <div class="dot" data-index="1"></div>
                    <div class="dot" data-index="2"></div>
                </div>
                <button class="carousel-btn next" id="nextBtn">
                    <i class="bi bi-chevron-right"></i>
                </button>
            </div>
        </div>

        <!-- Trust Indicators -->
        <div class="trust-indicators">
            <div class="trust-item">
                <div class="trust-icon">
                    <i class="bi bi-shield-lock"></i>
                </div>
                <div class="trust-content">
                    <div class="trust-title">Enterprise Security</div>
                    <div class="trust-desc">SOC 2 Type II Certified</div>
                </div>
            </div>

            <div class="trust-item">
                <div class="trust-icon">
                    <i class="bi bi-clock-history"></i>
                </div>
                <div class="trust-content">
                    <div class="trust-title">99.9% Uptime</div>
                    <div class="trust-desc">Guaranteed SLA</div>
                </div>
            </div>

            <div class="trust-item">
                <div class="trust-icon">
                    <i class="bi bi-headset"></i>
                </div>
                <div class="trust-content">
                    <div class="trust-title">24/7 Support</div>
                    <div class="trust-desc">Expert assistance</div>
                </div>
            </div>

            <div class="trust-item">
                <div class="trust-icon">
                    <i class="bi bi-award"></i>
                </div>
                <div class="trust-content">
                    <div class="trust-title">Industry Leader</div>
                    <div class="trust-desc">G2 #1 Rated</div>
                </div>
            </div>
        </div>
    </div>
</section>

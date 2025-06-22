<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">


<!-- Enhanced Footer Section -->
<footer class="footer-section">
    <div class="container">
        <div class="footer-content">
            <!-- Footer Brand and Description -->
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="footer-brand">
                        <a href="${pageContext.request.contextPath}/" class="footer-logo">
                            <%--                            <i class="bi bi-globe-americas"></i>--%>
                            <img src="favicon.ico" alt="logo" height="30" width="30" class="logo-animate">
                            CrawlForge
                        </a>
                    </div>
                    <p class="footer-description">
                        Advanced web crawling and analytics platform that helps businesses extract valuable insights
                        from web data.
                        Crawl smarter, analyze deeper, and make data-driven decisions.
                    </p>

                    <!-- Social Links -->
                    <div class="social-links">
                        <a href="#" class="social-link" title="GitHub">
                            <i class="bi bi-github"></i>
                        </a>
                        <a href="#" class="social-link" title="LinkedIn">
                            <i class="bi bi-linkedin"></i>
                        </a>
                        <a href="#" class="social-link" title="Twitter">
                            <i class="bi bi-twitter"></i>
                        </a>
                        <a href="#" class="social-link" title="Discord">
                            <i class="bi bi-discord"></i>
                        </a>
                        <a href="#" class="social-link" title="YouTube">
                            <i class="bi bi-youtube"></i>
                        </a>
                    </div>
                </div>

                <!-- Footer Links -->
                <div class="col-lg-8 col-md-6">
                    <div class="footer-links">
                        <!-- Product Links -->
                        <div class="footer-column">
                            <h4>Product</h4>
                            <a href="${pageContext.request.contextPath}/features" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Features
                            </a>
                            <a href="${pageContext.request.contextPath}/pricing" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Pricing
                            </a>
                            <a href="${pageContext.request.contextPath}/api" class="footer-link">
                                <i class="bi bi-arrow-right"></i>API Documentation
                            </a>
                            <a href="${pageContext.request.contextPath}/integrations" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Integrations
                            </a>
                            <a href="${pageContext.request.contextPath}/changelog" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Changelog
                            </a>
                        </div>

                        <!-- Company Links -->
                        <div class="footer-column">
                            <h4>Company</h4>
                            <a href="${pageContext.request.contextPath}/about" class="footer-link">
                                <i class="bi bi-arrow-right"></i>About Us
                            </a>
                            <a href="${pageContext.request.contextPath}/careers" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Careers
                            </a>
                            <a href="${pageContext.request.contextPath}/blog" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Blog
                            </a>
                            <a href="${pageContext.request.contextPath}/press" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Press Kit
                            </a>
                            <a href="${pageContext.request.contextPath}/contact" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Contact
                            </a>
                        </div>

                        <!-- Resources Links -->
                        <div class="footer-column">
                            <h4>Resources</h4>
                            <a href="${pageContext.request.contextPath}/docs" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Documentation
                            </a>
                            <a href="${pageContext.request.contextPath}/tutorials" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Tutorials
                            </a>
                            <a href="${pageContext.request.contextPath}/support" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Support Center
                            </a>
                            <a href="${pageContext.request.contextPath}/community" class="footer-link">
                                <i class="bi bi-arrow-right"></i>Community
                            </a>
                            <a href="${pageContext.request.contextPath}/status" class="footer-link">
                                <i class="bi bi-arrow-right"></i>System Status
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Newsletter Signup -->
            <div class="row">
                <div class="col-lg-6 mx-auto">
                    <div class="newsletter-section">
                        <h3 class="newsletter-title">
                            <i class="bi bi-envelope me-2"></i>
                            Stay Updated
                        </h3>
                        <p class="newsletter-description">
                            Get the latest updates on new features, tutorials, and web crawling insights.
                        </p>
                        <form class="newsletter-form" action="${pageContext.request.contextPath}/newsletter"
                              method="post">
                            <input type="email" class="newsletter-input" placeholder="Enter your email address"
                                   required>
                            <button type="submit" class="newsletter-button">
                                <i class="bi bi-send me-1"></i>
                                Subscribe
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Footer Bottom -->
            <div class="footer-bottom">
                <div class="footer-copyright">
                    <i class="bi bi-c-circle me-1"></i>
                    2025 CrawlForge Analytics. All rights reserved. Built with ❤️ and Java.
                </div>
                <div class="footer-legal">
                    <a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a>
                    <a href="${pageContext.request.contextPath}/terms">Terms of Service</a>
                    <a href="${pageContext.request.contextPath}/cookies">Cookie Policy</a>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- Footer JavaScript -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Newsletter form submission
        const newsletterForm = document.querySelector('.newsletter-form');
        if (newsletterForm) {
            newsletterForm.addEventListener('submit', function(e) {
                e.preventDefault();
                const email = this.querySelector('.newsletter-input').value;

                // Add your newsletter subscription logic here
                console.log('Newsletter subscription for:', email);

                // Show success message (you can customize this)
                const button = this.querySelector('.newsletter-button');
                const originalText = button.innerHTML;
                button.innerHTML = '<i class="bi bi-check-circle me-1"></i>Subscribed!';
                button.style.background = 'linear-gradient(135deg, #1db954, #1ed760)';

                setTimeout(() => {
                    button.innerHTML = originalText;
                    button.style.background = 'linear-gradient(135deg, #10a37f 0%, #0d8465 100%)';
                    this.querySelector('.newsletter-input').value = '';
                }, 2000);
            });
        }

        // Smooth scroll for footer links
        document.querySelectorAll('.footer-link[href^="#"]').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
    });
</script>

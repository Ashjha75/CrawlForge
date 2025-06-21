<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    /* Hero Section with Glass Effect and Gradients */
    .hero-section {
        min-height: 100vh;
        position: relative;
        background: linear-gradient(135deg,
        #1f1f2b 0%,
        #2c2c3a 25%,
        #40414f 50%,
        #2c2c3a 75%,
        #1f1f2b 100%);
        overflow: hidden;
        display: flex;
        align-items: center;
        padding: 100px 0 50px;
    }

    /* Animated Background Elements */
    .hero-section::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background:
                radial-gradient(circle at 20% 20%, rgba(16, 163, 127, 0.1) 0%, transparent 50%),
                radial-gradient(circle at 80% 80%, rgba(16, 163, 127, 0.08) 0%, transparent 50%),
                radial-gradient(circle at 40% 60%, rgba(16, 163, 127, 0.05) 0%, transparent 50%);
        animation: float 6s ease-in-out infinite;
    }

    .hero-section::after {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grid" width="10" height="10" patternUnits="userSpaceOnUse"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="%23ffffff" stroke-width="0.1" opacity="0.05"/></pattern></defs><rect width="100" height="100" fill="url(%23grid)"/></svg>');
        opacity: 0.3;
    }

    @keyframes float {
        0%, 100% { transform: translateY(0px) rotate(0deg); }
        50% { transform: translateY(-20px) rotate(1deg); }
    }

    /* Hero Content Container */
    .hero-content {
        position: relative;
        z-index: 2;
        width: 100%;
    }

    /* Main Hero Text */
    .hero-title {
        font-size: 4rem;
        font-weight: 800;
        background: linear-gradient(135deg,
        #10a37f 0%,
        #0d8465 25%,
        #10a37f 50%,
        #0a6b52 75%,
        #10a37f 100%);
        background-size: 300% 300%;
        -webkit-background-clip: text;
        background-clip: text;
        -webkit-text-fill-color: transparent;
        text-align: center;
        margin-bottom: 1.5rem;
        animation: gradient-shift 3s ease-in-out infinite, fadeInUp 1s ease-out;
        line-height: 1.2;
    }

    @keyframes gradient-shift {
        0%, 100% { background-position: 0% 50%; }
        50% { background-position: 100% 50%; }
    }

    .hero-subtitle {
        font-size: 1.3rem;
        color: #c5c5d2;
        text-align: center;
        margin-bottom: 3rem;
        opacity: 0.9;
        animation: fadeInUp 1s ease-out 0.3s both;
        max-width: 600px;
        margin-left: auto;
        margin-right: auto;
        line-height: 1.6;
    }

    /* Glass Cards Container */
    .hero-cards {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 2rem;
        margin-top: 4rem;
        animation: fadeInUp 1s ease-out 0.6s both;
    }

    /* Earnings Card with Glass Effect */
    .earnings-card {
        background: linear-gradient(135deg,
        rgba(16, 163, 127, 0.15) 0%,
        rgba(16, 163, 127, 0.08) 50%,
        rgba(16, 163, 127, 0.05) 100%);
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(16, 163, 127, 0.2);
        border-radius: 20px;
        padding: 2rem;
        position: relative;
        overflow: hidden;
        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
        box-shadow:
                0 8px 32px rgba(0, 0, 0, 0.3),
                inset 0 1px 0 rgba(255, 255, 255, 0.1);
    }

    .earnings-card::before {
        content: '';
        position: absolute;
        top: 0;
        left: -100%;
        width: 100%;
        height: 100%;
        background: linear-gradient(90deg,
        transparent 0%,
        rgba(16, 163, 127, 0.1) 50%,
        transparent 100%);
        transition: left 0.6s ease;
    }

    .earnings-card:hover::before {
        left: 100%;
    }

    .earnings-card:hover {
        transform: translateY(-5px);
        box-shadow:
                0 12px 40px rgba(16, 163, 127, 0.2),
                inset 0 1px 0 rgba(255, 255, 255, 0.15);
        border-color: rgba(16, 163, 127, 0.4);
    }

    .earnings-label {
        color: #c5c5d2;
        font-size: 0.9rem;
        margin-bottom: 0.5rem;
        opacity: 0.8;
    }

    .earnings-amount {
        font-size: 3rem;
        font-weight: 700;
        color: #10a37f;
        margin-bottom: 1rem;
        text-shadow: 0 0 20px rgba(16, 163, 127, 0.3);
    }

    .earnings-details {
        color: #c5c5d2;
        font-size: 0.9rem;
        opacity: 0.7;
    }

    /* Analytics Chart Area */
    .chart-container {
        height: 100px;
        position: relative;
        margin-top: 1rem;
        overflow: hidden;
    }

    .chart-line {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 2px;
        background: linear-gradient(90deg,
        transparent 0%,
        #10a37f 50%,
        transparent 100%);
        animation: chart-pulse 2s ease-in-out infinite;
    }

    @keyframes chart-pulse {
        0%, 100% { opacity: 0.5; transform: scaleX(0.8); }
        50% { opacity: 1; transform: scaleX(1); }
    }

    /* Connect Sources Card */
    .connect-card {
        background: linear-gradient(135deg,
        rgba(64, 65, 79, 0.9) 0%,
        rgba(44, 44, 58, 0.8) 50%,
        rgba(31, 31, 43, 0.9) 100%);
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 2rem;
        position: relative;
        overflow: hidden;
        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
        box-shadow:
                0 8px 32px rgba(0, 0, 0, 0.3),
                inset 0 1px 0 rgba(255, 255, 255, 0.05);
    }

    .connect-card:hover {
        transform: translateY(-5px);
        box-shadow:
                0 12px 40px rgba(0, 0, 0, 0.4),
                inset 0 1px 0 rgba(255, 255, 255, 0.1);
    }

    .connect-title {
        color: #ffffff;
        font-size: 1.5rem;
        font-weight: 600;
        margin-bottom: 2rem;
        text-align: center;
    }

    /* Service Icons Grid */
    .services-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 1rem;
        margin-bottom: 2rem;
    }

    .service-icon {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
        color: white;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
    }

    .service-icon::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(45deg, transparent 30%, rgba(255, 255, 255, 0.2) 50%, transparent 70%);
        transform: translateX(-100%);
        transition: transform 0.5s ease;
    }

    .service-icon:hover::before {
        transform: translateX(100%);
    }

    .service-icon:hover {
        transform: scale(1.1);
    }

    .service-uber { background: linear-gradient(135deg, #000000, #333333); }
    .service-amazon { background: linear-gradient(135deg, #ff9900, #ffb84d); }
    .service-spotify { background: linear-gradient(135deg, #1db954, #1ed760); }
    .service-netflix { background: linear-gradient(135deg, #e50914, #f40612); }

    /* CTA Button */
    .cta-button {
        background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
        color: white;
        border: none;
        padding: 1rem 2rem;
        border-radius: 50px;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
        box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3);
        width: 100%;
        margin-top: 1rem;
    }

    .cta-button::before {
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
        transition: left 0.5s ease;
    }

    .cta-button:hover::before {
        left: 100%;
    }

    .cta-button:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(16, 163, 127, 0.4);
    }

    /* Insights Panel */
    .insights-panel {
        margin-top: 2rem;
        padding: 1.5rem;
        background: rgba(16, 163, 127, 0.05);
        border-radius: 12px;
        border: 1px solid rgba(16, 163, 127, 0.1);
    }

    .insight-item {
        display: flex;
        align-items: center;
        margin-bottom: 1rem;
        color: #c5c5d2;
        font-size: 0.9rem;
    }

    .insight-icon {
        width: 20px;
        height: 20px;
        margin-right: 0.8rem;
        color: #10a37f;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .hero-title {
            font-size: 2.5rem;
        }

        .hero-subtitle {
            font-size: 1.1rem;
        }

        .hero-cards {
            grid-template-columns: 1fr;
            gap: 1.5rem;
        }

        .earnings-amount {
            font-size: 2.5rem;
        }

        .services-grid {
            grid-template-columns: repeat(4, 1fr);
        }

        .service-icon {
            width: 50px;
            height: 50px;
            font-size: 1.2rem;
        }
    }

    @media (max-width: 480px) {
        .hero-title {
            font-size: 2rem;
        }

        .earnings-card, .connect-card {
            padding: 1.5rem;
        }

        .earnings-amount {
            font-size: 2rem;
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

<!-- Hero Section -->
<section class="hero-section">
    <div class="container">
        <div class="hero-content">
            <!-- Main Hero Text -->
            <h1 class="hero-title">Connect. Learn. Earn</h1>
            <p class="hero-subtitle">
                Your data is a profitable asset. With WebCrawler you control what
                data to share anonymously and earn from it.
            </p>

            <!-- Hero Cards Grid -->
            <div class="hero-cards">
                <!-- Earnings Card -->
                <div class="earnings-card">
                    <div class="earnings-label">Your earnings</div>
                    <div class="earnings-amount">$30.00</div>
                    <div class="earnings-details">
                        Next payout in:<br>
                        10,550 pts
                    </div>
                    <div class="chart-container">
                        <div class="chart-line"></div>
                    </div>
                </div>

                <!-- Connect Sources Card -->
                <div class="connect-card">
                    <h3 class="connect-title">Connect sources</h3>

                    <div class="services-grid">
                        <div class="service-icon service-uber">
                            <i class="bi bi-car-front"></i>
                        </div>
                        <div class="service-icon service-amazon">
                            <i class="bi bi-cart"></i>
                        </div>
                        <div class="service-icon service-spotify">
                            <i class="bi bi-music-note"></i>
                        </div>
                        <div class="service-icon service-netflix">
                            <i class="bi bi-play-circle"></i>
                        </div>
                    </div>

                    <button class="cta-button">
                        <i class="bi bi-download me-2"></i>
                        Download on the App Store
                    </button>

                    <div class="insights-panel">
                        <div class="insight-item">
                            <i class="bi bi-graph-up insight-icon"></i>
                            <span>Learn more from your data and make better decisions</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

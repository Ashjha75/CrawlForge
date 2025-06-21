

document.addEventListener('DOMContentLoaded', function() {
    // Register GSAP ScrollTrigger
    gsap.registerPlugin(ScrollTrigger);

    // Adjust hero section padding based on actual header height
    function adjustHeroPadding() {
        const header = document.querySelector('.navbar-custom') || document.querySelector('nav') || document.querySelector('header');
        if (header) {
            const headerHeight = header.offsetHeight;
            document.documentElement.style.setProperty('--header-height', headerHeight + 'px');
        }
    }

    // Call on load and resize
    adjustHeroPadding();
    window.addEventListener('resize', adjustHeroPadding);

    // Typewriter effect
    const typewriterText = document.getElementById('typewriterText');
    const texts = [
        'Extract data from any website with AI precision',
        'Scale your web scraping operations globally',
        'Turn web data into actionable business insights',
        'Automate data collection with smart algorithms'
    ];

    let textIndex = 0;
    let charIndex = 0;
    let isDeleting = false;

    function typeWriter() {
        const currentText = texts[textIndex];

        if (isDeleting) {
            typewriterText.textContent = currentText.substring(0, charIndex - 1);
            charIndex--;
        } else {
            typewriterText.textContent = currentText.substring(0, charIndex + 1);
            charIndex++;
        }

        let typeSpeed = isDeleting ? 50 : 100;

        if (!isDeleting && charIndex === currentText.length) {
            typeSpeed = 2000;
            isDeleting = true;
        } else if (isDeleting && charIndex === 0) {
            isDeleting = false;
            textIndex = (textIndex + 1) % texts.length;
            typeSpeed = 500;
        }

        setTimeout(typeWriter, typeSpeed);
    }

    setTimeout(typeWriter, 1000);

    // Neural network canvas animation
    const canvas = document.getElementById('networkCanvas');
    const ctx = canvas.getContext('2d');

    function resizeCanvas() {
        canvas.width = canvas.offsetWidth;
        canvas.height = canvas.offsetHeight;
    }

    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    const nodes = [];
    const connections = [];

    // Create nodes
    for (let i = 0; i < 50; i++) {
        nodes.push({
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height,
            vx: (Math.random() - 0.5) * 0.5,
            vy: (Math.random() - 0.5) * 0.5,
            radius: Math.random() * 2 + 1
        });
    }

    function animateNetwork() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Update and draw nodes
        nodes.forEach(node => {
            node.x += node.vx;
            node.y += node.vy;

            if (node.x < 0 || node.x > canvas.width) node.vx *= -1;
            if (node.y < 0 || node.y > canvas.height) node.vy *= -1;

            ctx.beginPath();
            ctx.arc(node.x, node.y, node.radius, 0, Math.PI * 2);
            ctx.fillStyle = 'rgba(16, 163, 127, 0.6)';
            ctx.fill();
        });

        // Draw connections
        for (let i = 0; i < nodes.length; i++) {
            for (let j = i + 1; j < nodes.length; j++) {
                const dx = nodes[i].x - nodes[j].x;
                const dy = nodes[i].y - nodes[j].y;
                const distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < 100) {
                    ctx.beginPath();
                    ctx.moveTo(nodes[i].x, nodes[i].y);
                    ctx.lineTo(nodes[j].x, nodes[j].y);
                    ctx.strokeStyle = `rgba(16, 163, 127, ${0.3 * (1 - distance / 100)})`;
                    ctx.lineWidth = 0.5;
                    ctx.stroke();
                }
            }
        }

        requestAnimationFrame(animateNetwork);
    }

    animateNetwork();

    // Card scrolling animation
    const cards = document.querySelectorAll('.data-card');
    const navDots = document.querySelectorAll('.nav-dot');
    let currentCard = 0;

    function updateCards() {
        cards.forEach((card, index) => {
            card.classList.remove('active', 'next', 'prev');
            navDots[index].classList.remove('active');

            if (index === currentCard) {
                card.classList.add('active');
                navDots[index].classList.add('active');
            } else if (index === currentCard + 1 || (currentCard === cards.length - 1 && index === 0)) {
                card.classList.add('next');
            } else {
                card.classList.add('prev');
            }
        });
    }

    // GSAP ScrollTrigger for card transitions
    gsap.timeline({
        scrollTrigger: {
            trigger: '.data-cards-container',
            start: 'top center',
            end: 'bottom center',
            scrub: 1,
            onUpdate: (self) => {
                const progress = self.progress;
                const newCard = Math.floor(progress * cards.length);
                if (newCard !== currentCard && newCard < cards.length) {
                    currentCard = newCard;
                    updateCards();
                }
            }
        }
    });

    // Navigation dots click
    navDots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            currentCard = index;
            updateCards();
        });
    });

    // Animated counters
    function animateCounter(element, target, suffix = '') {
        let current = 0;
        const increment = target / 100;
        const timer = setInterval(() => {
            current += increment;
            if (current >= target) {
                current = target;
                clearInterval(timer);
            }
            element.textContent = Math.floor(current) + suffix;
        }, 20);
    }

    // Performance chart animation
    const performanceChart = document.getElementById('performanceChart');
    if (performanceChart) {
        const ctx = performanceChart.getContext('2d');
        const data = [20, 45, 30, 60, 40, 80, 55, 70, 45, 85, 60, 90];

        function drawChart() {
            ctx.clearRect(0, 0, performanceChart.width, performanceChart.height);

            const width = performanceChart.width;
            const height = performanceChart.height;
            const stepX = width / (data.length - 1);

            // Draw gradient
            const gradient = ctx.createLinearGradient(0, 0, 0, height);
            gradient.addColorStop(0, 'rgba(16, 163, 127, 0.3)');
            gradient.addColorStop(1, 'rgba(16, 163, 127, 0.05)');

            ctx.beginPath();
            ctx.moveTo(0, height);

            data.forEach((value, index) => {
                const x = index * stepX;
                const y = height - (value / 100) * height;
                ctx.lineTo(x, y);
            });

            ctx.lineTo(width, height);
            ctx.closePath();
            ctx.fillStyle = gradient;
            ctx.fill();

            // Draw line
            ctx.beginPath();
            data.forEach((value, index) => {
                const x = index * stepX;
                const y = height - (value / 100) * height;
                if (index === 0) {
                    ctx.moveTo(x, y);
                } else {
                    ctx.lineTo(x, y);
                }
            });
            ctx.strokeStyle = '#10a37f';
            ctx.lineWidth = 2;
            ctx.stroke();
        }

        drawChart();
    }

    // Terminal logs animation
    const terminalOutput = document.getElementById('terminalOutput');
    const logMessages = [
        { level: 'success', message: 'amazon.com - 15,420 products extracted' },
        { level: 'info', message: 'linkedin.com - processing user profiles...' },
        { level: 'success', message: 'github.com - 2,847 repositories indexed' },
        { level: 'warning', message: 'twitter.com - rate limit detected, adjusting...' },
        { level: 'success', message: 'shopify.com - 8,923 product listings crawled' },
        { level: 'info', message: 'reddit.com - analyzing trending topics...' }
    ];

    let logIndex = 0;

    function addLogEntry() {
        if (terminalOutput) {
            const log = logMessages[logIndex % logMessages.length];
            const timestamp = new Date().toLocaleTimeString();

            const logEntry = document.createElement('div');
            logEntry.className = 'log-entry';
            logEntry.innerHTML = `
                <span class="log-timestamp">[${timestamp}]</span>
                <span class="log-level ${log.level}">${log.level.toUpperCase()}</span>
                <span class="log-message">${log.message}</span>
            `;

            terminalOutput.appendChild(logEntry);

            if (terminalOutput.children.length > 8) {
                terminalOutput.removeChild(terminalOutput.firstChild);
            }

            terminalOutput.scrollTop = terminalOutput.scrollHeight;
            logIndex++;
        }
    }

    // Add initial logs
    setTimeout(() => {
        addLogEntry();
        setInterval(addLogEntry, 2500);
    }, 2000);

    // Start counters when visible
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const counters = entry.target.querySelectorAll('[data-target]');
                counters.forEach(counter => {
                    const target = parseFloat(counter.dataset.target);
                    const suffix = counter.nextElementSibling?.classList.contains('metric-suffix') ? '' : '';
                    animateCounter(counter, target, suffix);
                });
                observer.unobserve(entry.target);
            }
        });
    });

    cards.forEach(card => observer.observe(card));

    // CTA button particle effect
    const ctaButton = document.getElementById('ctaButton');
    if (ctaButton) {
        ctaButton.addEventListener('click', function() {
            // Add particle explosion effect
            const particles = this.querySelector('.button-particles');
            particles.innerHTML = '';

            for (let i = 0; i < 20; i++) {
                const particle = document.createElement('div');
                particle.style.cssText = `
                    position: absolute;
                    width: 4px;
                    height: 4px;
                    background: #10a37f;
                    border-radius: 50%;
                    top: 50%;
                    left: 50%;
                    pointer-events: none;
                    animation: particle-explode 0.8s ease-out forwards;
                    animation-delay: ${Math.random() * 0.2}s;
                `;
                particles.appendChild(particle);
            }

            setTimeout(() => {
                particles.innerHTML = '';
            }, 1000);
        });
    }

    // Initialize first card
    updateCards();
});

// Particle explosion keyframes
const style = document.createElement('style');
style.textContent = `
    @keyframes particle-explode {
        0% {
            transform: translate(-50%, -50%) scale(1);
            opacity: 1;
        }
        100% {
            transform: translate(-50%, -50%) translate(${Math.random() * 200 - 100}px, ${Math.random() * 200 - 100}px) scale(0);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);

<script>
document.getElementById('ctaButton').addEventListener('click', function() {
    window.location.href = '<%= request.getContextPath() %>/scrapyUi';
});
</script>

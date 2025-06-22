document.addEventListener('DOMContentLoaded', function() {
    // Initialize all components
    initializeCounters();
    initializeDateRange();
    initializeCharts();
    initializeResourceGauges();
    initializeActivityFeed();

    // Animated counters
    function initializeCounters() {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const counter = entry.target;
                    const target = parseInt(counter.dataset.target);
                    animateCounter(counter, target);
                    observer.unobserve(counter);
                }
            });
        });

        document.querySelectorAll('[data-target]').forEach(counter => {
            observer.observe(counter);
        });
    }

    function animateCounter(element, target) {
        let current = 0;
        const increment = target / 100;
        const timer = setInterval(() => {
            current += increment;
            if (current >= target) {
                current = target;
                clearInterval(timer);
            }
            element.textContent = Math.floor(current);
        }, 20);
    }

    // Date range picker
    function initializeDateRange() {
        document.querySelectorAll('.date-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                document.querySelectorAll('.date-btn').forEach(b => b.classList.remove('active'));
                this.classList.add('active');

                const range = this.dataset.range;
                console.log('Date range changed to:', range);
                // Implement date range filtering logic here
            });
        });
    }

    // Initialize charts (placeholder - replace with actual chart library)
    function initializeCharts() {
        // KPI mini charts
        drawMiniChart('crawlsChart', [20, 45, 30, 60, 40, 80, 55]);
        drawMiniChart('successChart', [60, 65, 70, 75, 80, 85, 90]);
        drawMiniChart('dataChart', [30, 50, 40, 70, 60, 90, 85]);
        drawMiniChart('performanceChart', [80, 75, 70, 65, 60, 55, 50]);

        // Main activity chart
        drawActivityChart();

        // Success breakdown pie chart
        drawPieChart();
    }

    function drawMiniChart(canvasId, data) {
        const canvas = document.getElementById(canvasId);
        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;

        ctx.clearRect(0, 0, width, height);

        // Draw gradient background
        const gradient = ctx.createLinearGradient(0, 0, 0, height);
        gradient.addColorStop(0, 'rgba(16, 163, 127, 0.3)');
        gradient.addColorStop(1, 'rgba(16, 163, 127, 0.05)');

        // Draw line chart
        ctx.beginPath();
        ctx.moveTo(0, height);

        data.forEach((value, index) => {
            const x = (index / (data.length - 1)) * width;
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
            const x = (index / (data.length - 1)) * width;
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

    function drawActivityChart() {
        // Placeholder for main activity chart
        // Replace with actual chart library like Chart.js or D3.js
        console.log('Activity chart initialized');
    }

    function drawPieChart() {
        // Placeholder for pie chart
        // Replace with actual chart library
        console.log('Pie chart initialized');
    }

    // Resource gauges animation
    function initializeResourceGauges() {
        document.querySelectorAll('.gauge-fill').forEach(gauge => {
            const percentage = gauge.dataset.percentage;
            gauge.style.width = '0%';

            setTimeout(() => {
                gauge.style.width = percentage + '%';
            }, 500);
        });
    }

    // Activity feed updates
    function initializeActivityFeed() {
        // Simulate real-time updates
        setInterval(updateActivityFeed, 10000); // Update every 10 seconds
    }

    function updateActivityFeed() {
        const activities = [
            {
                type: 'success',
                icon: 'check-circle',
                text: '<strong>api-site.com</strong> crawl completed successfully',
                meta: '892 pages • 1m 45s • Just now'
            },
            {
                type: 'processing',
                icon: 'arrow-clockwise',
                text: '<strong>data-source.com</strong> crawl in progress',
                meta: '234 pages • 1m 12s elapsed • 30s ago'
            }
        ];

        const randomActivity = activities[Math.floor(Math.random() * activities.length)];
        addActivityItem(randomActivity);
    }

    function addActivityItem(activity) {
        const feed = document.getElementById('activityFeed');
        const newItem = document.createElement('div');
        newItem.className = 'activity-item';
        newItem.style.opacity = '0';
        newItem.innerHTML = `
            <div class="activity-icon ${activity.type}">
                <i class="bi bi-${activity.icon}"></i>
            </div>
            <div class="activity-content">
                <div class="activity-text">${activity.text}</div>
                <div class="activity-meta">${activity.meta}</div>
            </div>
        `;

        feed.insertBefore(newItem, feed.firstChild);

        // Animate in
        setTimeout(() => {
            newItem.style.opacity = '1';
            newItem.style.transform = 'translateX(0)';
        }, 100);

        // Remove old items if too many
        const items = feed.querySelectorAll('.activity-item');
        if (items.length > 6) {
            items[items.length - 1].remove();
        }
    }

    // Chart controls
    document.querySelectorAll('.chart-toggle').forEach(toggle => {
        toggle.addEventListener('click', function() {
            const parent = this.closest('.chart-controls');
            parent.querySelectorAll('.chart-toggle').forEach(t => t.classList.remove('active'));
            this.classList.add('active');

            const metric = this.dataset.metric;
            console.log('Chart metric changed to:', metric);
            // Implement chart update logic here
        });
    });

    // Export functionality
    document.querySelector('.export-report-btn').addEventListener('click', function() {
        // Add loading state
        const originalText = this.innerHTML;
        this.innerHTML = '<i class="bi bi-hourglass"></i><span>Generating...</span>';
        this.disabled = true;

        // Simulate export process
        setTimeout(() => {
            this.innerHTML = '<i class="bi bi-check"></i><span>Downloaded!</span>';

            setTimeout(() => {
                this.innerHTML = originalText;
                this.disabled = false;
            }, 2000);
        }, 2000);

        console.log('Exporting report...');
        // Implement actual export logic here
    });
});

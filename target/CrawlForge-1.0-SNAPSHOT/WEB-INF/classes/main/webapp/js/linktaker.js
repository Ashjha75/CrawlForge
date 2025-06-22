document.addEventListener('DOMContentLoaded', function() {
    // Range slider updates
    const depthSlider = document.getElementById('depth');
    const threadsSlider = document.getElementById('threads');
    const depthValue = document.getElementById('depthValue');
    const threadsValue = document.getElementById('threadsValue');

    depthSlider.addEventListener('input', function() {
        depthValue.textContent = this.value;
    });

    threadsSlider.addEventListener('input', function() {
        threadsValue.textContent = this.value;
    });

    // Advanced options toggle
    const advancedToggle = document.getElementById('advancedToggle');
    const advancedOptions = document.getElementById('advancedOptions');

    advancedToggle.addEventListener('click', function() {
        this.classList.toggle('active');
        advancedOptions.classList.toggle('active');
    });

    // Number input controls
    document.querySelectorAll('.number-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const target = document.getElementById(this.dataset.target);
            const isPlus = this.classList.contains('plus');
            const step = parseFloat(target.step) || 1;
            const min = parseFloat(target.min) || 0;
            const max = parseFloat(target.max) || 100;

            let value = parseFloat(target.value) || 0;

            if (isPlus && value < max) {
                value += step;
            } else if (!isPlus && value > min) {
                value -= step;
            }

            target.value = value;
        });
    });

    // Template selection
    document.querySelectorAll('.template-card').forEach(card => {
        card.addEventListener('click', function() {
            const template = this.dataset.template;
            applyTemplate(template);
        });
    });

    function applyTemplate(template) {
        const urlInput = document.getElementById('url');
        const depthSlider = document.getElementById('depth');
        const threadsSlider = document.getElementById('threads');
        const externalLinks = document.getElementById('externalLinks');

        const templates = {
            ecommerce: {
                depth: 3,
                threads: 6,
                externalLinks: false,
                placeholder: 'https://example-shop.com'
            },
            news: {
                depth: 2,
                threads: 8,
                externalLinks: true,
                placeholder: 'https://news-site.com'
            },
            social: {
                depth: 1,
                threads: 4,
                externalLinks: false,
                placeholder: 'https://social-platform.com'
            },
            research: {
                depth: 4,
                threads: 3,
                externalLinks: true,
                placeholder: 'https://research-site.edu'
            }
        };

        const config = templates[template];
        if (config) {
            depthSlider.value = config.depth;
            threadsSlider.value = config.threads;
            externalLinks.checked = config.externalLinks;
            urlInput.placeholder = config.placeholder;

            // Update display values
            document.getElementById('depthValue').textContent = config.depth;
            document.getElementById('threadsValue').textContent = config.threads;

            // Add visual feedback
            urlInput.focus();
        }
    }

    // Form submission
    const crawlForm = document.getElementById('crawlForm');
    const submitBtn = document.getElementById('submitBtn');
    const formProgress = document.getElementById('formProgress');

    crawlForm.addEventListener('submit', function(e) {
        e.preventDefault();

        // Show loading state
        submitBtn.classList.add('loading');
        formProgress.classList.add('active');

        // Simulate progress
        let progress = 0;
        const progressFill = document.querySelector('.progress-fill');
        const progressText = document.querySelector('.progress-text');

        const progressInterval = setInterval(() => {
            progress += Math.random() * 15;
            if (progress > 90) progress = 90;

            progressFill.style.width = progress + '%';

            if (progress < 30) {
                progressText.textContent = 'Validating URL...';
            } else if (progress < 60) {
                progressText.textContent = 'Setting up crawl configuration...';
            } else {
                progressText.textContent = 'Initializing crawl task...';
            }
        }, 200);

        // Simulate form submission
        setTimeout(() => {
            clearInterval(progressInterval);
            progressFill.style.width = '100%';
            progressText.textContent = 'Crawl task started successfully!';

            setTimeout(() => {
                // Reset form or redirect
                submitBtn.classList.remove('loading');
                formProgress.classList.remove('active');
                // window.location.href = 'dashboard.jsp';
            }, 1000);
        }, 3000);
    });

    // Reset button
    document.getElementById('resetBtn').addEventListener('click', function() {
        crawlForm.reset();
        document.getElementById('depthValue').textContent = '2';
        document.getElementById('threadsValue').textContent = '4';
        advancedOptions.classList.remove('active');
        advancedToggle.classList.remove('active');
    });
    document.addEventListener('DOMContentLoaded', function () {
        const icon = document.querySelector('.section-icon');
        if (!icon) return;

        document.addEventListener('mousemove', function (e) {
            const rect = icon.getBoundingClientRect();
            const iconX = rect.left + rect.width / 2;
            const iconY = rect.top + rect.height / 2;
            const angle = Math.atan2(e.clientY - iconY, e.clientX - iconX) * 180 / Math.PI;
            icon.style.transform = `rotate(${angle}deg)`;
        });
    });
});

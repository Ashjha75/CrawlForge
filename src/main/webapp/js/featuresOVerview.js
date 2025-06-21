document.addEventListener('DOMContentLoaded', function() {
    // Animated counters for all metric values
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

    document.querySelectorAll('.feature-card').forEach(card => observer.observe(card));

    // API Code Tabs
    const apiTabs = document.querySelectorAll('.api-tab');
    const codeBlocks = document.querySelectorAll('.code-content');

    apiTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const lang = this.dataset.lang;

            // Update active tab
            apiTabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');

            // Show corresponding code block
            codeBlocks.forEach(block => {
                block.classList.add('hidden');
                if (block.id === lang + 'Code') {
                    block.classList.remove('hidden');
                }
            });
        });
    });

    // Copy code functionality
    document.querySelector('.code-copy').addEventListener('click', function() {
        const activeCode = document.querySelector('.code-content:not(.hidden)');
        const text = activeCode.textContent;

        navigator.clipboard.writeText(text).then(() => {
            this.innerHTML = '<i class="bi bi-check"></i>';
            setTimeout(() => {
                this.innerHTML = '<i class="bi bi-clipboard"></i>';
            }, 2000);
        });
    });

    // Feature card hover effects
    document.querySelectorAll('.feature-card').forEach(card => {
        card.addEventListener('mouseenter', function() {
            const glow = this.querySelector('.feature-glow');
            if (glow) {
                glow.style.opacity = '1';
            }
        });

        card.addEventListener('mouseleave', function() {
            const glow = this.querySelector('.feature-glow');
            if (glow) {
                glow.style.opacity = '0';
            }
        });
    });

    // Network nodes interaction
    document.querySelectorAll('.network-node').forEach(node => {
        node.addEventListener('mouseenter', function() {
            const label = this.querySelector('.node-label');
            label.style.opacity = '1';
            label.style.transform = 'translateX(-50%) translateY(-5px)';
        });

        node.addEventListener('mouseleave', function() {
            const label = this.querySelector('.node-label');
            label.style.opacity = '0.8';
            label.style.transform = 'translateX(-50%) translateY(0)';
        });
    });
});

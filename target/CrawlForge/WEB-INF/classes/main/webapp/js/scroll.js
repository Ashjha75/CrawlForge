// Scroll Progress Indicator and Scroll to Top Button
document.addEventListener('DOMContentLoaded', function() {
    // Create scroll progress indicator
    const scrollIndicator = document.createElement('div');
    scrollIndicator.className = 'scroll-indicator';
    scrollIndicator.innerHTML = '<div class="scroll-progress"></div>';
    document.body.appendChild(scrollIndicator);

    // Create scroll to top button
    const scrollToTop = document.createElement('a');
    scrollToTop.href = '#';
    scrollToTop.className = 'scroll-to-top';
    scrollToTop.innerHTML = '<i class="bi bi-arrow-up"></i>';
    document.body.appendChild(scrollToTop);

    const scrollProgress = document.querySelector('.scroll-progress');

    // Update scroll progress
    function updateScrollProgress() {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        const scrollHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
        const scrollPercent = (scrollTop / scrollHeight) * 100;

        scrollProgress.style.width = scrollPercent + '%';

        // Show/hide scroll to top button
        if (scrollTop > 300) {
            scrollToTop.classList.add('visible');
        } else {
            scrollToTop.classList.remove('visible');
        }
    }

    // Scroll to top functionality
    scrollToTop.addEventListener('click', function(e) {
        e.preventDefault();
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    // Update on scroll
    window.addEventListener('scroll', updateScrollProgress);

    // Initial update
    updateScrollProgress();

    // Smooth scroll for all anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
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

# WebCrawler Design System Reference

## **Color Palette**
```css
/* Primary Colors */
--primary-green: #10a37f;
--primary-green-dark: #0d8465;
--primary-green-darker: #0a6b52;

/* Background Colors */
--bg-primary: #1f1f2b;
--bg-secondary: #2c2c3a;
--bg-tertiary: #40414f;

/* Text Colors */
--text-primary: #ffffff;
--text-secondary: #c5c5d2;
--text-muted: rgba(197, 197, 210, 0.8);

/* Accent Colors */
--accent-red: #ff4757;
--accent-orange: #ff9900;
--accent-green: #1db954;
--accent-dark: #000000;
```

## **Glass Morphism Effects**
```css
/* Main Glass Effect */
.glass-effect {
    background: linear-gradient(135deg, 
        rgba(16, 163, 127, 0.15) 0%, 
        rgba(16, 163, 127, 0.08) 50%, 
        rgba(16, 163, 127, 0.05) 100%);
    backdrop-filter: blur(15px);
    -webkit-backdrop-filter: blur(15px);
    border: 1px solid rgba(16, 163, 127, 0.2);
    box-shadow: 
        0 8px 32px rgba(0, 0, 0, 0.3),
        inset 0 1px 0 rgba(255, 255, 255, 0.1);
}

/* Secondary Glass Effect */
.glass-secondary {
    background: linear-gradient(135deg, 
        rgba(64, 65, 79, 0.9) 0%, 
        rgba(44, 44, 58, 0.8) 50%, 
        rgba(31, 31, 43, 0.9) 100%);
    backdrop-filter: blur(15px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    box-shadow: 
        0 8px 32px rgba(0, 0, 0, 0.3),
        inset 0 1px 0 rgba(255, 255, 255, 0.05);
}

/* Navbar Glass Effect */
.navbar-glass {
    background: linear-gradient(135deg, 
        rgba(31, 31, 43, 0.85) 0%, 
        rgba(44, 44, 58, 0.9) 50%, 
        rgba(64, 65, 79, 0.85) 100%);
    backdrop-filter: blur(12px);
    -webkit-backdrop-filter: blur(12px);
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    box-shadow: 
        0 8px 32px rgba(0, 0, 0, 0.15),
        inset 0 1px 0 rgba(255, 255, 255, 0.1);
}
```

## **Gradient Styles**
```css
/* Text Gradients */
.gradient-text {
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
    animation: gradient-shift 3s ease-in-out infinite;
}

/* Background Gradients */
.hero-background {
    background: linear-gradient(135deg, 
        #1f1f2b 0%, 
        #2c2c3a 25%, 
        #40414f 50%, 
        #2c2c3a 75%, 
        #1f1f2b 100%);
}

/* Button Gradients */
.gradient-button {
    background: linear-gradient(135deg, #10a37f 0%, #0d8465 100%);
    box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3);
}

/* Hover Button Gradient */
.gradient-button:hover {
    background: linear-gradient(135deg, #0d8465 0%, #0a6b52 100%);
    box-shadow: 0 6px 20px rgba(16, 163, 127, 0.4);
}
```

## **Animation Keyframes**
```css
/* Gradient Text Animation */
@keyframes gradient-shift {
    0%, 100% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
}

/* Floating Animation */
@keyframes float {
    0%, 100% { transform: translateY(0px) rotate(0deg); }
    50% { transform: translateY(-20px) rotate(1deg); }
}

/* Fade In Up Animation */
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

/* Pulse Animation */
@keyframes pulse-badge {
    0% { 
        transform: scale(1);
        box-shadow: 0 2px 8px rgba(255, 71, 87, 0.4);
    }
    50% { 
        transform: scale(1.1);
        box-shadow: 0 4px 15px rgba(255, 71, 87, 0.6);
    }
    100% { 
        transform: scale(1);
        box-shadow: 0 2px 8px rgba(255, 71, 87, 0.4);
    }
}

/* Chart Pulse */
@keyframes chart-pulse {
    0%, 100% { opacity: 0.5; transform: scaleX(0.8); }
    50% { opacity: 1; transform: scaleX(1); }
}

/* Rotation Animation */
@keyframes rotate {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
```

## **Hover Effects & Transitions**
```css
/* Smooth Transitions */
.smooth-transition {
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

/* Hover Lift Effect */
.hover-lift:hover {
    transform: translateY(-5px);
    box-shadow: 
        0 12px 40px rgba(16, 163, 127, 0.2),
        inset 0 1px 0 rgba(255, 255, 255, 0.15);
}

/* Gradient Sweep Effect */
.gradient-sweep {
    position: relative;
    overflow: hidden;
}

.gradient-sweep::before {
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

.gradient-sweep:hover::before {
    left: 100%;
}

/* Navigation Link Hover */
.nav-link-hover::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, 
        rgba(255, 255, 255, 0.05) 0%, 
        rgba(16, 163, 127, 0.15) 30%,
        rgba(16, 163, 127, 0.2) 50%,
        rgba(16, 163, 127, 0.15) 70%,
        rgba(255, 255, 255, 0.05) 100%);
    transition: left 0.5s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: -1;
}

.nav-link-hover:hover::before {
    left: 0;
}
```

## **Responsive Breakpoints**
```css
/* Mobile First Approach */
@media (max-width: 480px) {
    .hero-title { font-size: 2rem; }
    .card-padding { padding: 1.5rem; }
    .earnings-amount { font-size: 2rem; }
    .navbar-brand { font-size: 1.2rem; }
}

@media (max-width: 768px) {
    .hero-title { font-size: 2.5rem; }
    .hero-subtitle { font-size: 1.1rem; }
    .grid-layout { grid-template-columns: 1fr; }
    .service-icons { grid-template-columns: repeat(4, 1fr); }
    .earnings-amount { font-size: 2.5rem; }
    body { padding-top: 70px; }
}

@media (max-width: 991.98px) {
    .navbar-nav { text-align: center; }
    .nav-padding { padding: 0.8rem 1rem; }
    .notification-badge { top: 0.3rem; right: 0.5rem; }
}

@media (min-width: 992px) {
    .hero-title { font-size: 4rem; }
    .hero-cards { grid-template-columns: 1fr 1fr; }
}
```

## **Typography Scale**
```css
/* Font Sizes */
.hero-title { 
    font-size: 4rem; 
    font-weight: 800; 
    line-height: 1.2; 
}

.hero-subtitle { 
    font-size: 1.3rem; 
    font-weight: 400; 
    line-height: 1.6; 
}

.card-title { 
    font-size: 1.5rem; 
    font-weight: 600; 
}

.earnings-amount { 
    font-size: 3rem; 
    font-weight: 700; 
    text-shadow: 0 0 20px rgba(16, 163, 127, 0.3);
}

.body-text { 
    font-size: 0.9rem; 
    font-weight: 400; 
}

.navbar-brand { 
    font-size: 1.4rem; 
    font-weight: 700; 
}

/* Font Family */
body {
    font-family: 'Inter', sans-serif;
}
```

## **Border Radius Standards**
```css
.border-small { border-radius: 8px; }
.border-medium { border-radius: 12px; }
.border-large { border-radius: 20px; }
.border-pill { border-radius: 50px; }
.border-circle { border-radius: 50%; }
```

## **Shadow Definitions**
```css
/* Card Shadows */
.shadow-light { 
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); 
}

.shadow-medium { 
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3); 
}

.shadow-heavy { 
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.4); 
}

/* Colored Shadows */
.shadow-primary { 
    box-shadow: 0 4px 15px rgba(16, 163, 127, 0.3); 
}

.shadow-primary-hover { 
    box-shadow: 0 6px 20px rgba(16, 163, 127, 0.4); 
}

.shadow-accent { 
    box-shadow: 0 2px 8px rgba(255, 71, 87, 0.4); 
}

/* Inset Shadows for Glass Effect */
.inset-highlight { 
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1); 
}

.inset-highlight-strong { 
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.15); 
}
```

## **Grid & Layout Systems**
```css
/* Main Grid */
.hero-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 2rem;
}

/* Service Icons Grid */
.services-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1rem;
}

/* Responsive Grid */
@media (max-width: 768px) {
    .hero-grid {
        grid-template-columns: 1fr;
        gap: 1.5rem;
    }
    
    .services-grid {
        grid-template-columns: repeat(4, 1fr);
    }
}

/* Flexbox Utilities */
.flex-center { 
    display: flex; 
    align-items: center; 
    justify-content: center; 
}

.flex-between { 
    display: flex; 
    align-items: center; 
    justify-content: space-between; 
}

.flex-column { 
    display: flex; 
    flex-direction: column; 
}
```

## **Background Patterns**
```css
/* Grid Pattern */
.grid-pattern {
    background: url('data:image/svg+xml,');
}

/* Radial Gradients */
.radial-accents {
    background: 
        radial-gradient(circle at 20% 20%, rgba(16, 163, 127, 0.1) 0%, transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(16, 163, 127, 0.08) 0%, transparent 50%),
        radial-gradient(circle at 40% 60%, rgba(16, 163, 127, 0.05) 0%, transparent 50%);
}
```

## **Component Specific Styles**

### **Notification Badge**
```css
.notification-badge {
    position: absolute;
    top: 0.2rem;
    right: 0.2rem;
    background: linear-gradient(135deg, #ff4757 0%, #ff3742 100%);
    color: white;
    border-radius: 50%;
    width: 18px;
    height: 18px;
    font-size: 10px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 10;
    box-shadow: 
        0 2px 8px rgba(255, 71, 87, 0.4),
        inset 0 1px 0 rgba(255, 255, 255, 0.3);
    border: 1px solid rgba(255, 255, 255, 0.2);
    animation: pulse-badge 2s infinite;
}
```

### **Service Icons**
```css
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

/* Service Brand Colors */
.service-uber { background: linear-gradient(135deg, #000000, #333333); }
.service-amazon { background: linear-gradient(135deg, #ff9900, #ffb84d); }
.service-spotify { background: linear-gradient(135deg, #1db954, #1ed760); }
.service-netflix { background: linear-gradient(135deg, #e50914, #f40612); }
```

### **CTA Button**
```css
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
```

## **Animation Timing Functions**
```css
/* Smooth Easing */
.ease-smooth { transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1); }
.ease-bounce { transition-timing-function: cubic-bezier(0.68, -0.55, 0.265, 1.55); }
.ease-back { transition-timing-function: cubic-bezier(0.175, 0.885, 0.32, 1.275); }

/* Animation Delays */
.delay-100 { animation-delay: 0.1s; }
.delay-200 { animation-delay: 0.2s; }
.delay-300 { animation-delay: 0.3s; }
.delay-400 { animation-delay: 0.4s; }
.delay-500 { animation-delay: 0.5s; }
.delay-600 { animation-delay: 0.6s; }
```

## **Usage Instructions**

1. **Color Palette**: Use the CSS custom properties for consistent theming
2. **Glass Effects**: Apply `.glass-effect` or `.glass-secondary` classes for morphism
3. **Animations**: Use keyframes with appropriate delays for staggered effects
4. **Responsiveness**: Follow mobile-first approach with the provided breakpoints
5. **Gradients**: Combine with glass effects for modern UI appearance
6. **Hover States**: Always include smooth transitions for better UX
7. **Typography**: Use the defined scale for consistent text hierarchy
8. **Shadows**: Layer different shadow types for depth and dimension

This design system ensures consistency across all CrawlForge components while maintaining modern aesthetics and excellent user experience.


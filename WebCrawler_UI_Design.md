# 🧩 WebCrawler_Analytics – UI Design & CSS Guide (Inspired by Scrapy.org)

This markdown file contains the complete **UI + Design + CSS instructions** for the `WebCrawler_Analytics` project.  
It is written to be **clean**, **modern**, and compatible with future AI/code tools.

---

## 🎨 Color Palette (Inspired by Scrapy.org + ChatGPT)

| Element         | Color Code  | Notes                          |
|----------------|-------------|--------------------------------|
| Primary Accent  | `#10a37f`   | Vibrant green for buttons/icons |
| Background      | `#1f1f2b`   | Hero/primary dark section       |
| Section BG      | `#2b2c39`   | Alternating sections            |
| Card BG         | `#40414f`   | Panel containers                |
| Text (Primary)  | `#ffffff`   | Base white text                 |
| Text (Muted)    | `#c5c5d2`   | Subheadings, form labels        |
| Border Soft     | `#5c5f6e`   | Inputs, outlines                |
| CTA Hover       | `#0d8465`   | Hover state for buttons         |

---

## 🧱 Layout Design

- **Hero Section**: Full-width background with title + subtitle + CTA
- **NavBar**: Top-fixed, logo left, links right, collapsible
- **Cards/Grid**: Used for analytics/statistics and crawler form
- **Alternating Sections**: Testimonials or callouts with section background
- **Footer**: Centered, flat footer with muted text

---

## 🧩 Typography

| Element     | Font           | Size        | Weight     |
|-------------|----------------|-------------|------------|
| Main Font   | `Inter`, sans-serif | Base: `16px` | Regular |
| Heading H1  |                | `2.5rem`     | Bold       |
| Heading H2  |                | `2rem`       | Semi-bold  |
| Body Text   |                | `14-16px`    | Normal     |
| CTA Buttons | Uppercase      | `14px`       | Medium     |

---

## 🧰 CSS Utility & Style Classes

> These are reusable classes or styles for use across JSP pages.

```css
/* Text & Colors */
.text-white     { color: #ffffff; }
.text-muted     { color: #c5c5d2; }
.text-primary   { color: #10a37f; }

/* Backgrounds */
.bg-dark        { background-color: #1f1f2b; }
.bg-section     { background-color: #2b2c39; }
.bg-card        { background-color: #40414f; }
.bg-hero        { background: linear-gradient(to right, #1f1f2b, #2c2c3a); }

/* Borders & Rounding */
.border-soft    { border: 1px solid #5c5f6e; }
.rounded-2xl    { border-radius: 16px; }

/* Spacing & Layout */
.p-4            { padding: 1rem; }
.px-5           { padding-left: 3rem; padding-right: 3rem; }
.my-5           { margin-top: 3rem; margin-bottom: 3rem; }
.mx-auto        { margin-left: auto; margin-right: auto; }
.flex-center    { display: flex; justify-content: center; align-items: center; }

/* Hero Section */
.hero-title     { font-size: 2.5rem; font-weight: 700; color: #10a37f; }
.hero-subtitle  { font-size: 1.25rem; color: #c5c5d2; }

/* Buttons */
.btn {
  background-color: #10a37f;
  color: white;
  border: none;
  padding: 0.6rem 1.4rem;
  border-radius: 8px;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  transition: background-color 0.3s ease;
}
.btn:hover {
  background-color: #0d8465;
}

/* Cards */
.card-glass {
  background-color: rgba(64, 65, 79, 0.92);
  backdrop-filter: blur(6px);
  border: 1px solid #5c5f6e;
  border-radius: 12px;
  padding: 1.5rem;
}

# 🌐 CrawlForge - Enterprise Web Crawler Analytics Platform

![System Architecture](./src/main/webapp/img/Flowchart.jpg)
> CrawlForge is a robust Java Servlet + JSP enterprise application that performs intelligent web crawling, real-time analytics, and comprehensive content indexing at scale. Built with modern MVC architecture and enterprise-grade dashboard analytics.

## 🏗️ Enterprise Architecture Overview

### 🔄 **Core System Components**

#### **🟦 URL Management Layer**
- **URL Seeder** → Multi-threaded URL input processing with validation
- **URL Populator** → Advanced deduplication using bloom filters and hash-based storage
- **Seen URL Storage** → High-performance Redis/MySQL hybrid storage for processed URLs
- **URL Queue Management** → Priority-based queue with depth and domain-aware scheduling
- **URL Supplier Service** → Thread-safe URL distribution with load balancing

#### **🟨 Intelligent Fetch & Render Engine**
- **Multi-threaded HTML Fetcher** → Configurable thread pools with connection pooling
- **Content Renderer** → JavaScript-aware rendering with headless browser support
- **Robots.txt Compliance** → Automatic robots.txt parsing and politeness policies
- **Rate Limiting** → Domain-specific delays and request throttling

#### **🧩 Advanced URL Orchestration**
- **Smart URL Extractor** → Machine learning-enhanced link discovery
- **Duplicate Detection** → SHA-256 hash-based deduplication with bloom filters
- **Intelligent URL Filtering** → Pattern matching, domain whitelisting, and content-type filtering
- **Depth Management** → Configurable crawl depth with breadth-first traversal

#### **🟩 Enterprise Storage Solutions**
- **Analytics Database** → Real-time metrics aggregation with time-series data
- **Content Storage** → MySQL with full-text indexing and compression
- **Redis Cache Layer** → LRU eviction with TTL-based cache management
- **File Storage** → Distributed file system for large content assets

## 📊 **Real-Time Dashboard Analytics**

### **Dashboard Features**
- **📈 Live Crawl Monitoring** → Real-time session tracking with WebSocket updates
- **🎯 Performance Metrics** → Success rates, load times, and error analytics
- **📋 Content Analysis** → Keyword extraction, SEO metrics, and link analysis
- **👥 User Management** → Role-based access control with JWT authentication
- **📤 Data Export** → CSV/JSON export with scheduled reporting
- **🔍 Advanced Search** → Full-text search across crawled content with relevance scoring

### **Chart & Visualization Types**
- **Domain Distribution** → Interactive pie charts showing crawl coverage
- **Keyword Frequency** → Word clouds and bar charts for content analysis
- **Status Code Analytics** → HTTP response code distribution and error tracking
- **Performance Trends** → Time-series charts for load times and success rates
- **Link Analysis** → Network graphs showing internal/external link relationships

## 🧱 **Enterprise Project Structure**

```
CrawlForge/
│
├── src/main/java/
│   ├── com/controller/          # Servlet Controllers
│   │   ├── DashboardServlet.java
│   │   ├── CrawlController.java
│   │   └── AuthController.java
│   │
│   ├── com/service/             # Business Logic Layer
│   │   ├── DashboardService.java
│   │   ├── AnalyticsService.java
│   │   ├── CrawlerService.java
│   │   └── StatisticsCalculator.java
│   │
│   ├── com/dao/                 # Data Access Layer
│   │   ├── DashboardDataDAO.java
│   │   ├── CrawlStatisticsDAO.java
│   │   ├── UserActivityDAO.java
│   │   └── ChartDataDAO.java
│   │
│   ├── com/entity/              # JPA Entities
│   │   ├── User.java
│   │   ├── CrawlSession.java
│   │   ├── Page.java
│   │   ├── DashboardData.java
│   │   ├── CrawlStatistics.java
│   │   ├── ChartData.java
│   │   └── UserActivity.java
│   │
│   ├── com/utils/               # Utility Classes
│   │   ├── HibernateUtil.java
│   │   ├── JsonUtil.java
│   │   ├── CacheManager.java
│   │   └── ExportUtil.java
│   │
│   └── com/middleware/          # Security & Filters
│       ├── DashboardAuthFilter.java
│       └── SecurityFilter.java
│
├── src/main/webapp/
│   ├── jsp/                     # JSP Views
│   │   ├── dashboard/
│   │   ├── crawler/
│   │   └── auth/
│   │
│   ├── css/                     # Stylesheets
│   │   ├── dashboard.css
│   │   ├── charts.css
│   │   └── style.css
│   │
│   ├── js/                      # JavaScript
│   │   ├── dashboard.js
│   │   ├── charts.js
│   │   └── crawler.js
│   │
│   └── index.jsp               # Main Layout
│
├── src/main/resources/
│   ├── hibernate.cfg.xml
│   ├── log4j2.xml
│   └── application.properties
│
├── pom.xml                     # Maven Dependencies
└── README.md
```

## 🎨 **Modern UI Design System**

### **Color Palette (Scrapy-Inspired)**
| Element | Color Code | Usage | Accessibility |
|---------|------------|-------|---------------|
| Primary Accent | `#10a37f` | Buttons, CTAs, Success states | WCAG AA Compliant |
| Background Dark | `#1f1f2b` | Main background, Hero sections | High contrast |
| Section Background | `#2b2c39` | Card containers, Panels | Optimal readability |
| Card Background | `#40414f` | Dashboard cards, Forms | Glass morphism ready |
| Text Primary | `#ffffff` | Headings, Primary content | Maximum contrast |
| Text Muted | `#c5c5d2` | Subtext, Form labels | Subtle emphasis |
| Border Soft | `#5c5f6e` | Input borders, Dividers | Clean separation |

### **Advanced CSS Features**
- **🌟 Glass Morphism Effects** → Backdrop blur with transparency layers
- **🎭 Gradient Animations** → Smooth color transitions and hover states
- **📱 Responsive Grid System** → CSS Grid with mobile-first approach
- **🎯 Interactive Components** → Hover effects with cubic-bezier easing
- **🔄 Loading Animations** → Skeleton screens and progress indicators

## ⚙️ **Enterprise Technology Stack**

| Layer | Technology | Version | Purpose |
|-------|------------|---------|---------|
| **Frontend** | JSP + Bootstrap 5 | 5.3.0 | Responsive UI framework |
| **Backend** | Java Servlet | Java 21 | Enterprise web layer |
| **Crawler Engine** | JSoup + Multithreading | 1.17.2 | HTML parsing & extraction |
| **Database** | MySQL + Hibernate | 8.0.33 | Data persistence & ORM |
| **Caching** | Redis + ConcurrentHashMap | 7.0 | High-performance caching |
| **Security** | JWT + BCrypt | Latest | Authentication & authorization |
| **Build Tool** | Maven | 3.9.0 | Dependency management |
| **Testing** | JUnit 5 + Testcontainers | 5.10.0 | Unit & integration testing |
| **Logging** | SLF4J + Logback | 2.0.9 | Structured logging |
| **JSON Processing** | Jackson + Gson | 2.17.2 | Data serialization |

## 🚀 **Advanced Features & Capabilities**

### **🔧 Core Crawler Features**
- ✅ **Multi-threaded Architecture** → Configurable thread pools with work-stealing
- ✅ **Intelligent URL Discovery** → Machine learning-based link prioritization
- ✅ **Content Analysis** → NLP-powered keyword extraction and sentiment analysis
- ✅ **SEO Metrics** → Meta tag analysis, heading structure, and content quality scoring
- ✅ **Performance Monitoring** → Real-time load time tracking and bottleneck detection
- ✅ **Error Recovery** → Automatic retry logic with exponential backoff
- ✅ **Compliance Engine** → Robots.txt parsing and rate limiting per domain

### **📊 Dashboard Analytics**
- ✅ **Real-time Metrics** → Live session monitoring with WebSocket updates
- ✅ **Interactive Charts** → Chart.js integration with drill-down capabilities
- ✅ **User Activity Tracking** → Comprehensive user behavior analytics
- ✅ **Performance Insights** → Success rate trends and optimization recommendations
- ✅ **Export Capabilities** → Scheduled reports in CSV/JSON/PDF formats
- ✅ **Search & Filtering** → Advanced query builder with full-text search

### **🔐 Security & Compliance**
- ✅ **JWT Authentication** → Stateless token-based security
- ✅ **Role-based Access Control** → Granular permission management
- ✅ **Input Sanitization** → XSS and SQL injection prevention
- ✅ **HTTPS Enforcement** → SSL/TLS encryption for all communications
- ✅ **GDPR Compliance** → Data anonymization and user consent management
- ✅ **Audit Logging** → Comprehensive activity tracking and compliance reporting

## 📈 **Performance & Scalability**

### **🎯 Performance Metrics**
- **Throughput**: 1000+ pages/minute with optimal configuration
- **Concurrency**: 50+ simultaneous crawl sessions
- **Response Time**: 95% with JaCoCo
- **Code Quality**: SonarQube integration with quality gates
- **Security Scanning**: Automated dependency vulnerability checks
- **Performance Monitoring**: Application metrics with Micrometer

[//]: # ()
[//]: # (## 📚 **API Documentation**)

[//]: # ()
[//]: # (### **Dashboard API Endpoints**)

[//]: # (```)

[//]: # (GET  /api/dashboard?timeRange=24h&type=all)

[//]: # (POST /api/dashboard/refresh)

[//]: # (GET  /api/statistics?userId=123&timeRange=7d)

[//]: # (GET  /api/charts?type=domain-distribution)

[//]: # (POST /api/export?format=csv&timeRange=30d)

[//]: # (```)

[//]: # ()
[//]: # (### **Crawler API Endpoints**)

[//]: # (```)

[//]: # (POST /api/crawl/start)

[//]: # (GET  /api/crawl/status/{sessionId})

[//]: # (POST /api/crawl/pause/{sessionId})

[//]: # (POST /api/crawl/resume/{sessionId})

[//]: # (DELETE /api/crawl/cancel/{sessionId})

[//]: # (```)


## 📄 **License**

This project is licensed under the MIT License .

## 👨‍💻 **Author & Maintainer**

**Ashish Kumar Jha**  
*Full-Stack Developer | MERN Developer & Spring Boot Aficionado 🌱 | Crafting seamless, scalable web apps with modern tech and passion for clean, efficient code*

- 🌐 **Portfolio**: [your-portfolio.com](https://ashish5jha.github.io/portfolio/)
- 💼 **LinkedIn**: [linkedin.com/in/ashish-jha](https://www.linkedin.com/in/ashish5jha)
- 📧 **Email**: network.ashishjha@gmail.com
- 🐙 **GitHub**: [@ashish-jha](https://github.com/Ashjha75)
## 🙏 **Acknowledgments**

- **Scrapy.org** for design inspiration
- **Spring Framework** for architectural patterns
- **Apache Software Foundation** for excellent open-source tools
- **Java Community** for continuous innovation and support

### 🛠️ Installation & Build

Clone the repository and build:

```bash
git clone https://github.com/Ashjha75/CrawlForge.git
cd CrawlForge
mvn clean install
````

### 🚀 Running the Application

* Deploy the generated `.war` file from `target/` into your local Tomcat server.
* Or run using Jetty/Maven plugin for quick testing:

```bash
mvn jetty:run
```

*(Ensure your `hibernateUtils.java` and `application.properties` are configured properly.)*

### 🐳 Docker (MySQL & Redis)

If you prefer Docker for dependencies, start MySQL & Redis services:

```bash
docker-compose up -d
```

*(See `docker-compose.yml` in project root for configuration.)*

### 🔐 Environment Variables

| Variable      | Description        | Default Value |
| ------------- | ------------------ | ------------- |
| `DB_HOST`     | MySQL Host Address | `localhost`   |
| `DB_USER`     | MySQL Username     | `root`        |
| `DB_PASSWORD` | MySQL Password     | `root`        |
| `DB_NAME`     | Database Name      | `mydb`        |

---

**⭐ Star this repository if you find it helpful!**

*Built with ❤️ using Java, dedication, and countless cups of coffee ☕*

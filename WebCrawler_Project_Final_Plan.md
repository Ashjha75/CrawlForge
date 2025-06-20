
# WebCrawler_Analytics – Final Project Plan

This document outlines the **comprehensive final checklist** of UI, backend, data, and auxiliary features for the WebCrawler_Analytics project. Designed to demonstrate **2+ years Java developer proficiency**.

---

## 1. 🖥️ UI Layer

### 1.1 Layout & Navigation
- **Responsive Design**: Container (`max-width:1200px`), fluid grid
- **Navbar**: Logo, Home, Dashboard, Reports, Settings
- **Sidebar (Optional)**: Collapsible with icons for modules
- **Footer**: Simple copyright notice

### 1.2 Pages & Components
- **index.jsp**: 
  - URL Input Form
  - Depth selector, thread count slider
  - Start/Stop buttons
- **dashboard.jsp**:
  - Summary cards: Total Pages, Avg Load Time, Errors
  - Filter controls (domain, status)
- **results.jsp**:
  - DataTable of crawled pages (sortable, paginated)
  - Columns: URL, Title, Status, Load Time
- **analytics.jsp**:
  - Charts (keyword frequency, link types)
  - Use Chart.js or Google Charts
- **error.jsp**:
  - Friendly error messages
- **partials/**:
  - `header.jsp`, `footer.jsp`
  - Notification Toasts

### 1.3 Styling
- **Color Palette**: ChatGPT-inspired (see design-guide.md)
- **CSS Framework**: Bootstrap 5 via CDN
- **Utility Classes**: `.bg-dark`, `.text-primary`, `.p-4`, `.border-soft`, etc.
- **Custom CSS**: `style.css` for overrides

---

## 2. ⚙️ Backend Modules

### 2.1 Controller (Servlets)
- `CrawlController.java`: Start/stop crawl, pass parameters
- `DashboardServlet.java`: Fetch analytics summary
- `ResultsServlet.java`: Serve paginated page data
- `ExportServlet.java`: Export CSV/JSON of results

### 2.2 Service Layer
- `WebCrawlerService.java`: Core crawling logic
- `KeywordAnalyzer.java`: Keyword frequency analysis
- `LinkAnalyzer.java`: Internal vs External link counts
- `CrawlerScheduler.java`: Scheduled crawling jobs

### 2.3 Data Models (POJOs)
- `PageData.java`: URL, title, status, loadTime, metadata
- `KeywordCount.java`: keyword, count
- `LinkInfo.java`: sourceURL, targetURL, linkType
- `AnalyticsResult.java`: aggregated metrics

### 2.4 DAO Layer
- `DBConnection.java`: Singleton JDBC connection
- `PageDAO.java`: CRUD operations for pages
- `AnalyticsDAO.java`: Save/fetch analytics
- `SchedulerDAO.java`: Manage scheduled tasks

### 2.5 Utilities
- `HtmlUtils.java`: URL normalization, parsing helpers
- `ExportUtil.java`: CSV/JSON file generation
- `ConfigUtil.java`: Read properties (depth, threads, DB)

---

## 3. 🗄️ Data Layer

### 3.1 Database Schema (MySQL)
- **Tables**:
  - `pages (id, url, title, status_code, load_time, crawled_at)`
  - `links (id, page_id, target_url, link_type)`
  - `keywords (id, keyword, count, generated_at)`
  - `schedules (id, job_name, cron_expression, last_run)`
- **Normalization**: Foreign keys (pages→links, schedules)

### 3.2 JDBC Configuration
- Use `mysql-connector` driver (v8.3.0)
- Connection pooling (HikariCP optional)
- Transactions for batch inserts

---

## 4. 🧪 Testing & Quality

- **Unit Tests**: JUnit 5 for `service`, `dao`, `utils`
- **Integration Tests**: Embedded DB (H2) for DAO layer
- **Logging**: Log4j2 with `log4j2.xml` config
- **Exception Handling**:
  - Custom: `InvalidUrlException`, `CrawlLimitException`
  - Global error servlet/filter

---

## 5. 🚀 DevOps & Deployment

- **Maven**: Standard structure, WAR packaging
- **CI**: GitHub Actions — build, test, code analysis
- **Env Config**: `application.properties` for DB, crawler settings
- **Docker (Optional)**: Dockerfile + `docker-compose.yml` for MySQL

---

## 6. 🔮 Future Enhancements

- **User Authentication**: Login, role-based access
- **Pause/Resume**: Persist crawler state
- **Plugin Architecture**: Custom parsers via Factory
- **Dark/Light Toggle**: CSS theme switch
- **Graphical Charts**: Real-time updates via WebSocket

---

**Document created on June 20, 2025.**

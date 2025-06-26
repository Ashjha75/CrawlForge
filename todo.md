# ✅ WebCrawler Website - TODO List

> This `TODO.md` defines the **structure, responsibilities, and implementation plan** for each module and screen in our Java WebCrawler project.  
> It includes the required **JSP screens**, **backend logic**, **Java components**, and **tech stack mappings** for clean, scalable development.

---

## 🧩 Core Features Overview

| Feature                         | Description |
|--------------------------------|-------------|
| Multithreaded Web Crawler      | Crawl websites concurrently, respecting limits and policies |
| Content Extraction             | Extract meta info, links, headings, keyword stats |
| Data Persistence               | Store results in JSON / SQLite |
| Crawl Dashboard                | View real-time status of crawling tasks |
| Search Interface               | Search crawled content by keyword |
| Scheduler + Crawl Resumption  | Schedule periodic crawling, resume from last session |
| Robots.txt & Politeness Policy | Respect site rules and delays |

---

## 🖥️ Frontend - JSP Screens

### 1. `index.jsp` - Home / Seed Input Page
- 📌 **Purpose**: Accept seed URL, depth, and crawl options from the user.
- 🔧 **Backend**: Servlet `CrawlController.java` (Handles POST and triggers crawling thread).
- 💾 **Stores**: Crawl config and seed data in a session or temp file.

---

### 2. `progress.jsp` - Crawl Progress & Logs
- 📌 **Purpose**: Show live crawl status (pages crawled, current URL, error count).
- 🔧 **Backend**:
  - `ProgressServlet.java`: Uses shared progress object or Observer pattern.
  - Uses `CrawlMonitor.java` to get thread stats.
- 🔁 **AJAX Polling**: To update status every few seconds.

---

### 3. `results.jsp` - Crawled Page Viewer
- 📌 **Purpose**: Paginated display of crawled pages (title, URL, snippet, keywords).
- 🔧 **Backend**:
  - `ResultServlet.java`: Fetches from local JSON/SQLite via DAO.
  - Uses `PageDAO.java`, `PageModel.java`.

---

### 4. `search.jsp` - Keyword Search Page
- 📌 **Purpose**: Search for keywords in crawled data.
- 🔧 **Backend**:
  - `SearchController.java`: Uses indexed map or DB LIKE queries.
  - Supports relevance-sorted results.

---

### 5. `dashboard.jsp` - Analytics Dashboard
- 📌 **Purpose**: View top domains, most used keywords, and page crawl times.
- 🔧 **Backend**:
  - `StatsService.java`: Aggregates and caches crawl metrics.
  - Data passed to JSP via Servlet as JSON.
- 📊 **UI**: Integrate Chart.js or Google Charts.

---

### 6. `settings.jsp` - Scheduler, Resume, Crawl Limits
- 📌 **Purpose**: Configure crawl interval, delay per domain, resume toggle.
- 🔧 **Backend**:
  - `SettingsController.java`: Stores config in properties file or DB.
  - Uses `CrawlScheduler.java` for time-based triggers (ScheduledExecutorService).

---

### 7. `error.jsp` - Error and Exception Logs
- 📌 **Purpose**: View crawl or system errors.
- 🔧 **Backend**:
  - `ErrorLogger.java`: Logs to file or DB
  - `ErrorServlet.java`: Reads and shows error logs.

---

## ⚙️ Backend Components (Java Classes)

| Module                  | Responsibility |
|-------------------------|----------------|
| `CrawlerEngine.java`    | Main controller managing threads and URL queue |
| `CrawlTask.java`        | Runnable task for each URL |
| `URLManager.java`       | Manages visited URLs and priority queue |
| `RobotsHandler.java`    | Reads and parses robots.txt rules |
| `HTMLParser.java`       | Extracts title, links, metadata using JSoup |
| `CrawlConfig.java`      | Holds config like depth, delay, thread pool size |
| `CrawlScheduler.java`   | Manages periodic crawling with resume support |
| `PageModel.java`        | POJO for parsed page data |
| `PageDAO.java`          | Handles saving and fetching from SQLite/JSON |
| `SearchEngine.java`     | Provides keyword-based search on indexed data |
| `StatsService.java`     | Provides analytics from stored data |
| `ErrorLogger.java`      | Logs system/crawl errors to file or DB |

---

## 💾 Data Storage Options

| Data Type            | Storage Format | Tool/Library |
|----------------------|----------------|--------------|
| Crawled Pages        | JSON or SQLite | Gson, JDBC   |
| Settings/Configs     | Properties file| Java IO      |
| Error Logs           | Text file or DB| Log4J / Custom|
| Scheduler Logs       | Flat file      | ScheduledExecutorService |

---

## 📦 Libraries and Tools Used

| Purpose                | Tool           |
|------------------------|----------------|
| HTML Parsing           | [JSoup](https://jsoup.org/) |
| JSON Handling          | Jackson or Gson |
| Web Server             | Tomcat + JSP/Servlets |
| Logging                | Log4j or Java Logger |
| DB Storage             | SQLite + JDBC |
| Thread Pool Management | ExecutorService |
| Frontend Charts        | Chart.js (in JSP) |

---

## 🔐 Security Considerations
- Validate and sanitize all URL inputs
- Enforce `robots.txt` compliance
- Limit crawling scope to avoid legal issues
- Add throttling for thread pool to avoid overloading host

---

## 📁 Suggested Folder Structure


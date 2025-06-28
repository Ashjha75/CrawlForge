# CrawlForge Dashboard – Backend Implementation Blueprint

## 0. Objective

Build a resilient, real-time dashboard API layer that turns raw crawl data into user-friendly analytics cards, charts and tables while following MVC and enterprise best-practices.

## 1. Target Folder Structure (create only what you still miss)

```
src/main/java/com/crawlforge/
├── controller/
│   └── DashboardServlet.java
├── service/
│   ├── DashboardService.java
│   ├── AnalyticsService.java
│   └── StatisticsCalculator.java
├── dao/
│   ├── CrawlSessionDAO.java
│   ├── PageDAO.java
│   └── AnalyticsDAO.java
├── model/
│   ├── DashboardData.java
│   ├── CrawlStatistics.java
│   ├── ChartData.java
│   └── UserActivity.java
├── utils/
│   ├── CacheManager.java
│   └── ExportUtil.java
└── middleware/
    └── DashboardAuthFilter.java
```

## 2. Incremental Road-map

| Phase                     | Goal                                                     | Key Files                                                              | Primary Java Topics                                                                         |
| ------------------------- | -------------------------------------------------------- | ---------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| **1. Data Access Layer**  | Fast, unit-tested DAO queries for dashboard metrics      | CrawlSessionDAO, PageDAO, AnalyticsDAO                                 | JPA Criteria, HQL, Generics, Connection Pool tuning                                         |
| **2. Domain Models**      | Immutable POJOs to transport metrics                     | DashboardData, CrawlStatistics, ChartData                              | Lombok @Builder, Value Objects, Encapsulation                                               |
| **3. Service Layer**      | Asynchronous data assembly & caching                     | DashboardService, AnalyticsService, StatisticsCalculator, CacheManager | CompletableFuture, ExecutorService, Strategy/Builder patterns, Thread-safety, Java Time API |
| **4. Presentation Layer** | Secure servlet that returns JSON to your existing JSP/JS | DashboardServlet, DashboardAuthFilter                                  | MVC pattern, Servlet Filters, JSON (Jackson), Error handling                                |
| **5. Enhancements**       | Export + WebSocket updates                               | ExportUtil, optional WebSocket endpoint                                | Java IO/NIO, Streaming API, Observer pattern                                                |

## 3. Detailed Phase Checklists

### Phase 1 – DAO Layer

1. Derive KPI queries (total sessions, active sessions, pages/day, error counts).
2. Implement **AnalyticsDAO** methods returning `Map` or DTOs.
3. Add pagination helpers in **PageDAO** for results page.
4. Unit-test each DAO against H2 in-memory DB.

*Edge cases*

* Handle empty result sets gracefully.
* Guard against `UnknownEntityException` by adding `@Entity` and `<mapping/>` entries in `hibernate.cfg.xml`.
* Wrap queries in read-only transactions.

### Phase 2 – Domain Models

1. Use Lombok’s `@Builder`, `@Getter`, `@ToString` (avoid `@Data` in JPA entities).
2. Embed lightweight static `ChartData` record inside DashboardData.
3. Keep DTOs **immutable**; no setters after build.

*Edge cases*

* Guard against `null` lists with `Collections.emptyList()`.
* Serialize `LocalDate`/`LocalDateTime` via Jackson’s JavaTimeModule.

### Phase 3 – Service Layer

1. Spin a **fixed thread-pool** inside DashboardService: `Executors.newFixedThreadPool(4)`.
2. Fire parallel `CompletableFuture` calls to each DAO.
3. Aggregate via `allOf().thenApply(...)`.
4. Pipe result through **StatisticsCalculator** strategies for:

    * Success-rate
    * Average pages per session
    * Domain distribution chart data
5. Cache final `DashboardData` in **CacheManager** (ConcurrentHashMap + file backup).

*Edge cases*

* Timeout any DAO future > 5 seconds; default to cached value.
* Evict cache on user logout or after 15 minutes.
* Sanitize color hex codes to avoid XSS when injected into JS.

### Phase 4 – Presentation Layer

1. **DashboardAuthFilter** validates JWT, injects `currentUserId`.
2. **DashboardServlet**:

    * GET without `Accept: application/json` ➔ forwards to existing `dashboard.jsp`.
    * GET/POST with `Accept: application/json` ➔ returns JSON payload.
3. Use `try (PrintWriter w = resp.getWriter())` to auto-close I/O stream.
4. Serve ETag headers and `Cache-Control: no-store`.

*Edge cases*

* Reject unauthenticated calls with 401 + toast-friendly JSON.
* Compress large JSON with GZIP filter once payload > 50 KB.
* Protect against `NumberFormatException` on request params.

### Phase 5 – Enhancements

1. **ExportUtil** generates CSV/JSON snapshots using streaming writer.
2. Optional **WebSocket endpoint** (`/ws/progress`) pushes crawl-progress events.
3. Add dark-mode color palette mapping.

## 4. Testing & Quality Gates

| Layer   | Test Type                   | Tools                   |
| ------- | --------------------------- | ----------------------- |
| DAO     | Unit + integration          | JUnit 5, Testcontainers |
| Service | Concurrency & timeout tests | Awaitility              |
| Servlet | End-to-end                  | Rest-assured, Mockito   |
| Cache   | TTL expiry                  | JUnit + `Thread.sleep`  |

## 5. Performance & Scalability Safeguards

* Push heavy aggregations down to SQL (`GROUP BY`/`COUNT`).
* Paginate large result sets; send at most 50 rows per call.
* Use asynchronous I/O for file exports.

## 6. Security & Compliance Checklist

* Sanitize user-supplied filters (prevent injection).
* Use `HttpOnly` + `Secure` flags on JWT cookie.
* Enforce role checks.
* Log only hashed user IDs (GDPR).

## 7. Documentation & Knowledge Transfer

1. Update `README.md` with API endpoints.
2. Add UML sequence diagram (PlantUML).
3. Inline JavaDoc for service methods.
4. Create Confluence page summarizing architecture.

## 8. Suggested Weekly Timeline

| Week | Deliverable                      |
| ---- | -------------------------------- |
| 1    | DAO layer + unit tests           |
| 2    | Domain models + service skeleton |
| 3    | Integrate DAO + caching          |
| 4    | Servlet + filter, hook front-end |
| 5    | Export + optional WebSocket      |
| 6    | Final review + hand-off          |


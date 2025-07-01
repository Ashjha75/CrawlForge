# 🧠 Project Data Model Documentation – CrawlForge
---

## 📦 1. Entity Details and Relationships

### 1.1. `User` Entity

**Table Name:** `users`  
Represents a system user with comprehensive authentication and activity tracking.

#### Fields:

- `userId (PK)` – Auto-generated.
- `username` *(UK)* – Unique username (3-30 chars, alphanumeric + underscore).
- `email` *(UK)* – Unique email with validation.
- `firstName`, `lastName` – User's name (2-50 chars each).
- `passwordHash` – BCrypt hashed password (60 chars).
- `status` *(Enum: ACTIVE, SUSPENDED, INACTIVE)* – Current user state.
- `emailVerified` *(Boolean)* – Email confirmation status.
- `lastLoginAt` *(Timestamp)* – Last login time.
- `loginAttempts` – Count of failed attempts (max 5).
- `newsletterSubscribed` *(Boolean)* – Marketing consent.
- `createdAt`, `updatedAt` – Auditing timestamps.

#### Relationships:

- `One-to-Many` with **CrawlSession** (a user can have multiple sessions).
- `Many-to-Many` with **Role** via `user_roles` table.
- `One-to-Many` with **DashboardData** (user-specific dashboard analytics).
- `One-to-Many` with **CrawlStatistics** (user performance metrics).
- `One-to-Many` with **ChartData** (user's chart configurations).
- `One-to-Many` with **UserActivity** (comprehensive activity tracking).

#### Business Methods:

- `getFullName()` – Concatenates first and last name.
- `isActive()` – Checks if user status is ACTIVE.
- `canLogin()` – Validates active status and login attempts < 5.
- `incrementLoginAttempts()` – Increases failed login counter.
- `resetLoginAttempts()` – Resets counter on successful login.
- `hasRole(String roleName)` – Checks role membership.
- `isAdmin()` – Convenience method for admin role check.

---

### 1.2. `Role` Entity

**Table Name:** `roles`  
Defines roles for users (e.g., ADMIN, USER, ANALYST).

#### Fields:

- `roleId (PK)` – Auto-generated.
- `name (UK)` – Unique role name (max 50 chars).
- `description` – Role description (max 255 chars).

#### Relationships:

- `Many-to-Many` with **User** via `user_roles` join table.

---

### 1.3. `CrawlSession` Entity

**Table Name:** `crawl_sessions`  
Represents a crawling session initiated by a user with comprehensive configuration and tracking.

#### Fields:

- `sessionId (PK)` – Auto-generated.
- `user (FK)` – Reference to User entity.
- `sessionName` – User-defined session name (max 100 chars).
- `seedUrl` – Starting URL for crawl (max 500 chars).
- `maxDepth` – Maximum crawl depth (default: 3).
- `maxPages` – Maximum pages to crawl (default: 100).
- `threadCount` – Number of concurrent threads (default: 5).
- `delayMs` – Delay between requests in milliseconds (default: 1000).
- `status` *(Enum: PENDING, RUNNING, PAUSED, COMPLETED, FAILED, CANCELLED)* – Session state.
- `pagesCrawled` – Count of successfully crawled pages.
- `pagesFailed` – Count of failed page requests.
- `totalLinksFound` – Total links discovered during crawl.
- `startedAt`, `completedAt` – Session timing.
- `errorMessage` – Last error message (max 1000 chars).
- `createdAt`, `updatedAt` – Auditing timestamps.

#### Relationships:

- `Many-to-One` with **User**.
- `One-to-Many` with **Page**.

#### Business Methods:

- `isActive()` – Checks if status is RUNNING or PAUSED.
- `canResume()` – Validates if session can be resumed.
- `getSuccessRate()` – Calculates success percentage.

---

### 1.4. `Page` Entity

**Table Name:** `pages`  
Represents a single web page discovered during a crawl with comprehensive SEO and performance metrics.

#### Fields:

- `pageId (PK)` – Auto-generated.
- `crawlSession (FK)` – Reference to CrawlSession.
- `url` – Page URL (max 1000 chars, unique per session).
- `title` – Page title (max 500 chars).
- `metaDescription` – Meta description (max 1000 chars).
- `metaKeywords` – Meta keywords (max 500 chars).
- `contentType` – MIME type (max 100 chars).
- `statusCode` – HTTP response code.
- `contentLength` – Page size in bytes.
- `loadTimeMs` – Page load time in milliseconds.
- `depthLevel` – Crawl depth level.
- `wordCount` – Total word count.
- `internalLinksCount` – Count of internal links.
- `externalLinksCount` – Count of external links.
- `imageCount` – Count of images.
- `h1Tags`, `h2Tags` – Heading tags content (max 1000 chars each).
- `errorMessage` – Error details if crawl failed (max 500 chars).
- `crawledAt` – Timestamp when page was crawled.

#### Relationships:

- `Many-to-One` with **CrawlSession**.
- `One-to-Many` with **Link** (as source and target).
- `One-to-Many` with **Keyword**.

#### Business Methods:

- `isSuccessful()` – Checks if status code is 2xx.
- `isRedirect()` – Checks if status code is 3xx.
- `isError()` – Checks if status code is 4xx or 5xx.
- `getDomain()` – Extracts domain from URL.

---

### 1.5. `Link` Entity

**Table Name:** `links`  
Represents a hyperlink discovered on a page with detailed link analysis.

#### Fields:

- `linkId (PK)` – Auto-generated.
- `sourcePage (FK)` – Page containing the link.
- `targetPage (FK)` – Target page if crawled (nullable).
- `targetUrl` – Target URL (max 1000 chars).
- `anchorText` – Link text (max 500 chars).
- `linkType` *(Enum: INTERNAL, EXTERNAL, MAILTO, JAVASCRIPT, ANCHOR)* – Link classification.
- `relAttribute` – Rel attribute value (max 100 chars).
- `titleAttribute` – Title attribute value (max 200 chars).
- `discoveredAt` – When link was discovered.

#### Relationships:

- `Many-to-One` with **Page** (source).
- `Many-to-One` with **Page** (target, optional).

---

### 1.6. `Keyword` Entity

**Table Name:** `keywords`  
Represents an extracted keyword from a page with frequency and density analysis.

#### Fields:

- `keywordId (PK)` – Auto-generated.
- `page (FK)` – Reference to Page entity.
- `keyword` – Extracted keyword (max 100 chars).
- `frequency` – Occurrence count.
- `density` – Keyword density percentage.
- `keywordType` *(Enum: TITLE, META, HEADING, CONTENT, ALT_TEXT)* – Source classification.

#### Relationships:

- `Many-to-One` with **Page**.

---

## 📊 2. Dashboard Analytics Entities

### 2.1. `DashboardData` Entity

**Table Name:** `dashboard_data`  
Aggregated dashboard metrics and analytics for real-time monitoring.

#### Fields:

- `dashboardId (PK)` – Auto-generated.
- `user (FK)` – Reference to User entity.
- `totalSessions`, `activeSessions`, `completedSessions` – Session counts.
- `totalPagesCrawled`, `totalPagesFailed`, `totalLinksFound` – Crawl metrics.
- `averageSuccessRate`, `averagePagesPerSession`, `averageLoadTimeMs` – Performance metrics.
- `totalErrorCount` – Error count.
- `lastCrawlTime` – Most recent crawl timestamp.
- `crawlsToday`, `crawlsThisWeek`, `crawlsThisMonth` – Time-based counts.
- `domainDistributionJson`, `statusCodeDistributionJson` – Chart data as JSON.
- `keywordFrequencyJson`, `linkTypeDistributionJson` – Analytics data as JSON.
- `dailyCrawlTrendJson`, `recentSessionsJson` – Trend data as JSON.
- `topDomainsJson`, `popularKeywordsJson` – Top items as JSON.
- `userTotalSessions` – User-specific session count.
- `userLastActivity` – User's last activity timestamp.
- `systemHealthJson` – System health metrics as JSON.
- `timeRange` – Data aggregation period (24h, 7d, 30d, all).
- `dashboardType` – Dashboard variant (STANDARD, ADMIN, ANALYTICS).
- `refreshIntervalMinutes` – Auto-refresh interval.
- `isCached`, `cacheExpiresAt` – Caching metadata.
- `generatedAt`, `updatedAt` – Timestamps.
- `dataVersion` – Schema version.

#### Relationships:

- `Many-to-One` with **User**.

#### Business Methods:

- `hasRecentActivity()` – Checks for activity in last 24 hours.
- `isSystemHealthy()` – Validates system health status.
- `getOverallSuccessRate()` – Calculates overall success percentage.
- `hasActiveWork()` – Checks for active sessions.
- `isCacheValid()` – Validates cache expiry.

---

### 2.2. `CrawlStatistics` Entity

**Table Name:** `crawl_statistics`  
Comprehensive statistical analysis and performance metrics.

#### Fields:

- `statisticsId (PK)` – Auto-generated.
- `user (FK)` – Reference to User entity.
- `totalSessions`, `activeSessions`, `completedSessions`, `failedSessions` – Session statistics.
- `pausedSessions`, `cancelledSessions` – Additional session states.
- `totalPagesCrawled`, `totalPagesFailed`, `totalPagesSuccessful` – Page metrics.
- `overallSuccessRate`, `averagePagesPerSession` – Performance calculations.
- `maxPagesInSession`, `minPagesInSession` – Session extremes.
- `averageLoadTimeMs`, `totalLoadTimeMs` – Performance timing.
- `averageSessionDurationMinutes` – Session duration analysis.
- `fastestPageLoadMs`, `slowestPageLoadMs` – Load time extremes.
- `totalLinksFound`, `totalInternalLinks`, `totalExternalLinks` – Link analytics.
- `averageLinksPerPage`, `maxLinksOnPage` – Link distribution.
- `totalKeywordsExtracted`, `averageKeywordsPerPage` – Keyword metrics.
- `averageKeywordDensity`, `uniqueKeywordCount` – Content analysis.
- `total2xxResponses`, `total3xxResponses`, `total4xxResponses`, `total5xxResponses` – HTTP status analytics.
- `mostCommonStatusCode`, `totalTimeouts`, `totalConnectionErrors` – Error analysis.
- `averageDepthReached`, `maxDepthReached` – Crawl depth metrics.
- `averageThreadUtilization`, `totalThreadHours` – Threading analysis.
- `firstCrawlTime`, `lastCrawlTime` – Time boundaries.
- `crawlsLast24Hours`, `crawlsLast7Days`, `crawlsLast30Days` – Recent activity.
- `pagesLast24Hours`, `pagesLast7Days`, `pagesLast30Days` – Page trends.
- `uniqueDomainsCount`, `mostCrawledDomain`, `mostCrawledDomainCount` – Domain analysis.
- `htmlPagesCount`, `imageLinksCount`, `documentLinksCount`, `otherContentCount` – Content type breakdown.
- `timeRange`, `statisticsType` – Configuration.
- `generatedAt`, `updatedAt`, `dataVersion` – Metadata.

#### Relationships:

- `Many-to-One` with **User**.

#### Business Methods:

- `getSessionCompletionRate()` – Session success percentage.
- `getErrorRate()` – Error percentage calculation.
- `getLinkDiscoveryRate()` – Links per page ratio.
- `hasRecentActivity()` – Recent activity check.
- `getPerformanceGrade()` – A+ to D performance rating.

---

### 2.3. `ChartData` Entity

**Table Name:** `chart_data`  
Chart.js compatible data structures for dashboard visualizations.

#### Fields:

- `chartId (PK)` – Auto-generated.
- `user (FK)` – Reference to User entity.
- `chartTitle` – Chart display title (max 200 chars).
- `chartType` *(Enum: BAR, LINE, PIE, DOUGHNUT, RADAR, POLAR_AREA, SCATTER, BUBBLE, MIXED)* – Chart type.
- `description` – Chart description (max 500 chars).
- `timeRange` – Data time range (24h, 7d, 30d, all).
- `datasetsJson` – Chart.js datasets as JSON.
- `labelsJson` – Chart labels as JSON.
- `optionsJson` – Chart configuration as JSON.
- `colorsJson` – Color scheme as JSON.
- `rawDataJson` – Raw data for export.
- `metadataJson` – Additional metadata.
- `totalDataPoints`, `maxValue`, `minValue`, `averageValue` – Data statistics.
- `topCategory`, `topCategoryValue` – Dominant category.
- `isRealtime` – Real-time update flag.
- `refreshIntervalSeconds` – Update frequency.
- `isCached`, `cacheExpiresAt` – Caching metadata.
- `generatedAt`, `updatedAt`, `dataVersion` – Timestamps.

#### Relationships:

- `Many-to-One` with **User**.

#### Business Methods:

- `hasData()` – Validates data availability.
- `isRealtimeChart()` – Checks real-time mode.
- `getFormattedTitle()` – Returns display title.
- `getDataRange()` – Calculates value range.
- `isLargeDataset()` – Checks if data exceeds 1000 points.

---

### 2.4. `UserActivity` Entity

**Table Name:** `user_activity`  
Comprehensive user behavior and activity analytics.

#### Fields:

- `activityId (PK)` – Auto-generated.
- `user (FK)` – Reference to User entity.
- `totalSessions`, `activeSessions`, `completedSessions` – Session activity.
- `failedSessions`, `pausedSessions`, `cancelledSessions` – Session states.
- `lastLoginAt`, `lastCrawlActivity`, `firstCrawlActivity` – Activity timestamps.
- `sessionsToday`, `sessionsThisWeek`, `sessionsThisMonth`, `sessionsThisYear` – Time-based metrics.
- `totalPagesCrawled`, `totalPagesFailed` – Crawling performance.
- `averageSuccessRate`, `personalBestSuccessRate` – Success metrics.
- `totalLinksDiscovered`, `totalKeywordsExtracted` – Discovery metrics.
- `totalCrawlingTimeMinutes`, `averageSessionDurationMinutes` – Time analysis.
- `longestSessionDurationMinutes`, `shortestSessionDurationMinutes` – Duration extremes.
- `uniqueDomainsExplored`, `mostCrawledDomain`, `mostCrawledDomainCount` – Domain preferences.
- `activityPatternJson`, `hourlyDistributionJson` – Behavioral patterns as JSON.
- `weeklyPatternJson`, `monthlyTrendJson` – Temporal patterns as JSON.
- `recentSessionsJson`, `achievementsJson` – Recent activity as JSON.
- `preferencesJson`, `frequentSeedUrlsJson`, `customSettingsJson` – User preferences as JSON.
- `productivityScore`, `productivityGrade` – Performance scoring.
- `productivityInsightsJson` – Insights as JSON.
- `loginAttempts`, `emailVerified`, `newsletterSubscribed` – Account status.
- `accountCreatedAt`, `daysSinceRegistration` – Account age.
- `accountTier` – User tier (BASIC, PREMIUM, ENTERPRISE).
- `timeRange`, `activityType` – Configuration.
- `generatedAt`, `updatedAt`, `dataVersion` – Metadata.

#### Relationships:

- `Many-to-One` with **User**.

#### Business Methods:

- `isActiveUser()` – Checks recent activity (last 7 days).
- `isPowerUser()` – Validates high-performance user (50+ sessions, 85%+ success).
- `getActivityLevel()` – Returns activity classification (VERY_HIGH, HIGH, MEDIUM, LOW, INACTIVE).
- `getSessionCompletionRate()` – Session completion percentage.
- `getAveragePagesPerSession()` – Pages per session ratio.
- `hasRecentActivity()` – Recent activity check (last 24 hours).
- `getExperienceLevel()` – User experience classification (BEGINNER to EXPERT).
- `isEligibleForUpgrade()` – Upgrade eligibility check.

---

## 🔧 3. Core Use Cases

### 3.1. User Management

- **Register:** Create new user, assign default role, initialize activity tracking.
- **Login:** Validate credentials, update lastLoginAt, reset login attempts, log activity.
- **Profile Update:** Edit user details, update preferences.
- **Role Assignment:** Admin adds/removes roles, updates permissions.
- **Account Security:** Monitor login attempts, handle suspensions.

### 3.2. Crawl Session Management

- **Create/Start/Pause/Resume/Cancel** crawl sessions with comprehensive state management.
- **Track Progress** using real-time metrics and performance indicators.
- **Configuration Management:** Set crawl parameters, threading, delays.
- **Error Handling:** Capture and analyze crawl failures.

### 3.3. Crawled Data Analysis

- **Content Analysis:** View crawled pages, links, keywords with SEO insights.
- **Performance Analytics:** Analyze load times, success rates, error patterns.
- **Link Structure Analysis:** Internal vs external link mapping.
- **Keyword Analytics:** Frequency analysis, density calculations, content optimization.
- **Domain Analysis:** Domain-specific performance and coverage metrics.

### 3.4. Dashboard Analytics

- **Real-time Monitoring:** Live session tracking with WebSocket updates.
- **Performance Metrics:** Success rates, load times, error analytics.
- **Chart Visualizations:** Interactive charts for domain distribution, keyword frequency.
- **User Activity Tracking:** Comprehensive user behavior analytics.
- **Export Capabilities:** CSV/JSON export with scheduled reporting.
- **Comparative Analysis:** User performance vs platform averages.

### 3.5. Admin Panel

- **User Management:** Create, update, delete users and roles.
- **System Monitoring:** Track system health, resource utilization.
- **Crawl Moderation:** Monitor and control crawl behavior.
- **Analytics Overview:** Platform-wide statistics and trends.
- **Security Management:** Monitor login attempts, manage suspensions.

---

## 📐 4. Business Logic & Rules

### 4.1. Security Rules
- **Login Restrictions:** Max 5 failed attempts → SUSPENDED status.
- **Session Security:** JWT tokens with configurable expiration.
- **Input Validation:** XSS prevention, SQL injection protection.
- **Role-based Access:** Granular permission system.

### 4.2. Crawling Rules
- **Status Transitions:** PENDING → RUNNING → COMPLETED/FAILED/PAUSED/CANCELLED.
- **Politeness Policy:** Respect robots.txt, enforce delays between requests.
- **Resource Limits:** Configurable max pages, depth, thread count.
- **Error Handling:** Automatic retry logic with exponential backoff.

### 4.3. Analytics Rules
- **Keyword Density:** `(frequency / totalWords) * 100`.
- **Success Rate:** `(successfulPages / totalPages) * 100`.
- **Link Categorization:**
    - `INTERNAL`: Same domain
    - `EXTERNAL`: Different domain
    - `MAILTO`, `JAVASCRIPT`, `ANCHOR`: Special types
- **Performance Grading:** A+ (95%+) to D (<70%) based on success rates.

### 4.4. Caching Rules
- **Dashboard Cache:** 15-minute TTL with file backup.
- **Chart Cache:** Configurable refresh intervals (30-300 seconds).
- **Statistics Cache:** Daily aggregation with incremental updates.
- **User Activity:** Real-time updates with hourly aggregation.

### 4.5. Data Retention
- **Audit Timestamps:** `createdAt`, `updatedAt` auto-managed by JPA.
- **Data Versioning:** Schema version tracking for migrations.
- **Cleanup Policies:** Configurable retention periods for old data.

---

## 🚀 5. Future Enhancements

### 5.1. Advanced Features
- **Scheduled Crawling:** Cron-based recurring crawls with resume capability.
- **Distributed Crawling:** Message queue integration for horizontal scaling.
- **Machine Learning:** Content classification, sentiment analysis.
- **API Integration:** RESTful APIs for third-party integrations.

### 5.2. Analytics Enhancements
- **Predictive Analytics:** Crawl performance prediction models.
- **Anomaly Detection:** Automatic detection of unusual patterns.
- **Custom Dashboards:** User-configurable dashboard layouts.
- **Advanced Reporting:** Scheduled reports with custom metrics.

### 5.3. Infrastructure
- **Microservices:** Service decomposition for better scalability.
- **Container Deployment:** Docker and Kubernetes support.
- **Cloud Integration:** AWS/Azure deployment options.
- **Monitoring:** Application performance monitoring (APM) integration.

---
```
## 🧩 6. Entity Relationship Diagram (Mermaid)
```mermaid 
USER ||--o{ CRAWL_SESSION : has
USER ||--o{ DASHBOARD_DATA : owns
USER ||--o{ CRAWL_STATISTICS : tracks
USER ||--o{ CHART_DATA : creates
USER ||--o{ USER_ACTIVITY : monitors
CRAWL_SESSION ||--o{ PAGE : contains
PAGE ||--o{ KEYWORD : has
PAGE ||--o{ LINK : has_links
LINK }o--|| PAGE : targets
USER }o--o{ ROLE : assigned
```

    USER {
        Long userId PK
        String username UK
        String email UK
        String passwordHash
        String firstName
        String lastName
        UserStatus status
        Boolean emailVerified
        LocalDateTime lastLoginAt
        Integer loginAttempts
        Boolean newsletterSubscribed
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    
    ROLE {
        Long roleId PK
        String name UK
        String description
    }
    
    CRAWL_SESSION {
        Long sessionId PK
        Long userId FK
        String sessionName
        String seedUrl
        Integer maxDepth
        Integer maxPages
        Integer threadCount
        Integer delayMs
        CrawlStatus status
        Integer pagesCrawled
        Integer pagesFailed
        Integer totalLinksFound
        LocalDateTime startedAt
        LocalDateTime completedAt
        String errorMessage
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    
    PAGE {
        Long pageId PK
        Long sessionId FK
        String url
        String title
        String metaDescription
        String metaKeywords
        String contentType
        Integer statusCode
        Long contentLength
        Long loadTimeMs
        Integer depthLevel
        Integer wordCount
        Integer internalLinksCount
        Integer externalLinksCount
        Integer imageCount
        String h1Tags
        String h2Tags
        String errorMessage
        LocalDateTime crawledAt
    }
    
    KEYWORD {
        Long keywordId PK
        Long pageId FK
        String keyword
        Integer frequency
        Double density
        KeywordType keywordType
    }
    
    LINK {
        Long linkId PK
        Long sourcePageId FK
        Long targetPageId FK
        String targetUrl
        String anchorText
        LinkType linkType
        String relAttribute
        String titleAttribute
        LocalDateTime discoveredAt
    }
    
    DASHBOARD_DATA {
        Long dashboardId PK
        Long userId FK
        Long totalSessions
        Long activeSessions
        Long completedSessions
        Long totalPagesCrawled
        Long totalPagesFailed
        Long totalLinksFound
        Double averageSuccessRate
        Double averagePagesPerSession
        Long averageLoadTimeMs
        Integer totalErrorCount
        LocalDateTime lastCrawlTime
        String timeRange
        String dashboardType
        Boolean isCached
        LocalDateTime cacheExpiresAt
        LocalDateTime generatedAt
        LocalDateTime updatedAt
    }
    
    CRAWL_STATISTICS {
        Long statisticsId PK
        Long userId FK
        Long totalSessions
        Long activeSessions
        Long completedSessions
        Long failedSessions
        Double overallSuccessRate
        Double averagePagesPerSession
        Double averageLoadTimeMs
        Long totalLinksFound
        Long totalKeywordsExtracted
        Integer uniqueDomainsCount
        String mostCrawledDomain
        String timeRange
        String statisticsType
        LocalDateTime generatedAt
        LocalDateTime updatedAt
    }
    
    CHART_DATA {
        Long chartId PK
        Long userId FK
        String chartTitle
        ChartType chartType
        String description
        String timeRange
        Long totalDataPoints
        Double maxValue
        Double minValue
        Double averageValue
        Boolean isRealtime
        Integer refreshIntervalSeconds
        Boolean isCached
        LocalDateTime cacheExpiresAt
        LocalDateTime generatedAt
        LocalDateTime updatedAt
    }
    
    USER_ACTIVITY {
        Long activityId PK
        Long userId FK
        Long totalSessions
        Long activeSessions
        Long completedSessions
        LocalDateTime lastLoginAt
        LocalDateTime lastCrawlActivity
        LocalDateTime firstCrawlActivity
        Long sessionsToday
        Long sessionsThisWeek
        Long sessionsThisMonth
        Double averageSuccessRate
        Double personalBestSuccessRate
        Integer uniqueDomainsExplored
        String mostCrawledDomain
        Double productivityScore
        String productivityGrade
        String accountTier
        String timeRange
        String activityType
        LocalDateTime generatedAt
        LocalDateTime updatedAt
    }
```

---

## 📋 7. Implementation Checklist

### 7.1. Database Schema
- ✅ Core entities (User, Role, CrawlSession, Page, Link, Keyword)
- ✅ Dashboard analytics entities (DashboardData, CrawlStatistics, ChartData, UserActivity)
- ✅ Proper indexing strategy for performance
- ✅ Foreign key constraints and relationships
- ✅ Audit fields with automatic timestamp management

### 7.2. Business Logic
- ✅ Entity validation with Bean Validation annotations
- ✅ Business methods for calculations and state management
- ✅ Enum types for status and classification fields
- ✅ Proper equals/hashCode implementations

### 7.3. Analytics Features
- ✅ Real-time dashboard data aggregation
- ✅ Chart.js compatible data structures
- ✅ User activity and behavior tracking
- ✅ Performance metrics and grading system
- ✅ Caching strategy for optimal performance

This comprehensive data model provides the foundation for a robust, scalable web crawler with enterprise-grade analytics capabilities, supporting both operational crawling activities and sophisticated business intelligence requirements.
```


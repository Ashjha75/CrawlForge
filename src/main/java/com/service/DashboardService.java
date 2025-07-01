package com.service;

import com.dao.*;
import com.entity.CrawlSession;
import com.entity.DashboardData;
import com.entity.Page;
import com.entity.User;
import com.utils.JsonUtil;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardService {

    private static final Logger LOGGER = Logger.getLogger(DashboardService.class.getName());

    // Fixed thread pool for asynchronous operations as per dashboard.md
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    // DAO instances
    private final DashboardDataDAO dashboardDataDAO;
    private final CrawlStatisticsDAO crawlStatisticsDAO;
    private final UserActivityDAO userActivityDAO;
    private final ChartDataDAO chartDataDAO;
    private final CrawlSessionDAO crawlSessionDAO;
    private final PageDAO pageDAO;

    // Cache manager for 15-minute TTL as per dashboard.md
    private final ConcurrentHashMap<String, DashboardData> dashboardCache;
    private final ConcurrentHashMap<String, LocalDateTime> cacheExpiry;

    public DashboardService() {
        this.dashboardDataDAO = new DashboardDataDAO();
        this.crawlStatisticsDAO = new CrawlStatisticsDAO();
        this.userActivityDAO = new UserActivityDAO();
        this.chartDataDAO = new ChartDataDAO();
        this.crawlSessionDAO = new CrawlSessionDAO();
        this.pageDAO = new PageDAO();
        this.dashboardCache = new ConcurrentHashMap<>();
        this.cacheExpiry = new ConcurrentHashMap<>();
    }

    // Asynchronous dashboard data retrieval
    public CompletableFuture<DashboardData> getDashboardDataAsync(Long userId, String timeRange) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return getDashboardData(userId, timeRange);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error in async dashboard data retrieval for user: " + userId, e);
                        return getCachedDashboardData(userId, timeRange);
                    }
                }, executorService).orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    LOGGER.log(Level.WARNING, "Dashboard data async timeout for user: " + userId, throwable);
                    return getCachedDashboardData(userId, timeRange);
                });
    }

    // Main dashboard data retrieval method
    public DashboardData getDashboardData(Long userId, String timeRange) {
        try {
            String cacheKey = userId + "_" + timeRange;

            // Check cache first
            DashboardData cachedData = getCachedDashboardData(userId, timeRange);
            if (cachedData != null && isCacheValid(cacheKey)) {
                return cachedData;
            }

            // Fetch or create new dashboard data
            Optional<DashboardData> existingData = dashboardDataDAO.findByUserIdAndTimeRange(userId, timeRange);

            if (existingData.isPresent() && !existingData.get().needsRefresh()) {
                DashboardData data = existingData.get();
                updateCache(cacheKey, data);
                return data;
            }

            // Generate new dashboard data
            DashboardData newData = generateDashboardData(userId, timeRange);

            // Save to database
            if (existingData.isPresent()) {
                DashboardData existing = existingData.get();
                updateDashboardDataFields(existing, newData);
                dashboardDataDAO.updateDashboardData(existing);
                updateCache(cacheKey, existing);
                return existing;
            } else {
                dashboardDataDAO.saveDashboardData(newData);
                updateCache(cacheKey, newData);
                return newData;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating dashboard data for user: " + userId, e);
            return getCachedDashboardData(userId, timeRange);
        }
    }

    // Generate dashboard data from raw crawl data
    private DashboardData generateDashboardData(Long userId, String timeRange) {
        try {
            // Parallel data fetching using CompletableFuture as per dashboard.md
            CompletableFuture<List<CrawlSession>> sessionsFuture = CompletableFuture.supplyAsync(() ->
                    crawlSessionDAO.findByUserId(userId), executorService);

            CompletableFuture<List<Page>> pagesFuture = CompletableFuture.supplyAsync(() ->
                    pageDAO.findByUserId(userId), executorService);

            CompletableFuture<Long> totalSessionsFuture = CompletableFuture.supplyAsync(() ->
                    crawlSessionDAO.countByUserId(userId), executorService);

            // Wait for all futures with timeout
            CompletableFuture.allOf(sessionsFuture, pagesFuture, totalSessionsFuture)
                    .get(5, TimeUnit.SECONDS);

            List<CrawlSession> sessions = sessionsFuture.get();
            List<Page> pages = pagesFuture.get();
            Long totalSessions = totalSessionsFuture.get();

            // Calculate metrics
            long activeSessions = sessions.stream()
                    .mapToLong(s -> s.isActive() ? 1 : 0)
                    .sum();

            long completedSessions = sessions.stream()
                    .mapToLong(s -> s.getStatus() == CrawlSession.CrawlStatus.COMPLETED ? 1 : 0)
                    .sum();

            long totalPagesCrawled = pages.size();
            long totalPagesFailed = pages.stream()
                    .mapToLong(p -> p.isError() ? 1 : 0)
                    .sum();

            long totalLinksFound = sessions.stream()
                    .mapToLong(s -> s.getTotalLinksFound() != null ? s.getTotalLinksFound() : 0)
                    .sum();

            double averageSuccessRate = sessions.stream()
                    .mapToDouble(CrawlSession::getSuccessRate)
                    .average()
                    .orElse(0.0);

            double averagePagesPerSession = totalSessions > 0 ? (double) totalPagesCrawled / totalSessions : 0.0;

            long averageLoadTimeMs = pages.stream()
                    .filter(p -> p.getLoadTimeMs() != null)
                    .mapToLong(Page::getLoadTimeMs)
                    .sum() / Math.max(1, pages.size());

            // Time-based calculations
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
            LocalDateTime weekStart = now.minusDays(7);
            LocalDateTime monthStart = now.minusDays(30);

            long crawlsToday = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(todayStart) ? 1 : 0)
                    .sum();

            long crawlsThisWeek = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(weekStart) ? 1 : 0)
                    .sum();

            long crawlsThisMonth = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(monthStart) ? 1 : 0)
                    .sum();

            LocalDateTime lastCrawlTime = sessions.stream()
                    .map(CrawlSession::getCreatedAt)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            // Get user for relationship
            User user = sessions.isEmpty() ? null : sessions.get(0).getUser();

            // Build dashboard data
            return DashboardData.builder()
                    .user(user)
                    .totalSessions(totalSessions)
                    .activeSessions(activeSessions)
                    .completedSessions(completedSessions)
                    .totalPagesCrawled(totalPagesCrawled)
                    .totalPagesFailed(totalPagesFailed)
                    .totalLinksFound(totalLinksFound)
                    .averageSuccessRate(averageSuccessRate)
                    .averagePagesPerSession(averagePagesPerSession)
                    .averageLoadTimeMs(averageLoadTimeMs)
                    .totalErrorCount((int) totalPagesFailed)
                    .lastCrawlTime(lastCrawlTime)
                    .crawlsToday(crawlsToday)
                    .crawlsThisWeek(crawlsThisWeek)
                    .crawlsThisMonth(crawlsThisMonth)
                    .userTotalSessions(totalSessions.intValue())
                    .userLastActivity(lastCrawlTime)
                    .timeRange(timeRange)
                    .dashboardType("STANDARD")
                    .refreshIntervalMinutes(15)
                    .isCached(false)
                    .dataVersion(1)
                    .build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating dashboard data for user: " + userId, e);
            throw new RuntimeException("Failed to generate dashboard data", e);
        }
    }

    // Cache management methods
    public DashboardData getCachedDashboardData(Long userId, String timeRange) {
        String cacheKey = userId + "_" + timeRange;
        DashboardData cached = dashboardCache.get(cacheKey);

        if (cached != null && isCacheValid(cacheKey)) {
            return cached;
        }

        // Try to get from database if cache is invalid
        try {
            Optional<DashboardData> dbData = dashboardDataDAO.findByUserIdAndTimeRange(userId, timeRange);
            if (dbData.isPresent()) {
                updateCache(cacheKey, dbData.get());
                return dbData.get();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error fetching dashboard data from database", e);
        }

        // Return empty dashboard data as fallback
        return DashboardData.createEmpty(null, timeRange);
    }

    private boolean isCacheValid(String cacheKey) {
        LocalDateTime expiry = cacheExpiry.get(cacheKey);
        return expiry != null && expiry.isAfter(LocalDateTime.now());
    }

    private void updateCache(String cacheKey, DashboardData data) {
        dashboardCache.put(cacheKey, data);
        cacheExpiry.put(cacheKey, LocalDateTime.now().plusMinutes(15)); // 15-minute TTL
    }

    // Force refresh dashboard data
    public void refreshDashboardData(Long userId, String timeRange) {
        try {
            String cacheKey = userId + "_" + timeRange;

            // Remove from cache
            dashboardCache.remove(cacheKey);
            cacheExpiry.remove(cacheKey);

            // Mark database cache as expired
            Optional<DashboardData> existingData = dashboardDataDAO.findByUserIdAndTimeRange(userId, timeRange);
            if (existingData.isPresent()) {
                dashboardDataDAO.markCacheExpired(existingData.get().getDashboardId());
            }

            // Generate fresh data
            getDashboardData(userId, timeRange);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error refreshing dashboard data for user: " + userId, e);
        }
    }

    // Export functionality
    public void exportToCsv(Long userId, String timeRange, OutputStream outputStream) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
            DashboardData data = getDashboardData(userId, timeRange);

            // CSV headers
            writer.println("Metric,Value");
            writer.println("Total Sessions," + data.getTotalSessions());
            writer.println("Active Sessions," + data.getActiveSessions());
            writer.println("Completed Sessions," + data.getCompletedSessions());
            writer.println("Total Pages Crawled," + data.getTotalPagesCrawled());
            writer.println("Total Pages Failed," + data.getTotalPagesFailed());
            writer.println("Total Links Found," + data.getTotalLinksFound());
            writer.println("Average Success Rate," + String.format("%.2f%%", data.getAverageSuccessRate()));
            writer.println("Average Pages Per Session," + String.format("%.2f", data.getAveragePagesPerSession()));
            writer.println("Average Load Time (ms)," + data.getAverageLoadTimeMs());
            writer.println("Total Error Count," + data.getTotalErrorCount());
            writer.println("Last Crawl Time," + (data.getLastCrawlTime() != null ? data.getLastCrawlTime().toString() : "N/A"));
            writer.println("Crawls Today," + data.getCrawlsToday());
            writer.println("Crawls This Week," + data.getCrawlsThisWeek());
            writer.println("Crawls This Month," + data.getCrawlsThisMonth());

            writer.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error exporting dashboard data to CSV for user: " + userId, e);
        }
    }

    public void exportToJson(Long userId, String timeRange, OutputStream outputStream) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
            DashboardData data = getDashboardData(userId, timeRange);
            String jsonData = JsonUtil.toJson(data);
            writer.write(jsonData);
            writer.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error exporting dashboard data to JSON for user: " + userId, e);
        }
    }

    // Helper method to update dashboard data fields
    private void updateDashboardDataFields(DashboardData existing, DashboardData newData) {
        existing.setTotalSessions(newData.getTotalSessions());
        existing.setActiveSessions(newData.getActiveSessions());
        existing.setCompletedSessions(newData.getCompletedSessions());
        existing.setTotalPagesCrawled(newData.getTotalPagesCrawled());
        existing.setTotalPagesFailed(newData.getTotalPagesFailed());
        existing.setTotalLinksFound(newData.getTotalLinksFound());
        existing.setAverageSuccessRate(newData.getAverageSuccessRate());
        existing.setAveragePagesPerSession(newData.getAveragePagesPerSession());
        existing.setAverageLoadTimeMs(newData.getAverageLoadTimeMs());
        existing.setTotalErrorCount(newData.getTotalErrorCount());
        existing.setLastCrawlTime(newData.getLastCrawlTime());
        existing.setCrawlsToday(newData.getCrawlsToday());
        existing.setCrawlsThisWeek(newData.getCrawlsThisWeek());
        existing.setCrawlsThisMonth(newData.getCrawlsThisMonth());
        existing.setUserTotalSessions(newData.getUserTotalSessions());
        existing.setUserLastActivity(newData.getUserLastActivity());
        existing.setIsCached(false);
        existing.setCacheExpiresAt(null);
    }

    // Cleanup method for expired cache entries
    public void cleanupExpiredCache() {
        try {
            LocalDateTime now = LocalDateTime.now();
            cacheExpiry.entrySet().removeIf(entry -> entry.getValue().isBefore(now));

            // Remove corresponding dashboard cache entries
            dashboardCache.entrySet().removeIf(entry -> !cacheExpiry.containsKey(entry.getKey()));

            LOGGER.info("Cleaned up expired cache entries");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error cleaning up expired cache", e);
        }
    }

    // Shutdown method
    public void shutdown() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

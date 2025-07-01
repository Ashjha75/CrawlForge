package com.service;

import com.dao.CrawlStatisticsDAO;
import com.dao.UserActivityDAO;
import com.dao.ChartDataDAO;
import com.dao.CrawlSessionDAO;
import com.dao.PageDAO;
import com.dao.KeywordDAO;
import com.dao.LinkDAO;
import com.entity.*;
import com.utils.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsService {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsService.class.getName());

    // Fixed thread pool for asynchronous operations as per dashboard.md
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    // DAO instances
    private final CrawlStatisticsDAO crawlStatisticsDAO;
    private final UserActivityDAO userActivityDAO;
    private final ChartDataDAO chartDataDAO;
    private final CrawlSessionDAO crawlSessionDAO;
    private final PageDAO pageDAO;
    private final KeywordDAO keywordDAO;
    private final LinkDAO linkDAO;

    // Cache for analytics data
    private final ConcurrentHashMap<String, CrawlStatistics> statisticsCache;
    private final ConcurrentHashMap<String, UserActivity> activityCache;
    private final ConcurrentHashMap<String, LocalDateTime> cacheExpiry;

    public AnalyticsService() {
        this.crawlStatisticsDAO = new CrawlStatisticsDAO();
        this.userActivityDAO = new UserActivityDAO();
        this.chartDataDAO = new ChartDataDAO();
        this.crawlSessionDAO = new CrawlSessionDAO();
        this.pageDAO = new PageDAO();
        this.keywordDAO = new KeywordDAO();
        this.linkDAO = new LinkDAO();
        this.statisticsCache = new ConcurrentHashMap<>();
        this.activityCache = new ConcurrentHashMap<>();
        this.cacheExpiry = new ConcurrentHashMap<>();
    }

    // Asynchronous crawl statistics retrieval
    public CompletableFuture<CrawlStatistics> getCrawlStatisticsAsync(Long userId, String timeRange) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return getCrawlStatistics(userId, timeRange);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error in async crawl statistics retrieval for user: " + userId, e);
                        return getCachedCrawlStatistics(userId, timeRange);
                    }
                }, executorService).orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    LOGGER.log(Level.WARNING, "Crawl statistics async timeout for user: " + userId, throwable);
                    return getCachedCrawlStatistics(userId, timeRange);
                });
    }

    // Asynchronous user activity retrieval
    public CompletableFuture<UserActivity> getUserActivityAsync(Long userId, String timeRange) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return getUserActivity(userId, timeRange);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error in async user activity retrieval for user: " + userId, e);
                        return getCachedUserActivity(userId, timeRange);
                    }
                }, executorService).orTimeout(5, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    LOGGER.log(Level.WARNING, "User activity async timeout for user: " + userId, throwable);
                    return getCachedUserActivity(userId, timeRange);
                });
    }

    // Main crawl statistics retrieval method
    public CrawlStatistics getCrawlStatistics(Long userId, String timeRange) {
        try {
            String cacheKey = userId + "_stats_" + timeRange;

            // Check cache first
            CrawlStatistics cached = getCachedCrawlStatistics(userId, timeRange);
            if (cached != null && isCacheValid(cacheKey)) {
                return cached;
            }

            // Fetch or create new statistics
            Optional<CrawlStatistics> existingStats = crawlStatisticsDAO.findByUserIdAndTimeRange(userId, timeRange);

            if (existingStats.isPresent() && !isStatsExpired(existingStats.get())) {
                CrawlStatistics stats = existingStats.get();
                updateCache(cacheKey, stats);
                return stats;
            }

            // Generate new statistics
            CrawlStatistics newStats = generateCrawlStatistics(userId, timeRange);

            // Save to database
            if (existingStats.isPresent()) {
                CrawlStatistics existing = existingStats.get();
                updateStatisticsFields(existing, newStats);
                crawlStatisticsDAO.updateCrawlStatistics(existing);
                updateCache(cacheKey, existing);
                return existing;
            } else {
                crawlStatisticsDAO.saveCrawlStatistics(newStats);
                updateCache(cacheKey, newStats);
                return newStats;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating crawl statistics for user: " + userId, e);
            return getCachedCrawlStatistics(userId, timeRange);
        }
    }

    // Main user activity retrieval method
    public UserActivity getUserActivity(Long userId, String timeRange) {
        try {
            String cacheKey = userId + "_activity_" + timeRange;

            // Check cache first
            UserActivity cached = getCachedUserActivity(userId, timeRange);
            if (cached != null && isCacheValid(cacheKey)) {
                return cached;
            }

            // Fetch or create new activity data
            Optional<UserActivity> existingActivity = userActivityDAO.findByUserIdAndTimeRange(userId, timeRange);

            if (existingActivity.isPresent() && !isActivityExpired(existingActivity.get())) {
                UserActivity activity = existingActivity.get();
                updateActivityCache(cacheKey, activity);
                return activity;
            }

            // Generate new activity data
            UserActivity newActivity = generateUserActivity(userId, timeRange);

            // Save to database
            if (existingActivity.isPresent()) {
                UserActivity existing = existingActivity.get();
                updateActivityFields(existing, newActivity);
                userActivityDAO.updateUserActivity(existing);
                updateActivityCache(cacheKey, existing);
                return existing;
            } else {
                userActivityDAO.saveUserActivity(newActivity);
                updateActivityCache(cacheKey, newActivity);
                return newActivity;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating user activity for user: " + userId, e);
            return getCachedUserActivity(userId, timeRange);
        }
    }

    // Chart data retrieval method
    public List<ChartData> getChartData(Long userId, String timeRange) {
        try {
            return chartDataDAO.findByUserIdAndTimeRange(userId, timeRange);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching chart data for user: " + userId, e);
            return List.of();
        }
    }

    // Generate crawl statistics from raw data
    private CrawlStatistics generateCrawlStatistics(Long userId, String timeRange) {
        try {
            // Parallel data fetching
            CompletableFuture<List<CrawlSession>> sessionsFuture = CompletableFuture.supplyAsync(() ->
                    crawlSessionDAO.findByUserId(userId), executorService);

            CompletableFuture<List<Page>> pagesFuture = CompletableFuture.supplyAsync(() ->
                    pageDAO.findByUserId(userId), executorService);

            CompletableFuture<Long> totalKeywordsFuture = CompletableFuture.supplyAsync(() ->
                    keywordDAO.countByUserId(userId), executorService);

            CompletableFuture<Long> totalLinksFuture = CompletableFuture.supplyAsync(() ->
                    linkDAO.countByUserId(userId), executorService);

            // Wait for all futures
            CompletableFuture.allOf(sessionsFuture, pagesFuture, totalKeywordsFuture, totalLinksFuture)
                    .get(5, TimeUnit.SECONDS);

            List<CrawlSession> sessions = sessionsFuture.get();
            List<Page> pages = pagesFuture.get();
            Long totalKeywords = totalKeywordsFuture.get();
            Long totalLinks = totalLinksFuture.get();

            // Calculate statistics
            long totalSessions = sessions.size();
            long activeSessions = sessions.stream()
                    .mapToLong(s -> s.isActive() ? 1 : 0)
                    .sum();

            long completedSessions = sessions.stream()
                    .mapToLong(s -> s.getStatus() == CrawlSession.CrawlStatus.COMPLETED ? 1 : 0)
                    .sum();

            long failedSessions = sessions.stream()
                    .mapToLong(s -> s.getStatus() == CrawlSession.CrawlStatus.FAILED ? 1 : 0)
                    .sum();

            long totalPagesCrawled = pages.size();
            long totalPagesFailed = pages.stream()
                    .mapToLong(p -> p.isError() ? 1 : 0)
                    .sum();

            long totalPagesSuccessful = totalPagesCrawled - totalPagesFailed;

            double overallSuccessRate = totalPagesCrawled > 0 ?
                    (totalPagesSuccessful * 100.0) / totalPagesCrawled : 0.0;

            double averagePagesPerSession = totalSessions > 0 ?
                    (double) totalPagesCrawled / totalSessions : 0.0;

            double averageLoadTimeMs = pages.stream()
                    .filter(p -> p.getLoadTimeMs() != null)
                    .mapToLong(Page::getLoadTimeMs)
                    .average()
                    .orElse(0.0);

            // Time-based calculations
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dayAgo = now.minusDays(1);
            LocalDateTime weekAgo = now.minusDays(7);
            LocalDateTime monthAgo = now.minusDays(30);

            long crawlsLast24Hours = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(dayAgo) ? 1 : 0)
                    .sum();

            long crawlsLast7Days = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(weekAgo) ? 1 : 0)
                    .sum();

            long crawlsLast30Days = sessions.stream()
                    .mapToLong(s -> s.getCreatedAt().isAfter(monthAgo) ? 1 : 0)
                    .sum();

            // Get user for relationship
            User user = sessions.isEmpty() ? null : sessions.get(0).getUser();

            // Build statistics
            return CrawlStatistics.builder()
                    .user(user)
                    .totalSessions(totalSessions)
                    .activeSessions(activeSessions)
                    .completedSessions(completedSessions)
                    .failedSessions(failedSessions)
                    .totalPagesCrawled(totalPagesCrawled)
                    .totalPagesFailed(totalPagesFailed)
                    .totalPagesSuccessful(totalPagesSuccessful)
                    .overallSuccessRate(overallSuccessRate)
                    .averagePagesPerSession(averagePagesPerSession)
                    .averageLoadTimeMs(averageLoadTimeMs)
                    .totalKeywordsExtracted(totalKeywords)
                    .totalLinksFound(totalLinks)
                    .crawlsLast24Hours(crawlsLast24Hours)
                    .crawlsLast7Days(crawlsLast7Days)
                    .crawlsLast30Days(crawlsLast30Days)
                    .timeRange(timeRange)
                    .statisticsType("USER")
                    .dataVersion(1)
                    .build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating crawl statistics for user: " + userId, e);
            throw new RuntimeException("Failed to generate crawl statistics", e);
        }
    }

    // Generate user activity from raw data
    private UserActivity generateUserActivity(Long userId, String timeRange) {
        try {
            List<CrawlSession> sessions = crawlSessionDAO.findByUserId(userId);
            List<Page> pages = pageDAO.findByUserId(userId);

            // Calculate activity metrics
            long totalSessions = sessions.size();
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

            double averageSuccessRate = sessions.stream()
                    .mapToDouble(CrawlSession::getSuccessRate)
                    .average()
                    .orElse(0.0);

            LocalDateTime lastCrawlActivity = sessions.stream()
                    .map(CrawlSession::getCreatedAt)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            LocalDateTime firstCrawlActivity = sessions.stream()
                    .map(CrawlSession::getCreatedAt)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            // Get user for relationship
            User user = sessions.isEmpty() ? null : sessions.get(0).getUser();

            return UserActivity.builder()
                    .user(user)
                    .totalSessions(totalSessions)
                    .activeSessions(activeSessions)
                    .completedSessions(completedSessions)
                    .totalPagesCrawled(totalPagesCrawled)
                    .totalPagesFailed(totalPagesFailed)
                    .averageSuccessRate(averageSuccessRate)
                    .lastCrawlActivity(lastCrawlActivity)
                    .firstCrawlActivity(firstCrawlActivity)
                    .timeRange(timeRange)
                    .activityType("USER")
                    .dataVersion(1)
                    .build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating user activity for user: " + userId, e);
            throw new RuntimeException("Failed to generate user activity", e);
        }
    }

    // Cache management methods
    private CrawlStatistics getCachedCrawlStatistics(Long userId, String timeRange) {
        String cacheKey = userId + "_stats_" + timeRange;
        CrawlStatistics cached = statisticsCache.get(cacheKey);

        if (cached != null && isCacheValid(cacheKey)) {
            return cached;
        }

        // Try database fallback
        try {
            Optional<CrawlStatistics> dbStats = crawlStatisticsDAO.findByUserIdAndTimeRange(userId, timeRange);
            if (dbStats.isPresent()) {
                updateCache(cacheKey, dbStats.get());
                return dbStats.get();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error fetching statistics from database", e);
        }

        return CrawlStatistics.empty(userId);
    }

    private UserActivity getCachedUserActivity(Long userId, String timeRange) {
        String cacheKey = userId + "_activity_" + timeRange;
        UserActivity cached = activityCache.get(cacheKey);

        if (cached != null && isCacheValid(cacheKey)) {
            return cached;
        }

        // Try database fallback
        try {
            Optional<UserActivity> dbActivity = userActivityDAO.findByUserIdAndTimeRange(userId, timeRange);
            if (dbActivity.isPresent()) {
                updateActivityCache(cacheKey, dbActivity.get());
                return dbActivity.get();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error fetching activity from database", e);
        }

        return UserActivity.empty(userId);
    }

    private boolean isCacheValid(String cacheKey) {
        LocalDateTime expiry = cacheExpiry.get(cacheKey);
        return expiry != null && expiry.isAfter(LocalDateTime.now());
    }

    private void updateCache(String cacheKey, CrawlStatistics stats) {
        statisticsCache.put(cacheKey, stats);
        cacheExpiry.put(cacheKey, LocalDateTime.now().plusMinutes(15));
    }

    private void updateActivityCache(String cacheKey, UserActivity activity) {
        activityCache.put(cacheKey, activity);
        cacheExpiry.put(cacheKey, LocalDateTime.now().plusMinutes(15));
    }

    private boolean isStatsExpired(CrawlStatistics stats) {
        return stats.getGeneratedAt().isBefore(LocalDateTime.now().minusMinutes(15));
    }

    private boolean isActivityExpired(UserActivity activity) {
        return activity.getGeneratedAt().isBefore(LocalDateTime.now().minusMinutes(15));
    }

    // Helper methods to update fields
    private void updateStatisticsFields(CrawlStatistics existing, CrawlStatistics newStats) {
        existing.setTotalSessions(newStats.getTotalSessions());
        existing.setActiveSessions(newStats.getActiveSessions());
        existing.setCompletedSessions(newStats.getCompletedSessions());
        existing.setFailedSessions(newStats.getFailedSessions());
        existing.setTotalPagesCrawled(newStats.getTotalPagesCrawled());
        existing.setTotalPagesFailed(newStats.getTotalPagesFailed());
        existing.setTotalPagesSuccessful(newStats.getTotalPagesSuccessful());
        existing.setOverallSuccessRate(newStats.getOverallSuccessRate());
        existing.setAveragePagesPerSession(newStats.getAveragePagesPerSession());
        existing.setAverageLoadTimeMs(newStats.getAverageLoadTimeMs());
        existing.setTotalKeywordsExtracted(newStats.getTotalKeywordsExtracted());
        existing.setTotalLinksFound(newStats.getTotalLinksFound());
        existing.setCrawlsLast24Hours(newStats.getCrawlsLast24Hours());
        existing.setCrawlsLast7Days(newStats.getCrawlsLast7Days());
        existing.setCrawlsLast30Days(newStats.getCrawlsLast30Days());
    }

    private void updateActivityFields(UserActivity existing, UserActivity newActivity) {
        existing.setTotalSessions(newActivity.getTotalSessions());
        existing.setActiveSessions(newActivity.getActiveSessions());
        existing.setCompletedSessions(newActivity.getCompletedSessions());
        existing.setTotalPagesCrawled(newActivity.getTotalPagesCrawled());
        existing.setTotalPagesFailed(newActivity.getTotalPagesFailed());
        existing.setAverageSuccessRate(newActivity.getAverageSuccessRate());
        existing.setLastCrawlActivity(newActivity.getLastCrawlActivity());
        existing.setFirstCrawlActivity(newActivity.getFirstCrawlActivity());
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

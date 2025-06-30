package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardData {

    // Summary Statistics
    private final Long totalSessions;
    private final Long activeSessions;
    private final Long completedSessions;
    private final Long totalPagesCrawled;
    private final Long totalPagesFailed;
    private final Long totalLinksFound;

    // Performance Metrics
    private final Double averageSuccessRate;
    private final Double averagePagesPerSession;
    private final Long averageLoadTimeMs;
    private final Integer totalErrorCount;

    // Time-based Analytics
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastCrawlTime;
    private final Long crawlsToday;
    private final Long crawlsThisWeek;
    private final Long crawlsThisMonth;

    // Chart Data Collections
    private final List<ChartDataPoint> domainDistribution;
    private final List<ChartDataPoint> statusCodeDistribution;
    private final List<ChartDataPoint> keywordFrequency;
    private final List<ChartDataPoint> linkTypeDistribution;
    private final List<ChartDataPoint> dailyCrawlTrend;

    // Recent Activity
    private final List<RecentSession> recentSessions;
    private final List<TopDomain> topDomains;
    private final List<PopularKeyword> popularKeywords;

    // User-specific Data
    private final Long userId;
    private final String userName;
    private final Integer userTotalSessions;
    private final LocalDateTime userLastActivity;

    // System Health Indicators
    private final SystemHealth systemHealth;

    // Embedded Static Records for Chart Data
    @Builder
    @Getter
    @ToString
    public static class ChartDataPoint {
        private final String label;
        private final Long value;
        private final Double percentage;
        private final String color;

        public static ChartDataPoint of(String label, Long value, Double percentage) {
            return ChartDataPoint.builder()
                    .label(label)
                    .value(value)
                    .percentage(percentage)
                    .build();
        }

        public static ChartDataPoint of(String label, Long value, Double percentage, String color) {
            return ChartDataPoint.builder()
                    .label(label)
                    .value(value)
                    .percentage(percentage)
                    .color(color)
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    public static class RecentSession {
        private final Long sessionId;
        private final String sessionName;
        private final String seedUrl;
        private final CrawlSession.CrawlStatus status;
        private final Integer pagesCrawled;
        private final Double successRate;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime completedAt;
    }

    @Builder
    @Getter
    @ToString
    public static class TopDomain {
        private final String domain;
        private final Long pageCount;
        private final Double averageLoadTime;
        private final Double successRate;
        private final Integer errorCount;
    }

    @Builder
    @Getter
    @ToString
    public static class PopularKeyword {
        private final String keyword;
        private final Long totalFrequency;
        private final Integer pageCount;
        private final Double averageDensity;
        private final Keyword.KeywordType keywordType;
    }

    @Builder
    @Getter
    @ToString
    public static class SystemHealth {
        private final String status; // HEALTHY, WARNING, CRITICAL
        private final Integer activeThreads;
        private final Long memoryUsageMB;
        private final Double cpuUsagePercent;
        private final Long databaseConnections;
        private final List<String> warnings;
        private final List<String> errors;

        public boolean isHealthy() {
            return "HEALTHY".equals(status);
        }

        public boolean hasWarnings() {
            return warnings != null && !warnings.isEmpty();
        }

        public boolean hasErrors() {
            return errors != null && !errors.isEmpty();
        }
    }

    // Builder Defaults for Null Safety
    public static class DashboardDataBuilder {
        private List<ChartDataPoint> domainDistribution = Collections.emptyList();
        private List<ChartDataPoint> statusCodeDistribution = Collections.emptyList();
        private List<ChartDataPoint> keywordFrequency = Collections.emptyList();
        private List<ChartDataPoint> linkTypeDistribution = Collections.emptyList();
        private List<ChartDataPoint> dailyCrawlTrend = Collections.emptyList();
        private List<RecentSession> recentSessions = Collections.emptyList();
        private List<TopDomain> topDomains = Collections.emptyList();
        private List<PopularKeyword> popularKeywords = Collections.emptyList();
        private List<String> warnings = Collections.emptyList();
        private List<String> errors = Collections.emptyList();
    }

    // Utility Methods for Dashboard Logic
    public boolean hasRecentActivity() {
        return lastCrawlTime != null &&
                lastCrawlTime.isAfter(LocalDateTime.now().minusHours(24));
    }

    public boolean isSystemHealthy() {
        return systemHealth != null && systemHealth.isHealthy();
    }

    public Double getOverallSuccessRate() {
        if (totalPagesCrawled == null || totalPagesCrawled == 0) {
            return 0.0;
        }
        Long successfulPages = totalPagesCrawled - (totalPagesFailed != null ? totalPagesFailed : 0);
        return (successfulPages.doubleValue() / totalPagesCrawled.doubleValue()) * 100.0;
    }

    public boolean hasActiveWork() {
        return activeSessions != null && activeSessions > 0;
    }

    // Factory Methods for Common Dashboard Scenarios
//    Factory methods are static methods that return instances of a class, often with preconfigured or default values.
//    They provide an alternative to constructors for object creation, allowing for more descriptive names, encapsulation of complex creation logic, or returning specific subclasses or cached instances.
//    In your code, the empty method is a factory method that creates a DashboardData object with default values.
    public static DashboardData empty(Long userId, String userName) {
        return DashboardData.builder()
                .userId(userId)
                .userName(userName)
                .totalSessions(0L)
                .activeSessions(0L)
                .completedSessions(0L)
                .totalPagesCrawled(0L)
                .totalPagesFailed(0L)
                .totalLinksFound(0L)
                .averageSuccessRate(0.0)
                .averagePagesPerSession(0.0)
                .averageLoadTimeMs(0L)
                .totalErrorCount(0)
                .crawlsToday(0L)
                .crawlsThisWeek(0L)
                .crawlsThisMonth(0L)
                .userTotalSessions(0)
                .systemHealth(SystemHealth.builder()
                        .status("HEALTHY")
                        .activeThreads(0)
                        .memoryUsageMB(0L)
                        .cpuUsagePercent(0.0)
                        .databaseConnections(0L)
                        .build())
                .build();
    }
}

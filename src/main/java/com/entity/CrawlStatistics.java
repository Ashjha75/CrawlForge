package com.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Collections;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlStatistics {

    // Core Session Statistics
    private final Long totalSessions;
    private final Long activeSessions;
    private final Long completedSessions;
    private final Long failedSessions;
    private final Long pausedSessions;
    private final Long cancelledSessions;

    // Page Crawling Metrics
    private final Long totalPagesCrawled;
    private final Long totalPagesFailed;
    private final Long totalPagesSuccessful;
    private final Double overallSuccessRate;
    private final Double averagePagesPerSession;
    private final Integer maxPagesInSession;
    private final Integer minPagesInSession;

    // Performance Analytics
    private final Double averageLoadTimeMs;
    private final Long totalLoadTimeMs;
    private final Double averageSessionDurationMinutes;
    private final Long fastestPageLoadMs;
    private final Long slowestPageLoadMs;

    // Link Discovery Statistics
    private final Long totalLinksFound;
    private final Long totalInternalLinks;
    private final Long totalExternalLinks;
    private final Double averageLinksPerPage;
    private final Integer maxLinksOnPage;

    // Keyword Analysis Metrics
    private final Long totalKeywordsExtracted;
    private final Double averageKeywordsPerPage;
    private final Double averageKeywordDensity;
    private final Integer uniqueKeywordCount;

    // Error and Status Code Analytics
    private final Long total2xxResponses;
    private final Long total3xxResponses;
    private final Long total4xxResponses;
    private final Long total5xxResponses;
    private final Integer mostCommonStatusCode;
    private final Long totalTimeouts;
    private final Long totalConnectionErrors;

    // Depth and Threading Analysis
    private final Double averageDepthReached;
    private final Integer maxDepthReached;
    private final Double averageThreadUtilization;
    private final Long totalThreadHours;

    // Time-based Trends
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime firstCrawlTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastCrawlTime;

    private final Long crawlsLast24Hours;
    private final Long crawlsLast7Days;
    private final Long crawlsLast30Days;
    private final Long pagesLast24Hours;
    private final Long pagesLast7Days;
    private final Long pagesLast30Days;

    // Domain and Content Statistics
    private final Integer uniqueDomainsCount;
    private final String mostCrawledDomain;
    private final Long mostCrawledDomainCount;
    private final List<DomainStatistic> topDomains;

    // Content Type Analysis
    private final Long htmlPagesCount;
    private final Long imageLinksCount;
    private final Long documentLinksCount;
    private final Long otherContentCount;

    // User-specific Statistics
    private final Long userId;
    private final Integer userSessionCount;
    private final Long userTotalPages;
    private final Double userAverageSuccessRate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime userFirstCrawl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime userLastCrawl;

    // System Health Indicators
    private final SystemHealthMetrics systemHealth;

    // Embedded Static Records for Detailed Statistics
    @Builder
    @Getter
    @ToString
    public static class DomainStatistic {
        private final String domain;
        private final Long pageCount;
        private final Double averageLoadTime;
        private final Double successRate;
        private final Integer errorCount;
        private final Long totalLinksFound;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final LocalDateTime lastCrawled;
    }

    @Builder
    @Getter
    @ToString
    public static class SystemHealthMetrics {
        private final Double systemCpuUsage;
        private final Long systemMemoryUsageMB;
        private final Long systemFreeMemoryMB;
        private final Integer activeThreadCount;
        private final Integer totalThreadPoolSize;
        private final Long databaseConnectionsActive;
        private final Long databaseConnectionsIdle;
        private final Double diskSpaceUsagePercent;
        private final String systemStatus; // HEALTHY, WARNING, CRITICAL

        public boolean isHealthy() {
            return "HEALTHY".equals(systemStatus);
        }

        public boolean needsAttention() {
            return "WARNING".equals(systemStatus) || "CRITICAL".equals(systemStatus);
        }
    }

    // Embedded Records for Time-based Analysis
    @Builder
    @Getter
    @ToString
    public static class TimeBasedMetric {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDateTime date;
        private final Long sessionCount;
        private final Long pageCount;
        private final Double averageSuccessRate;
        private final Double averageLoadTime;
    }

    // Builder Defaults for Null Safety
    public static class CrawlStatisticsBuilder {
        private List<DomainStatistic> topDomains = Collections.emptyList();
        private Long totalSessions = 0L;
        private Long activeSessions = 0L;
        private Long completedSessions = 0L;
        private Long failedSessions = 0L;
        private Long pausedSessions = 0L;
        private Long cancelledSessions = 0L;
        private Long totalPagesCrawled = 0L;
        private Long totalPagesFailed = 0L;
        private Double overallSuccessRate = 0.0;
        private Double averagePagesPerSession = 0.0;
        private Double averageLoadTimeMs = 0.0;
        private Long totalLinksFound = 0L;
        private Long totalKeywordsExtracted = 0L;
        private Integer uniqueDomainsCount = 0;
        private Integer uniqueKeywordCount = 0;
    }

    // Utility Methods for Statistical Calculations
    public Double getSessionCompletionRate() {
        if (totalSessions == null || totalSessions == 0) {
            return 0.0;
        }
        Long completed = completedSessions != null ? completedSessions : 0L;
        return (completed.doubleValue() / totalSessions.doubleValue()) * 100.0;
    }

    public Double getErrorRate() {
        if (totalPagesCrawled == null || totalPagesCrawled == 0) {
            return 0.0;
        }
        Long failed = totalPagesFailed != null ? totalPagesFailed : 0L;
        return (failed.doubleValue() / totalPagesCrawled.doubleValue()) * 100.0;
    }

    public Double getAverageSessionsPerDay() {
        if (firstCrawlTime == null || lastCrawlTime == null) {
            return 0.0;
        }
        long daysBetween = Duration.between(firstCrawlTime, lastCrawlTime).toDays();
        if (daysBetween == 0) daysBetween = 1; // Avoid division by zero

        return totalSessions != null ? totalSessions.doubleValue() / daysBetween : 0.0;
    }

    public Double getLinkDiscoveryRate() {
        if (totalPagesCrawled == null || totalPagesCrawled == 0) {
            return 0.0;
        }
        Long links = totalLinksFound != null ? totalLinksFound : 0L;
        return links.doubleValue() / totalPagesCrawled.doubleValue();
    }

    public boolean hasRecentActivity() {
        return lastCrawlTime != null &&
                lastCrawlTime.isAfter(LocalDateTime.now().minusHours(24));
    }

    public boolean isSystemUnderLoad() {
        return systemHealth != null &&
                systemHealth.getSystemCpuUsage() != null &&
                systemHealth.getSystemCpuUsage() > 80.0;
    }

    public String getPerformanceGrade() {
        if (overallSuccessRate == null) return "N/A";

        if (overallSuccessRate >= 95.0) return "A+";
        if (overallSuccessRate >= 90.0) return "A";
        if (overallSuccessRate >= 85.0) return "B+";
        if (overallSuccessRate >= 80.0) return "B";
        if (overallSuccessRate >= 75.0) return "C+";
        if (overallSuccessRate >= 70.0) return "C";
        return "D";
    }

    // Factory Methods for Common Scenarios
    public static CrawlStatistics empty(Long userId) {
        return CrawlStatistics.builder()
                .userId(userId)
                .systemHealth(SystemHealthMetrics.builder()
                        .systemStatus("HEALTHY")
                        .systemCpuUsage(0.0)
                        .systemMemoryUsageMB(0L)
                        .activeThreadCount(0)
                        .databaseConnectionsActive(0L)
                        .build())
                .build();
    }

    public static CrawlStatistics forUser(Long userId, Long userSessionCount, Long userTotalPages) {
        return CrawlStatistics.builder()
                .userId(userId)
                .userSessionCount(userSessionCount.intValue())
                .userTotalPages(userTotalPages)
                .userAverageSuccessRate(0.0)
                .build();
    }
}

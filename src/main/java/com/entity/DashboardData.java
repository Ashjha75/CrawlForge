package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    @Setter(AccessLevel.NONE)
    private Long dashboardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Summary Statistics
    @Column(name = "total_sessions")
    @Builder.Default
    private Long totalSessions = 0L;

    @Column(name = "active_sessions")
    @Builder.Default
    private Long activeSessions = 0L;

    @Column(name = "completed_sessions")
    @Builder.Default
    private Long completedSessions = 0L;

    @Column(name = "total_pages_crawled")
    @Builder.Default
    private Long totalPagesCrawled = 0L;

    @Column(name = "total_pages_failed")
    @Builder.Default
    private Long totalPagesFailed = 0L;

    @Column(name = "total_links_found")
    @Builder.Default
    private Long totalLinksFound = 0L;

    // Performance Metrics
    @Column(name = "average_success_rate")
    @Builder.Default
    private Double averageSuccessRate = 0.0;

    @Column(name = "average_pages_per_session")
    @Builder.Default
    private Double averagePagesPerSession = 0.0;

    @Column(name = "average_load_time_ms")
    @Builder.Default
    private Long averageLoadTimeMs = 0L;

    @Column(name = "total_error_count")
    @Builder.Default
    private Integer totalErrorCount = 0;

    // Time-based Analytics
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_crawl_time")
    private LocalDateTime lastCrawlTime;

    @Column(name = "crawls_today")
    @Builder.Default
    private Long crawlsToday = 0L;

    @Column(name = "crawls_this_week")
    @Builder.Default
    private Long crawlsThisWeek = 0L;

    @Column(name = "crawls_this_month")
    @Builder.Default
    private Long crawlsThisMonth = 0L;

    // Chart Data as JSON Strings (for database storage)
    @Column(name = "domain_distribution", columnDefinition = "TEXT")
    private String domainDistributionJson;

    @Column(name = "status_code_distribution", columnDefinition = "TEXT")
    private String statusCodeDistributionJson;

    @Column(name = "keyword_frequency", columnDefinition = "TEXT")
    private String keywordFrequencyJson;

    @Column(name = "link_type_distribution", columnDefinition = "TEXT")
    private String linkTypeDistributionJson;

    @Column(name = "daily_crawl_trend", columnDefinition = "TEXT")
    private String dailyCrawlTrendJson;

    // Recent Activity as JSON
    @Column(name = "recent_sessions", columnDefinition = "TEXT")
    private String recentSessionsJson;

    @Column(name = "top_domains", columnDefinition = "TEXT")
    private String topDomainsJson;

    @Column(name = "popular_keywords", columnDefinition = "TEXT")
    private String popularKeywordsJson;

    // User-specific Data
    @Column(name = "user_total_sessions")
    @Builder.Default
    private Integer userTotalSessions = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "user_last_activity")
    private LocalDateTime userLastActivity;

    // System Health as JSON
    @Column(name = "system_health", columnDefinition = "TEXT")
    private String systemHealthJson;

    // Dashboard Configuration
    @Column(name = "time_range", length = 20)
    @Builder.Default
    private String timeRange = "24h"; // "24h", "7d", "30d", "all"

    @Column(name = "dashboard_type", length = 50)
    @Builder.Default
    private String dashboardType = "STANDARD"; // "STANDARD", "ADMIN", "ANALYTICS"

    @Column(name = "refresh_interval_minutes")
    @Builder.Default
    private Integer refreshIntervalMinutes = 15;

    @Column(name = "is_cached")
    @Builder.Default
    private Boolean isCached = false;

    @Column(name = "cache_expires_at")
    private LocalDateTime cacheExpiresAt;

    // Audit Fields
    @CreationTimestamp
    @Column(name = "generated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime generatedAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @Column(name = "data_version")
    @Builder.Default
    private Integer dataVersion = 1;

    // Business Methods
    public boolean hasRecentActivity() {
        return lastCrawlTime != null &&
                lastCrawlTime.isAfter(LocalDateTime.now().minusHours(24));
    }

    public boolean isSystemHealthy() {
        return systemHealthJson != null &&
                systemHealthJson.contains("\"status\":\"HEALTHY\"");
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

    public boolean isCacheValid() {
        return isCached != null && isCached &&
                cacheExpiresAt != null &&
                cacheExpiresAt.isAfter(LocalDateTime.now());
    }

    public void markCacheExpired() {
        this.isCached = false;
        this.cacheExpiresAt = null;
    }

    public void setCacheExpiry(int minutes) {
        this.isCached = true;
        this.cacheExpiresAt = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean needsRefresh() {
        if (generatedAt == null) return true;
        return generatedAt.isBefore(LocalDateTime.now().minusMinutes(refreshIntervalMinutes));
    }

    public String getFormattedTimeRange() {
        return switch (timeRange) {
            case "24h" -> "Last 24 Hours";
            case "7d" -> "Last 7 Days";
            case "30d" -> "Last 30 Days";
            case "all" -> "All Time";
            default -> "Custom Range";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DashboardData that)) return false;
        return dashboardId != null && dashboardId.equals(that.dashboardId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Static Factory Methods
    public static DashboardData createEmpty(User user, String timeRange) {
        return DashboardData.builder()
                .user(user)
                .timeRange(timeRange)
                .dashboardType("STANDARD")
                .refreshIntervalMinutes(15)
                .dataVersion(1)
                .build();
    }

    public static DashboardData createForAdmin(User user) {
        return DashboardData.builder()
                .user(user)
                .timeRange("7d")
                .dashboardType("ADMIN")
                .refreshIntervalMinutes(5)
                .dataVersion(1)
                .build();
    }
}
